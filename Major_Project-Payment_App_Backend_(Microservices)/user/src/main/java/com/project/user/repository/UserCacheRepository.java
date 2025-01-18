package com.project.user.repository;


import com.project.user.dto.response.userCacheResponseDto;
import com.project.user.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // to save object in cache
    public void saveInCache(userCacheResponseDto user){
        this.redisTemplate.opsForValue().set(getKey(user.getUserId()), user, Constants.USER_REDIS_KEY_EXPIRY, TimeUnit.SECONDS);
    }

    // to get object from cache
    public Object getFromCache(int userId){
        return  this.redisTemplate.opsForValue().get(getKey(userId));
    }

    private String getKey(int userId){
        return Constants.USER_REDIS_KEY_PREFIX + userId;
    }
}
