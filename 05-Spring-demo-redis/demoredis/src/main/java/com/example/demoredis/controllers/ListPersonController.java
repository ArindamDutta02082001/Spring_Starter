package com.example.demoredis.controllers;

import com.example.demoredis.model.Person;
import com.example.demoredis.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class ListPersonController {


    @Autowired
    ListRepository listRepository;

    // to left push a person object in list
    @PostMapping("/lpush")
    public Long leftPush(@RequestBody Person person){
        return listRepository.lpush(person);
    }

    // to right push a person object in list
    @PostMapping("/rpush")
    public Long rightPush(@RequestBody Person person){
        return  listRepository.rpush(person);
    }

    // to left pop a person from the list
    @DeleteMapping("/lpop")
    public Person leftPop(@RequestParam(value = "count", required = false, defaultValue = "1") long count){

        return  listRepository.lpop(count);

    }

    // to right pop a person from the list
    @DeleteMapping("/rpop")
    public Person rightPop(@RequestParam(value = "count", required = false, defaultValue = "1") int count){

        return  listRepository.rpop(count);
    }



    //    localhost:9000/person/list/lrange?start=0&end=-1
    @GetMapping("/lrange")
    public List<Object> getElements(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                    @RequestParam(value = "end", required = false, defaultValue = "-1") int end){

        return  listRepository.lrange(start,end);
    }

}
