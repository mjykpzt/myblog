package com.mjy.blog.service.impl;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.mapper.ImgDao;
import com.mjy.blog.service.QiNiuYunService;
import com.mjy.blog.service.RedisService;
import com.mjy.blog.utils.GetJsonImg;
import com.mjy.blog.utils.imgJson;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *@author mjy
 *@date 2020/7/21
 */
@Service
public class QiNiuYunServiceImpl implements QiNiuYunService {
    @Value("${QiNiuYun.accessKey}")
    private String accessKey;
    @Value("${QiNiuYun.secreKey}")
    private String secretKey;
    @Value("${QiNiuYun.bucket}")
    private String bucket;
    @Value("${QiNiuYun.callbackUrl}")
    private String callbackUrl;
    @Value("${QiNiuYun.baseUrl}")
    private String baseUrl;
    private String callbackBodyType = "application/json";
    private Logger logger1 = Logger.getLogger("console");

    @Resource
    private ImgDao imgDao;

    @Resource
    private RedisService redisService;

    @Override
    public ResponseBean uploadToken(String filename,Integer size,Integer uid,String hash) {
        int num = imgDao.isHasImg(hash);
        if (num>0){
            return ResponseBean.getSuccessResponse("文件重复",baseUrl+hash);
        }
        Long totalSize = redisService.setImgSize(uid, size);
        int sizeMax = 1024 * 1024 * 1024;
        if (totalSize> sizeMax){
            return ResponseBean.getFailResponse("今日文件上传已经达到上限");
        }
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", callbackUrl);
        putPolicy.put("callbackBody", "{\"user_id\":"+uid+",\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        putPolicy.put("callbackBodyType", callbackBodyType);
        putPolicy.put("mimeLimit","image/*");
        putPolicy.put("fsizeLimit",size+1);
        long expireSeconds = 300;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        logger1.debug(upToken);
        return ResponseBean.getSuccessResponse("成功", upToken);
    }

    @Override
    public ResponseBean callback(HttpServletRequest request) {
        String callbackAuthHeader = request.getHeader(TokenEnum.AUTHORIZATION_TOKEN_HEADER);
        //通过读取回调POST请求体获得，不要设置为null
        byte[] callbackBody = new byte[1024];
        try {
            //这里是最重要的，接收byte[]
            request.getInputStream().read(callbackBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将byte[]转化为字符串
        String callbackBodyStr = new String(callbackBody);
        Auth auth = Auth.create(accessKey, secretKey);
        //检查是否为七牛合法的回调请求
        boolean validCallback = auth.isValidCallback(callbackAuthHeader, callbackUrl, callbackBody, callbackBodyType);
        logger1.debug(validCallback);
        if (validCallback){
            try {
                imgJson jsonBodyObject = GetJsonImg.getJsonBodyObject(callbackBodyStr, imgJson.class);
                logger1.debug(callbackBodyStr);
                if (jsonBodyObject!=null){
                    int i = imgDao.addImgInformation(jsonBodyObject);
                    logger1.debug("影响行数"+i);
                    if (i>0){
                        String url = baseUrl+jsonBodyObject.getKey();
                        return ResponseBean.getSuccessResponse("上传成功",url);
                    }
                }

            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        return ResponseBean.getSuccessResponse("上传失败");
    }
}
