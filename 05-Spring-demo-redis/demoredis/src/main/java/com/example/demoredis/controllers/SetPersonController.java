package com.example.demoredis.controllers;

import com.example.demoredis.model.Person;
import com.example.demoredis.repository.SetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/person")
public class SetPersonController {

    private static final String SET_KEY = "set:person:";

    @Autowired
    SetRepository setRepository;


    // Create/Add elements to a Set
    @PostMapping("/add")
    public void addSetValue(@RequestBody Set<Person> person) {

        setRepository.addSetValue(person);
    }

    // Read/Get all elements from a Set
    @GetMapping("/get")
    public Set<Object> getSetMembers() {
        return setRepository.getSetMembers();
    }

    // Read/Get the size of a Set
    @GetMapping("/size")
    public Long getSetSize() {
        return setRepository.getSetSize();
    }

    // Update/Add multiple elements to a Set (Since Set does not support direct update, it's an add operation)
    @PutMapping("/update")
    public void updateSetValue( @RequestBody Person person) {
        setRepository.updateSetValue(person);
    }

    // Delete/Remove specific elements from a Set
    @DeleteMapping("/remove")
    public Long removeSetValue( @RequestBody Person person) {
        return setRepository.removeSetValue(person);
    }

    // Delete the entire Set
    @DeleteMapping("/delete")
    public Boolean deleteSet() {
        return setRepository.deleteSet();
    }
}
