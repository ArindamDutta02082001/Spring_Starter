package com.example.demoredis.controllers;


import com.example.demoredis.connection.RedisConnectionFactoryClass;
import com.example.demoredis.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("person/")
public class HashPersonController {
    /**
     * format of hashmap in redis
     // person_hash::1   --> {"id": 1, "name": "ABC", "age": 30, ....}
     // person_hash::2   --> {"id": 2, "name": "DEF", "age": 40 .....}
     */


    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;


    private static final String PERSON_HASH_KEY_PREFIX = "person_hash::";

    /* it converts an object to map and vice versa
     hash map <--> java object

     */
    ObjectMapper objectMapper = new ObjectMapper();

    // to create a new hash map for each person object
    // a new table name is formed by the id we pass
    @PostMapping("/hmset")
    public void addPerson(@RequestBody Person person){

        Map map = objectMapper.convertValue(person, Map.class);
        redisConnectionFactoryClass.getTemplate().opsForHash().putAll(PERSON_HASH_KEY_PREFIX + person.getId(), map);
    }

    // to get the hash map based on the hashmap
    @GetMapping("/hmget")
    public Object getPerson(@RequestParam("id") int personId){

        System.out.println(redisConnectionFactoryClass.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + personId));
        Map map =  redisConnectionFactoryClass.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + personId);
        return  objectMapper.convertValue(map, Object.class);
    }

    // to update a particular key-val pair in a hash table
    @PutMapping("/hmupdate")
    public Object updatePerson(@RequestParam("id") int personId , @RequestParam("attribute") String attribute , @RequestParam("value") String value){

        redisConnectionFactoryClass.getTemplate().opsForHash().put(PERSON_HASH_KEY_PREFIX + personId , attribute, value);

        // getting the updated person
        Map map =  redisConnectionFactoryClass.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + personId);
        return objectMapper.convertValue(map, Object.class);
    }

    // to delete a particular key val in a hashmap
    @DeleteMapping("/hdel")
    public Object hdel(@RequestParam("id") int id,
                       @RequestParam("attributes") String fields){

        redisConnectionFactoryClass.getTemplate().opsForHash().delete(PERSON_HASH_KEY_PREFIX + id, fields);
        Map map =  redisConnectionFactoryClass.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + id);
        return objectMapper.convertValue(map, Object.class);
    }

}


