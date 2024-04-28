package com.example.demoredis.controllers;

import com.example.demoredis.connection.RedisConnectionFactoryClass;
import com.example.demoredis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;

    String PERSON_VALUE_PREFIX = "person:";

    @PostMapping("/set/{personId}")
    public void setValue(@PathVariable("personId") Integer personId,
                         @RequestBody Person person){

        String key = PERSON_VALUE_PREFIX + personId;
        RedisConnectionFactoryClass.getTemplate().opsForValue().set(key, person, 120, TimeUnit.SECONDS);
    }

    @GetMapping("/get/{personId}")
    public Person getValue(@PathVariable("personId") Integer personId){
        String key = PERSON_VALUE_PREFIX + personId;
        return (Person) RedisConnectionFactoryClass.getTemplate().opsForValue().get(key);
    }

    @
}
