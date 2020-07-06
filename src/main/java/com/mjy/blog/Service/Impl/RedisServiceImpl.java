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
     * @param keyName1 token
     * @param keyName2 token
     * @return: boolean
     * @author: 0205
     */
    @Override
    public Boolean WhiteToBlackTokens(Integer uid,String keyName1,String keyName2) {
        try {
            String value1 = getValue(uid, keyName1);
            String value2 = getValue(uid, keyName2);
            if (value1 !=null&&value2 != null){
                setBlack(uid,60*24,value1,value2);
            }else if (value1 !=null){
                setBlack(uid,60*24,value1);
            }else if (value2!=null){
                setBlack(uid,60*24,value2);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
        return set != null && set.contains(token);
    }


    @Override
    public Boolean findTokenInBlack(Integer uid,String token){
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(dateTimeFormatter);
        String yesterday = now.minusDays(1).format(dateTimeFormatter);
        return  findTokenInBlackSimply(uid+today,token)||findTokenInBlackSimply(uid+yesterday,token);
    }


}
