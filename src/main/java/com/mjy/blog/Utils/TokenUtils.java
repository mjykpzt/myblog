package com.mjy.blog.Utils;

import com.mjy.blog.Bean.User;
import com.mjy.blog.Config.KeyConfig;
import org.springframework.security.core.Authentication;

/**
 * @author mjy
 * @create 2020-05-01-21:32
 */
public class TokenUtils {

    /**
     * @param authentication
     * @param keyConfig
     * @param flag           是否生成刷新token
     * @return: java.lang.String
     * @author: 0205
     */
    public static String createToken(Authentication authentication, KeyConfig keyConfig, Boolean flag) {
        User principal = (User) authentication.getPrincipal();
        User user = new User();
        user.setFlushTokenFlag(flag);
        user.setUsername(principal.getUsername());
        user.setRoles(principal.getRoles());
        user.setId(principal.getId());
        return createToken(user,keyConfig);
    }

    public static String createToken(User user, KeyConfig keyConfig){
        return JwtUtils.generateTokenExpireInMinutes(user, keyConfig.getPrivateKey(), 30);
    }
}
