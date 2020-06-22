package com.mjy.blog.controller;

import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.User;
import com.mjy.blog.Config.KeyConfig;
import com.mjy.blog.Service.TokenService;
import com.mjy.blog.Utils.JwtUtils;
import com.mjy.blog.Utils.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/getToken")
    public ResponseBean getToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("flushToken".equals(cookie.getName())) {
                String value = cookie.getValue();
                Payload<User> infoFromToken = JwtUtils.getInfoFromToken(value, keyConfig.getPublicKey(), User.class);
                User userInfo = infoFromToken.getUserInfo();
                if (userInfo != null && userInfo.getFlushTokenFlag()) {
                    String token = tokenService.getToken(userInfo, keyConfig);
                    response.setContentType("application/json;charset=utf-8");
                    response.addHeader("Authorization", "Bearer " + token);
                    return ResponseBean.getSuccessResponse("成功");
                }
            }
        }
        return ResponseBean.getFailResponse("获取失败");
    }
}
