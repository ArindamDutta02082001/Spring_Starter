package com.example.demoredis.repository;

import com.example.demoredis.connection.CacheConfig;
import com.example.demoredis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public class SetRepository {


    private static final String SET_KEY = "set:person:";

    @Autowired
    CacheConfig cacheConfig;


    // Create/Add elements to a Set
    public void addSetValue(Set<Person> person) {

        for(Person p : person)
        {
            cacheConfig.getTemplate().opsForSet().add(SET_KEY, p);
        }
    }

    // Read/Get all elements from a Set
    public Set<Object> getSetMembers() {
        return cacheConfig.getTemplate().opsForSet().members(SET_KEY);
    }

    // Read/Get the size of a Set
    public Long getSetSize() {
        return cacheConfig.getTemplate().opsForSet().size(SET_KEY);
    }

    // Update/Add multiple elements to a Set (Since Set does not support direct update, it's an add operation)
    public void updateSetValue( Person person) {
        // removing the particular person
        //        -to-do

        // adding that new person
        cacheConfig.getTemplate().opsForSet().add(SET_KEY, person);
    }

    // Delete/Remove specific elements from a Set
    public Long removeSetValue( Person person) {
        return cacheConfig.getTemplate().opsForSet().remove(SET_KEY, person);
    }

    // Delete the entire Set
    public Boolean deleteSet() {
        return cacheConfig.getTemplate().delete(SET_KEY);
    }

}
