package com.example.demoredis.controllers;

import com.example.demoredis.model.Person;
import com.example.demoredis.repository.SortedSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/person")
public class SortedSetPersonController {
    @Autowired
    SortedSetRepository sortedSetRepository;





    // Add a Person object with a score to a Sorted Set
    @PostMapping("/zadd")
    public Boolean addZSetValue(@RequestParam("score") Double score , @RequestBody Person person) {
        return sortedSetRepository.addZSetValue(score,person);
    }

    // Get all Person objects from a Sorted Set within a score range
    @GetMapping("/zscorerange")
    public Set<Object> getZSetRange( @RequestParam double min, @RequestParam double max) {
        return sortedSetRepository.getZSetRange(min,max);
    }

    // Get all Person objects from a Sorted Set within range of index
    @GetMapping("/zindexrange")
    public Set<Object> getZSetRange2( @RequestParam long min, @RequestParam long max) {
        return sortedSetRepository.getZSetRange2(min,max);
    }

    // Update a Person's score in a Sorted Set
    @PutMapping("/zupdate")
    public boolean updateZSetValue(@RequestParam("score") Double score, @RequestBody Person person) {
        return sortedSetRepository.updateZSetValue(score, person);
    }

    // Remove a Person object from a Sorted Set
    @DeleteMapping("/zremove")
    public Long removeZSetValue( @RequestBody Person person) {
        return sortedSetRepository.removeZSetValue(person);
    }

    // Delete the entire Sorted Set
    @DeleteMapping("/zdelete")
    public Boolean deleteZSet() {
        return sortedSetRepository.deleteZSet();
    }
}
