package com.mjy.blog.Service;

import com.mjy.blog.Bean.User;
import com.mjy.blog.Config.KeyConfig;

/**
 * @author mjy
 * @create 2020-05-01-21:58
 */
public interface TokenService {
    String getToken(User user, KeyConfig keyConfig);
}
