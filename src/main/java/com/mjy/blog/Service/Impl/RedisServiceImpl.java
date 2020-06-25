package com.mjy.blog.Service.Impl;


import com.mjy.blog.Service.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author mjy
 * @create 2020-06-24-17:29
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("-yyyyMMdd", Locale.CHINA);


    /**
     * 将token保存到白名单
     *
     * @param uid   用户ID
     * @param key   token类型
     * @param value token值
     * @param time  过期时间
     * @return: java.lang.Boolean
     * @author: 0205
     */
    @Override
    public Boolean setWhite(Integer uid, String key, String value, Integer time) {
        boolean re = false;
        try {
            stringRedisTemplate.opsForValue().set(uid + key, value, time * 60 * 1000, TimeUnit.SECONDS);
            String s = stringRedisTemplate.opsForValue().get(uid + key);
            re = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }

    /**
     * @param uid   用户ID
     * @param value token值
     * @param time  过期时间
     * @return: java.lang.Boolean
     * @author: 0205
     */
    @Override
    public Boolean setBlack(Integer uid, Integer time,String... value) {
        boolean re = false;
        String format = dateTimeFormatter.format(ZonedDateTime.now());
        try {
            stringRedisTemplate.opsForSet().add(uid + format, value);
            stringRedisTemplate.expire(uid + format, time * 60 * 1000, TimeUnit.SECONDS);
            re = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }


    @Override
    public Boolean isHasKey(String key) {
        Boolean re = true;
        try {
            re = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return re;
    }

    @Override
    public String getValue(Integer uid, String key) {
        try {
            return stringRedisTemplate.opsForValue().get(uid + key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Set<String> getSet(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    @Override
    public Boolean delValue(Integer uid,String key) {
        Boolean delete = false;
        try {
            delete = stringRedisTemplate.delete(uid + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete;
    }

    /**
     * 将白名单中的token移除，并加入黑名单
     *
     * @param uid 用户ID
     * @param key1 token
     * @param key2 token
     * @return: boolean
     * @author: 0205
     */
    @Override
    public Boolean WhiteToBlackTokens(Integer uid,String key1,String key2) {
        try {
//            stringRedisTemplate.multi();
            String value1 = getValue(uid, key1);
            String s = stringRedisTemplate.opsForValue().get(uid + key1);
            String value2 = getValue(uid, key2);
            if (value1 !=null&&value2 != null){
                delValue(uid,key1);
                delValue(uid,key2);
                setBlack(uid,60*24,value1,value2);
            }
//            stringRedisTemplate.exec();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
//            stringRedisTemplate.discard();
            return false;
        }

    }

    /**
     * 查询黑名单上是否存在该ID
     *
     * @param key 用户ID
     * @param token token
     * @return: java.lang.Boolean
     * @author: 0205
     */
    @Override
    public Boolean findTokenInBlackSimply(String key,String token) {
        Set<String> set = getSet(key);
        if (set !=null) return set.contains(token);
        return false;
    }

    @Override
    public Boolean findTokenInBlack(Integer uid,String token){
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(dateTimeFormatter);
        String yesterday = now.minusDays(1).format(dateTimeFormatter);
        return  findTokenInBlackSimply(uid+today,token)||findTokenInBlackSimply(uid+yesterday,token);
    }


}
