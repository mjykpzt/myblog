package com.mjy.blog.controller;

import com.mjy.blog.bean.ResponseBean;
import com.mjy.blog.bean.TokenEnum;
import com.mjy.blog.bean.User;
import com.mjy.blog.config.KeyConfig;
import com.mjy.blog.service.RedisService;
import com.mjy.blog.service.TokenService;
import com.mjy.blog.utils.JwtUtils;
import com.mjy.blog.utils.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mjy
 * @create 2020-05-01-21:56
 */
@RestController
public class TokenCon {
    @Resource
    private KeyConfig keyConfig;
    @Resource
    private TokenService tokenService;
    @Resource
    private RedisService redisService;

    @PostMapping("/getToken")
    public ResponseBean getToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (TokenEnum.FLUSH_TOKEN_HEADER.equals(cookie.getName())) {

                String flushToken = cookie.getValue();
                Payload<User> infoFromToken = JwtUtils.getInfoFromToken(flushToken, keyConfig.getPublicKey(), User.class);
                User user = infoFromToken.getUserInfo();
                if (user != null && user.getFlushTokenFlag()) {
                    if (redisService.findTokenInBlack(user.getId(),flushToken)){
                        return ResponseBean.getFailResponse("flushToken无效");
                    }
                    String token = tokenService.getToken(user, keyConfig);
                    response.setContentType("application/json;charset=utf-8");
                    response.addHeader(TokenEnum.AUTHORIZATION_TOKEN_HEADER, TokenEnum.AUTHORIZATION_TOKEN_FLAG+ token);
                    return ResponseBean.getSuccessResponse("获取\"验证token\"成功");
                }
            }
        }
        return ResponseBean.getFailResponse("获取\"验证token\"失败");
    }
}
