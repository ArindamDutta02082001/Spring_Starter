package com.example.demoredis.controllers;


import com.example.demoredis.model.Person;
import com.example.demoredis.repository.HashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class HashPersonController {
    /**
     * format of hashmap in redis
     // person_hash::1   --> {"id": 1, "name": "ABC", "age": 30, ....}
     // person_hash::2   --> {"id": 2, "name": "DEF", "age": 40 .....}
     */


    @Autowired
    HashRepository hashRepository;

    @PostMapping("/hmset")
    public void addPerson(@RequestBody Person person){

        hashRepository.addPersoninDB(person);
    }

    // to get the hash map based on the hashmap
    @GetMapping("/hmget")
    public Object getPerson(@RequestParam("id") int personId){

        return hashRepository.getPersonFromDB(personId);
    }

    // to update a particular key-val pair in a hash table
    @PutMapping("/hmupdate")
    public Object updatePerson(@RequestParam("id") int personId , @RequestParam("attribute") String attribute , @RequestParam("value") String value){

        return  hashRepository.updatePersonINDB(personId,attribute,value);
    }

    // to delete a particular key val in a hashmap
    @DeleteMapping("/hdel")
    public Object deletePerson(@RequestParam("id") int id,
                               @RequestParam("attributes") String fields){

        return hashRepository.deletePersonINDB(id, fields);
    }

}


