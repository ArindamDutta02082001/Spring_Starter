package com.example.demoredis.controllers;

import com.example.demoredis.connection.RedisConnectionFactoryClass;
import com.example.demoredis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/person")
public class StringPersonController {

    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;

    String PERSON_VALUE_PREFIX = "person:";

    // to set a person object into a key-value pair
    @PostMapping("/set/{personId}")
    public void setValue(@PathVariable("personId") Integer personId,
                         @RequestBody Person person){

        String key = PERSON_VALUE_PREFIX + personId;
        redisConnectionFactoryClass.getTemplate().opsForValue().set(key, person, 120, TimeUnit.SECONDS);
    }

    // to get a person object into a key-value pair
    @GetMapping("/get/{personId}")
    public Person getValue(@PathVariable("personId") Integer personId){
        String key = PERSON_VALUE_PREFIX + personId;
        return (Person) redisConnectionFactoryClass.getTemplate().opsForValue().get(key);
    }

    // to delete a person
    @DeleteMapping("/del/{personId}")
    public Person delValue( @PathVariable("personId") Integer personId)
    {
        String key = PERSON_VALUE_PREFIX + personId;

        // getAndDelete() not working as redis in my PC is <5.0.0
        return (Person) redisConnectionFactoryClass.getTemplate().opsForValue().getAndDelete(key);
    }

    // to modify a person`s details
    @PutMapping("/update/{personId}")
    public String updateValue(@PathVariable("personId") Integer personId , @RequestBody Person person)
    {
        String key = PERSON_VALUE_PREFIX + personId;
        Boolean res = redisConnectionFactoryClass.getTemplate().opsForValue().setIfPresent(key , person );
        if(Boolean.TRUE.equals(res))
            return "key is updated ";
        else
            return "key is not updated ";
    }

}
