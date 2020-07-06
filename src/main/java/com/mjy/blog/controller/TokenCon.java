package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Config.KeyConfig;
import com.mjy.blog.Exception.AccessException;
import com.mjy.blog.Service.RedisService;
import com.mjy.blog.Service.TokenService;
import com.mjy.blog.Utils.JwtUtils;
import com.mjy.blog.Utils.Payload;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    public ResponseBean getToken(HttpServletRequest request, HttpServletResponse response) throws AccessException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("flushToken".equals(cookie.getName())) {

                String flushToken = cookie.getValue();
                Payload<User> infoFromToken = JwtUtils.getInfoFromToken(flushToken, keyConfig.getPublicKey(), User.class);
                User user = infoFromToken.getUserInfo();
                if (user != null && user.getFlushTokenFlag()) {
                    if (redisService.findTokenInBlack(user.getId(),flushToken)){
                        return ResponseBean.getFailResponse("token无效");
//                        throw new AccessException(401,"请重新登陆","-------------------");
                    }
                    String token = tokenService.getToken(user, keyConfig);
                    response.setContentType("application/json;charset=utf-8");
                    response.addHeader("Authorization", "Bearer " + token);
                    return ResponseBean.getSuccessResponse("成功");
                }
            }
        }
        return ResponseBean.getFailResponse("获取失败");
    }
}
