package com.mjy.blog.service;

import com.mjy.blog.bean.User;
import com.mjy.blog.config.KeyConfig;

/**
 * @author mjy
 * @create 2020-05-01-21:58
 */
public interface TokenService {
    String getToken(User user, KeyConfig keyConfig);
}
