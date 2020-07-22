package com.mjy.blog.service;

import com.mjy.blog.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;
/**
 *@author mjy
 *@date 2020/7/19
 */
public interface QiNiuYunService {
    /**
     *
     *返回七牛云上传凭证
     *
     * @param filename 文件名
     * @param size 文件大小
     * @param uid 用户id
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean uploadToken(String filename,Integer size,Integer uid);


    /**
     *
     *七牛云服务器回调接口
     *
     * @param request http请求
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean callback(HttpServletRequest request);
}
