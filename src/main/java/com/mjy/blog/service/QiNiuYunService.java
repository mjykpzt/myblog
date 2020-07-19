package com.mjy.blog.service;

import com.mjy.blog.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;

public interface QiNiuYunService {
    ResponseBean uploadToken(String filename);

    ResponseBean callback(HttpServletRequest request);
}
