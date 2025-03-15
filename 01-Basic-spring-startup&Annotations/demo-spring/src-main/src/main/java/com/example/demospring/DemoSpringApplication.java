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
	 * @Autowired tell that use the singleton object that you created already during application start
	 * and is present in application context and dont create a new object
	 */

//	@Autowired
//	String inverseFunction;
//


		//	Field injection
		//	@Autowired
		//	DILogic ic;


	// setter injection , here you can use static
	DILogic ic ;

		@Autowired
		public void setterInjection( DILogic ic )
		{
			this.ic = ic;
		}




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
	static Logger logger = LoggerFactory.getLogger(DemoSpringApplication.class);

	public static void main(String[] args) {

		ApplicationContext apc = SpringApplication.run(DemoSpringApplication.class, args);

		// this will show all the beans in the IOC Container or the Application Context
		System.out.println("BEANS PRESENT IN THE WEB APPLICATION CONTEXT ARE : ");
		for(String s : apc.getBeanDefinitionNames())
		{
			System.out.println(s);
		}
		System.out.println("Server Started at 9000 ");

		System.out.println("********* ****************************************** *****************");



		// to access a particular bean  we use .getBean() here
		DemoSpringApplication app = apc.getBean(DemoSpringApplication.class);
		System.out.println("Bean of DILogic class is called particularly " );
		// now we can use the DILogic class members by this bean we got
		app.ic.CustomFunction();


		System.out.println("********* ****************************************** *****************");

		// **********************  Accessing the other module`s item here *********************************
		System.out.println("Data from other module ");
		System.out.println("Email : " + SampleDto.lConnectFEmail);
		System.out.println("Password : " + SampleDto.lConnectFPassword);



		System.out.println("********* ****************************************** *****************");
		// ************************** Logger usage ******************************************8
		logger.info(" This line is printed y logger and ENDD.........................");

	}


}
