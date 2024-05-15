package com.example.demoredis.controllers;

import com.example.demoredis.connection.RedisConnectionFactoryClass;
import com.example.demoredis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class ListPersonController {


    private final String PERSON_LIST_KEY = "person:list";

    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;

    // to left push a person object in list
    @PostMapping("/lpush")
    public Long lpush(@RequestBody Person person){
        return redisConnectionFactoryClass.getTemplate().opsForList().leftPush(PERSON_LIST_KEY, person);
    }

    // to right push a person object in list
    @PostMapping("/rpush")
    public Long rpush(@RequestBody Person person){
        return redisConnectionFactoryClass.getTemplate().opsForList().rightPush(PERSON_LIST_KEY, person);
    }

    // to left pop a person from the list
    @DeleteMapping("/lpop")
    public Person lpop(@RequestParam(value = "count", required = false, defaultValue = "1") long count){

        // count can be passed if you have a newer version of redis cli
//        return (Person) redisConnectionFactoryClass.getTemplate().opsForList()
//                .leftPop(PERSON_LIST_KEY , count);

                return (Person) redisConnectionFactoryClass.getTemplate().opsForList()
                .leftPop(PERSON_LIST_KEY );

    }

    // to right pop a person from the list
    @DeleteMapping("/rpop")
    public Person rpop(@RequestParam(value = "count", required = false, defaultValue = "1") int count){

        // count can be passed if you have a newer version of redis cli
//        return (Person) redisConnectionFactoryClass.getTemplate().opsForList()
//                .rightPop(PERSON_LIST_KEY,count);

        return (Person) redisConnectionFactoryClass.getTemplate().opsForList()
                .rightPop(PERSON_LIST_KEY);
    }



    //    localhost:9000/person/list/lrange?start=0&end=-1
    @GetMapping("/lrange")
    public List<Object> lrange(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                               @RequestParam(value = "end", required = false, defaultValue = "-1") int end){

        return redisConnectionFactoryClass.getTemplate().opsForList()
                .range(PERSON_LIST_KEY, start, end);
    }

}
