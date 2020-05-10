package com.mjy.blog.Service.Impl;

import com.mjy.blog.Bean.User;
import com.mjy.blog.Config.KeyConfig;
import com.mjy.blog.Service.TokenService;
import com.mjy.blog.Utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * @author mjy
 * @create 2020-05-01-22:00
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String getToken(User user, KeyConfig keyConfig) {
        return TokenUtils.createToken(user, keyConfig,30);

    }
}
