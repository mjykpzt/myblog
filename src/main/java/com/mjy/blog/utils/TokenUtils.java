package com.mjy.blog.utils;

import com.mjy.blog.bean.User;
import com.mjy.blog.config.KeyConfig;
import org.springframework.security.core.Authentication;

/**
 * @author mjy
 * @create 2020-05-01-21:32
 */
public class TokenUtils {

    /**
     * @param authentication 用户信息
     * @param keyConfig 秘钥
     * @param flag  是否生成刷新token;如果为true，则生成刷新token，否则生成请求token
     * @return: java.lang.String
     * @author: 0205
     */
    public static String createToken(Authentication authentication, KeyConfig keyConfig, Boolean flag,Integer TokenLifeTime) {
        User principal = (User) authentication.getPrincipal();
        User user = new User();
        user.setFlushTokenFlag(flag);
        user.setUsername(principal.getUsername());
        user.setRoles(principal.getRoles());
        user.setId(principal.getId());
        return createToken(user,keyConfig,TokenLifeTime);
    }

    public static String createToken(User user, KeyConfig keyConfig,Integer TokenLifeTime){
        return JwtUtils.generateTokenExpireInMinutes(user, keyConfig.getPrivateKey(), TokenLifeTime);
    }
}
