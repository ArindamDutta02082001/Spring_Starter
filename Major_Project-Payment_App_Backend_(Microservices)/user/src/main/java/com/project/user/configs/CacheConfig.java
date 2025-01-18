package com.project.user.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfig {

    @Value("${redis-host}")
    private String redisHost;

    @Value("${redis-port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory getConnectionFactory(){

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(redisHost, redisPort)
        );
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> getTemplate(){


        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnectionFactory());

        // for normal key-val pairs | list | set | sorted sets
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());


        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }

}
