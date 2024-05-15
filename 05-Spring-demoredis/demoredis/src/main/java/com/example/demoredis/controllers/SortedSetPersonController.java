package com.example.demoredis.controllers;

import com.example.demoredis.connection.RedisConnectionFactoryClass;
import com.example.demoredis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/person")
public class SortedSetPersonController {
    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;

    private static  String SORTED_SET_KEY = "sorted:set:person";

    // Add a Person object with a score to a Sorted Set
    @PostMapping("/zadd")
    public Boolean addZSetValue(@RequestParam("score") Double score , @RequestBody Person person) {
        return redisConnectionFactoryClass.getTemplate().opsForZSet().add(SORTED_SET_KEY, person, score);
    }

    // Get all Person objects from a Sorted Set within a score range
    @GetMapping("/zscorerange")
    public Set<Object> getZSetRange( @RequestParam double min, @RequestParam double max) {
        System.out.println(redisConnectionFactoryClass.getTemplate().opsForZSet().rangeByScore(SORTED_SET_KEY, min, max));
        return redisConnectionFactoryClass.getTemplate().opsForZSet().rangeByScore(SORTED_SET_KEY, min, max);
    }

    // Get all Person objects from a Sorted Set within range of index
    @GetMapping("/zindexrange")
    public Set<Object> getZSetRange2( @RequestParam long min, @RequestParam long max) {
        System.out.println(redisConnectionFactoryClass.getTemplate().opsForZSet().range(SORTED_SET_KEY, min, max));
        return redisConnectionFactoryClass.getTemplate().opsForZSet().range(SORTED_SET_KEY, min, max);
    }

    // Update a Person's score in a Sorted Set
    @PutMapping("/zupdate")
    public boolean updateZSetValue(@RequestParam("score") Double score, @RequestBody Person person) {
        // removing the element of a particular score if present
        Person person1 = (Person) redisConnectionFactoryClass.getTemplate().opsForZSet().rangeByScore(SORTED_SET_KEY, score, score).stream().toArray()[0];
        if(person1 != null)
         redisConnectionFactoryClass.getTemplate().opsForZSet().remove(SORTED_SET_KEY, person1);
        // inserting the new element
        return Boolean.TRUE.equals(redisConnectionFactoryClass.getTemplate().opsForZSet().addIfAbsent(SORTED_SET_KEY, person, score));
    }

    // Remove a Person object from a Sorted Set
    @DeleteMapping("/zremove")
    public Long removeZSetValue( @RequestBody Person person) {
        return redisConnectionFactoryClass.getTemplate().opsForZSet().remove(SORTED_SET_KEY, person);
    }

    // Delete the entire Sorted Set
    @DeleteMapping("/zdelete")
    public Boolean deleteZSet() {
        return redisConnectionFactoryClass.getTemplate().delete(SORTED_SET_KEY);
    }
}
