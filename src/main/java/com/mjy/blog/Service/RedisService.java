package com.mjy.blog.Service;

import java.util.Set;

/**
 * @author mjy
 * @create 2020-06-24-17:20
 */
public interface RedisService {
    Boolean setWhite(Integer uid, String key, String value, Integer time);

    Boolean setBlack(Integer uid, Integer time,String... value);

    Boolean isHasKey(String key);

    String getValue(Integer uid, String key);

    Set<String> getSet(String key);

    Boolean delValue(Integer uid,String key);

    Boolean WhiteToBlackTokens(Integer uid,String keyName1,String keyName2 );

    Boolean findTokenInBlackSimply(String key,String token);

    Boolean findTokenInBlack(Integer uid,String token);
}
