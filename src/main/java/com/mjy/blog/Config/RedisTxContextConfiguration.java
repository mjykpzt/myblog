package com.mjy.blog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author mjy
 * @create 2020-06-24-22:52
 */
//@Configuration
//public class RedisTxContextConfiguration {
//    @Resource
//    private RedisConnectionFactory factory;
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplateRedis(){
//        StringRedisTemplate redisTemplate = new StringRedisTemplate();
//        redisTemplate.setEnableTransactionSupport(true);
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//}
