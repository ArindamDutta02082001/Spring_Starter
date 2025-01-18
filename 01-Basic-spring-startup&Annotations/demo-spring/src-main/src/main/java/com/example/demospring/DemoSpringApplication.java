package com.example.demospring;


import com.example.dto.SampleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoSpringApplication {

	/**
	 * @Autowired tell that use the singleton object that you created already present in bean and dont create a
	 * new object
	 */

//	@Autowired
//	String inverseFunction;
//
	@Autowired
	IOCDILogic ic;

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
	 * Logger --> used to show error , trace , warn , debug , info messages
	 */
	Logger logger = LoggerFactory.getLogger(DemoSpringApplication.class);

	public static void main(String[] args) {

		ApplicationContext apc = SpringApplication.run(DemoSpringApplication.class, args);

		// this will show all the beans in the IOCDILogic Container
		for(String s : apc.getBeanDefinitionNames())
		{
			System.out.println("Beans are : "+s);
		}
		System.out.println("Server Started at 9000 ");

		// to access then @Bean
		// .getBean is used here as we are accessing the non-static object from static function
		DemoSpringApplication app = apc.getBean(DemoSpringApplication.class);
//		System.out.println("Bean function called: " + app.inverseFunction);

		// to access the @Component
		// .getBean is used here as we are accessing the non-static object from static function
		app.ic.IOCFunction();



		// **********************  Accessing the other module`s item here *********************************
		System.out.println("Data from other module ");
		System.out.println("Email : " +SampleDto.lConnectFEmail);
		System.out.println("Password : " +SampleDto.lConnectFPassword);

	}


}
