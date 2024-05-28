package com.example.demoredis.repository;

import com.example.demoredis.config.RedisConnectionConfig;
import com.example.demoredis.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class HashRepository {

    /**
     * format of hashmap in redis
     // person_hash::1   --> {"id": 1, "name": "ABC", "age": 30, ....}
     // person_hash::2   --> {"id": 2, "name": "DEF", "age": 40 .....}
     */


    @Autowired
    RedisConnectionConfig redisConnectionConfig;


    private static final String PERSON_HASH_KEY_PREFIX = "person_hash::";

    /* it converts an object to map and vice versa
     hash map <--> java object
     */
    ObjectMapper objectMapper = new ObjectMapper();

    // to create a new hash map for each person object
    // a new table name is formed by the id we pass

    public void addPersoninDB( Person person){

        Map map = objectMapper.convertValue(person, Map.class);
        redisConnectionConfig.getTemplate().opsForHash().putAll(PERSON_HASH_KEY_PREFIX + person.getId(), map);
    }

    // to get the hash map based on the hashmap

    public Object getPersonFromDB( int personId){

        System.out.println(redisConnectionConfig.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + personId));
        Map map =  redisConnectionConfig.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + personId);
        return  objectMapper.convertValue(map, Object.class);
    }

    // to update a particular key-val pair in a hash table

    public Object updatePersonINDB( int personId , String attribute , String value){

        redisConnectionConfig.getTemplate().opsForHash().put(PERSON_HASH_KEY_PREFIX + personId , attribute, value);

        // getting the updated person
        Map map =  redisConnectionConfig.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + personId);
        return objectMapper.convertValue(map, Object.class);
    }

    // to delete a particular key val in a hashmap
    public Object deletePersonINDB( int id, String fields){

        redisConnectionConfig.getTemplate().opsForHash().delete(PERSON_HASH_KEY_PREFIX + id, fields);
        Map map =  redisConnectionConfig.getTemplate().opsForHash().entries(PERSON_HASH_KEY_PREFIX + id);
        return objectMapper.convertValue(map, Object.class);
    }

}
