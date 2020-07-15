package com.mjy.blog.service.Impl;

import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.bean.User;
import com.mjy.blog.config.KeyConfig;
import com.mjy.blog.service.RedisService;
import com.mjy.blog.service.TokenService;
import com.mjy.blog.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author mjy
 * @create 2020-05-01-22:00
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private RedisService redisService;

    @Override
    public String getToken(User user, KeyConfig keyConfig) {
        String token = TokenUtils.createToken(user, keyConfig, 30);
        redisService.setWhite(user.getId(), TokenEnum.AUTHORIZATION_TOKEN_HEADER,token,30);
        return token;

    }
}
