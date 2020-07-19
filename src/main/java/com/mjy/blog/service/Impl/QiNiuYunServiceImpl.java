package com.mjy.blog.service.Impl;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.mapper.ImgDao;
import com.mjy.blog.service.QiNiuYunService;
import com.mjy.blog.utils.GetJsonImg;
import com.mjy.blog.utils.imgJson;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @Resource
    private ImgDao imgDao;

    @Override
    public ResponseBean uploadToken(String filename) {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", callbackUrl);
        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        putPolicy.put("callbackBodyType", callbackBodyType);
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        return ResponseBean.getSuccessResponse("成功", upToken);
    }

    @Override
    public ResponseBean callback(HttpServletRequest request) {
        String callbackAuthHeader = request.getHeader("Authorization");
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
        if (validCallback){
            try {
                imgJson jsonBodyObject = GetJsonImg.getJsonBodyObject(callbackBodyStr, imgJson.class);
                if (jsonBodyObject!=null){
                    int i = imgDao.addImgInformation(jsonBodyObject);
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
