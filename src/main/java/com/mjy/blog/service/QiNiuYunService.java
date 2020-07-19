package com.mjy.blog.service;

import com.mjy.blog.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;

public interface QiNiuYunService {
    /**
     *
     *返回上传七牛云的token
     *
     * @param filename 文件名
     * @return: com.mjy.blog.bean.ResponseBean
     * @author: 0205
     */
    ResponseBean uploadToken(String filename);


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
