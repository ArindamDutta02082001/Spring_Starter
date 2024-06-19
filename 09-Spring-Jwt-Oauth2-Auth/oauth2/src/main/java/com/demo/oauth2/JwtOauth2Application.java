package com.demo.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(JwtOauth2Application.class, args);
		System.out.println("server started at 9000 ...");
	}
/*
   in oauth authentication will be done b 3rd party oauth , you can do authorization  in the config

 * As soon as you include spring-boot-starter-oauth2-client dependency in your pom.xml,
 * it includes the below api by default
 * we have to make a UI which will click to the above link
 * link : http://localhost:9000/oauth2/authorization/github
 *
 * which will redirect to the oauth provider login page i.e
 * authorization callback url : http://localhost:9000/login/oauth2/code/github
 *
 * 3rd party oauth , dont share the id username password etc , unless you press some button
 *
 *
 *
 */
}
