package com.example.demoredis.connection;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionConfig {


    // START A CONNECTION TO THE REDIS SERVER
    public  LettuceConnectionFactory connectionFactory()
    {
        // connection configs
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);

        // starting a connection
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;

    }

    // DEFINE A REDIS TEMPLATE TO USE DIFFERENT REDIS DATA SRUCTURES
    public  RedisTemplate<String, Object> getTemplate(){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());

        // for normal key-val pairs | list | set | sorted sets
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        // hashmaps
        redisTemplate.setHashKeySerializer(new StringRedisSerializer()); // ~field
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer()); // value for a field

        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }


}
