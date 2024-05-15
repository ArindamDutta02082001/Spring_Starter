package com.example.demoredis.controllers;

import com.example.demoredis.connection.RedisConnectionFactoryClass;
import com.example.demoredis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@RestController
@RequestMapping("/person")
public class SetPersonController {

    private static final String SET_KEY = "set:person:";

    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;


    // Create/Add elements to a Set
    @PostMapping("/add")
    public void addSetValue(@RequestBody Set<Person> person) {

        for(Person p : person)
        {
            redisConnectionFactoryClass.getTemplate().opsForSet().add(SET_KEY, p);
        }
    }

    // Read/Get all elements from a Set
    @GetMapping("/get")
    public Set<Object> getSetMembers() {
        return redisConnectionFactoryClass.getTemplate().opsForSet().members(SET_KEY);
    }

    // Read/Get the size of a Set
    @GetMapping("/size")
    public Long getSetSize() {
        return redisConnectionFactoryClass.getTemplate().opsForSet().size(SET_KEY);
    }

    // Update/Add multiple elements to a Set (Since Set does not support direct update, it's an add operation)
    @PutMapping("/update")
    public void updateSetValue( @RequestBody Person person) {
        // removing the particular person
        //        -to-do

        // adding that new person
        redisConnectionFactoryClass.getTemplate().opsForSet().add(SET_KEY, person);
    }

    // Delete/Remove specific elements from a Set
    @DeleteMapping("/remove")
    public Long removeSetValue( @RequestBody Person person) {
        return redisConnectionFactoryClass.getTemplate().opsForSet().remove(SET_KEY, person);
    }

    // Delete the entire Set
    @DeleteMapping("/delete")
    public Boolean deleteSet() {
        return redisConnectionFactoryClass.getTemplate().delete(SET_KEY);
    }

}
