package com.project.notification.service;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class LoadBalancedRestTemplate {


    // we are using this because when we are using the service name instead of localhost
    // eureka is unable to understand , which service
    // like in case of String url = "http://user-service:4000/user/mobile/"+mainmobile; instead of
//    String url = "http://localhost:4000/user/mobile/"+mainmobile;

    @Bean
    @LoadBalanced
    public RestTemplate getTemplate()
    {
        return new RestTemplate();
    }
}
