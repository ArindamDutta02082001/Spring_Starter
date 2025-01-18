package com.example.demoredis.repository;

import com.example.demoredis.connection.CacheConfig;
import com.example.demoredis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SortedSetRepository {
    @Autowired
    CacheConfig cacheConfig;

    private static  String SORTED_SET_KEY = "sorted:set:person";

    // Add a Person object with a score to a Sorted Set
 
    public Boolean addZSetValue(Double score , Person person) {
        return cacheConfig.getTemplate().opsForZSet().add(SORTED_SET_KEY, person, score);
    }

    // Get all Person objects from a Sorted Set within a score range
    public Set<Object> getZSetRange( double min, double max) {
        System.out.println(cacheConfig.getTemplate().opsForZSet().rangeByScore(SORTED_SET_KEY, min, max));
        return cacheConfig.getTemplate().opsForZSet().rangeByScore(SORTED_SET_KEY, min, max);
    }

    // Get all Person objects from a Sorted Set within range of index
    public Set<Object> getZSetRange2( long min, long max) {
        System.out.println(cacheConfig.getTemplate().opsForZSet().range(SORTED_SET_KEY, min, max));
        return cacheConfig.getTemplate().opsForZSet().range(SORTED_SET_KEY, min, max);
    }

    // Update a Person's score in a Sorted Set
    public boolean updateZSetValue(Double score, Person person) {
        // removing the element of a particular score if present
        Person person1 = (Person) cacheConfig.getTemplate().opsForZSet().rangeByScore(SORTED_SET_KEY, score, score).stream().toArray()[0];
        if(person1 != null)
            cacheConfig.getTemplate().opsForZSet().remove(SORTED_SET_KEY, person1);
        // inserting the new element
        return Boolean.TRUE.equals(cacheConfig.getTemplate().opsForZSet().addIfAbsent(SORTED_SET_KEY, person, score));
    }

    // Remove a Person object from a Sorted Set

    public Long removeZSetValue( Person person) {
        return cacheConfig.getTemplate().opsForZSet().remove(SORTED_SET_KEY, person);
    }

    // Delete the entire Sorted Set
    public Boolean deleteZSet() {
        return cacheConfig.getTemplate().delete(SORTED_SET_KEY);
    }
}
