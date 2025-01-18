package com.example.demoredis.repository;

import com.example.demoredis.connection.CacheConfig;
import com.example.demoredis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
public class StringRepository {

    @Autowired
    CacheConfig cacheConfig;

    String PERSON_VALUE_PREFIX = "person:";

    // to set a person object into a key-value pair
    public void setValue( Integer personId,
                         Person person){

        String key = PERSON_VALUE_PREFIX + personId;
        cacheConfig.getTemplate().opsForValue().set(key, person, 120, TimeUnit.SECONDS);
    }

    // to get a person object into a key-value pair
    public Person getValue( Integer personId){
        String key = PERSON_VALUE_PREFIX + personId;
        return (Person) cacheConfig.getTemplate().opsForValue().get(key);
    }

    // to delete a person
    public Person delValue( Integer personId)
    {
        String key = PERSON_VALUE_PREFIX + personId;

        // getAndDelete() not working as redis in my PC is <5.0.0
        return (Person) cacheConfig.getTemplate().opsForValue().getAndDelete(key);
    }

    // to modify a person`s details
    public String updateValue( Integer personId , Person person)
    {
        String key = PERSON_VALUE_PREFIX + personId;
        Boolean res = cacheConfig.getTemplate().opsForValue().setIfPresent(key , person );
        if(Boolean.TRUE.equals(res))
            return "key is updated ";
        else
            return "key is not updated ";
    }
}
