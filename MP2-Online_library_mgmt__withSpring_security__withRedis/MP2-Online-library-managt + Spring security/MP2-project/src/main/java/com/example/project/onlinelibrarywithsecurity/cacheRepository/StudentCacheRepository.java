package com.example.project.onlinelibrarywithsecurity.cacheRepository;

import com.example.project.onlinelibrarywithsecurity.configs.CacheConfig;
import com.example.project.onlinelibrarywithsecurity.responseObject.studentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class StudentCacheRepository {

    @Autowired
    CacheConfig cacheConfig;


    String STUDENT_VALUE_PREFIX = "student:";

    // to set a student object into a key-value pair
    public void setValue( Integer personId,
                          studentResponse student ){

        String key = STUDENT_VALUE_PREFIX + personId;
        cacheConfig.getTemplate().opsForValue().set(key, student, 3600, TimeUnit.SECONDS);
    }

    // to get a student object into a key-value pair
    public studentResponse getValue(Integer personId){
        String key = STUDENT_VALUE_PREFIX + personId;
        return  (studentResponse)cacheConfig.getTemplate().opsForValue().get(key);
    }

}
