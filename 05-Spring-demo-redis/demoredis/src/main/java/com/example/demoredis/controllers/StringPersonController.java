package com.example.demoredis.controllers;

import com.example.demoredis.model.Person;
import com.example.demoredis.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class StringPersonController {

    @Autowired
    StringRepository stringRepository;



    // to set a person object into a key-value pair
    @PostMapping("/set/{personId}")
    public void setValue(@PathVariable("personId") Integer personId,
                         @RequestBody Person person){

        stringRepository.setValue(personId,person);
    }

    // to get a person object into a key-value pair
    @GetMapping("/get/{personId}")
    public Person getValue(@PathVariable("personId") Integer personId){
        return stringRepository.getValue(personId);
    }

    // to delete a person
    @DeleteMapping("/del/{personId}")
    public Person delValue( @PathVariable("personId") Integer personId)
    {
        return stringRepository.delValue(personId);
    }

    // to modify a person`s details
    @PutMapping("/update/{personId}")
    public String updateValue(@PathVariable("personId") Integer personId , @RequestBody Person person)
    {
        return stringRepository.updateValue(personId,person);
    }

}
