package com.mjy.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.port}")
    private Integer port;
    @Value("${redis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${redis.pool.max-wait}")
    private Integer maxWait;
    @Value("${redis.pool.min-idle}")
    private Integer minIdle;
    @Value("${redis.pool.max-total}")
    private Integer maxTotal;


    @Bean("stringRedisTemplateForToken")
    public StringRedisTemplate StringRedisTemplateForToken() {
        StringRedisTemplate stringRedisTemplateForToken = new StringRedisTemplate();
        stringRedisTemplateForToken.setConnectionFactory(buildConnectionFactory(0));
        return stringRedisTemplateForToken;
    }

    @Bean("stringRedisTemplateForImg")
    public StringRedisTemplate StringRedisTemplateForImg() {
        StringRedisTemplate stringRedisTemplateForImg = new StringRedisTemplate();
        stringRedisTemplateForImg.setConnectionFactory(buildConnectionFactory(1));
        return stringRedisTemplateForImg;
    }

    private JedisClientConfiguration clientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        return builder.usePooling()
                .poolConfig(poolConfig())
                .build();
    }

    private RedisStandaloneConfiguration redisStandaloneConfiguration(Integer index) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPassword(RedisPassword.of(password));
        configuration.setPort(port);
        configuration.setDatabase(index);
        return configuration;
    }

    private JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        return poolConfig;
    }

    private JedisConnectionFactory buildConnectionFactory(int database) {
        return new JedisConnectionFactory(redisStandaloneConfiguration(database), clientConfiguration());
    }
}
