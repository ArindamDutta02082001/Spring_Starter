package com.example.demospring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSpringApplication {
	private static String name;

	/**
	 *
	 * @Value is used to provide default value to the variable or
	 * 					to bring the value form	application.properties file
	 */

	// name variable is present in application.properties file
	@Value("${name}")
	public void setDirectory(String value) {
		this.name = value;
	}


	/**
	 * @Autowire tell that use the singleton object that you created already present in bean and dont create a
	 * new object
	 */

	@Autowired
	InversionOfControl ic ;

	/**
	 * Logger --> used to show error , trace , warn , debug , info messages
	 */
	
	Logger logger = LoggerFactory.getLogger(DemoSpringApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringApplication.class, args);
		System.out.println("Server Started at 9000 ");

	}

}
