package com.example.demoredis.repository;

import com.example.demoredis.connection.CacheConfig;
import com.example.demoredis.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListRepository {


    private final String PERSON_LIST_KEY = "person:list";

    @Autowired
    CacheConfig cacheConfig;

    // to left push a person object in list
    public Long lpush(Person person){
        return cacheConfig.getTemplate().opsForList().leftPush(PERSON_LIST_KEY, person);
    }

    // to right push a person object in list
    public Long rpush(Person person){
        return cacheConfig.getTemplate().opsForList().rightPush(PERSON_LIST_KEY, person);
    }

    // to left pop a person from the list
    public Person lpop(long count){

        // count can be passed if you have a newer version of redis cli
//        return (Person) cacheConfig.getTemplate().opsForList()
//                .leftPop(PERSON_LIST_KEY , count);

        return (Person) cacheConfig.getTemplate().opsForList()
                .leftPop(PERSON_LIST_KEY );

    }

    // to right pop a person from the list
    public Person rpop(int count){

        // count can be passed if you have a newer version of redis cli
//        return (Person) cacheConfig.getTemplate().opsForList()
//                .rightPop(PERSON_LIST_KEY,count);

        return (Person) cacheConfig.getTemplate().opsForList()
                .rightPop(PERSON_LIST_KEY);
    }



    //    localhost:9000/person/list/lrange?start=0&end=-1
    public List<Object> lrange(int start, int end){

        return cacheConfig.getTemplate().opsForList()
                .range(PERSON_LIST_KEY, start, end);
    }
}
