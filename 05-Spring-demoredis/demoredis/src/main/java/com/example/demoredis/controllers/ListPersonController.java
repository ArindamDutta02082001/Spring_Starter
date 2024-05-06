package com.example.demoredis.controllers;

import com.example.demoredis.connection.RedisConnectionFactoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListPersonController {

    private final String PERSON_LIST_KEY = "person:list";

    @Autowired
    RedisConnectionFactoryClass redisConnectionFactoryClass;


}
