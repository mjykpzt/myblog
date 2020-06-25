package com.mjy.blog.Service.Impl;

import com.mjy.blog.Bean.User;
import com.mjy.blog.Config.KeyConfig;
import com.mjy.blog.Service.RedisService;
import com.mjy.blog.Service.TokenService;
import com.mjy.blog.Utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        redisService.setWhite(user.getId(),"Authorization",token,30);
        return token;

    }
}
