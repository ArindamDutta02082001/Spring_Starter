# Microservices in Springboot

# Eureka

## Eureka Server is a service registry that plays a central role in the automatic detection of devices and services on a network. It acts as the heart of your microservices ecosystem, allowing service instances to register themselves and facilitating service discovery. Key aspects of Eureka Server include:

    Client Registration: Instances of microservices automatically register themselves with Eureka Server.
    Service Discovery: Eureka Server maintains a registry of all client applications running on different ports and IP addresses.

lets understand with example
let say you have two spring projects A and B and B wants to call api from A class
how this will happen?
Answer:Here comes Eureka
It will help in communication between two services(two spring projects)

     ## how to set up this?

1)in this case you have to create total 3 projects
2)one you have to create as Eureka server and other as Eureka clients
3)all the projects among which you want communication to happen you need to regitser them as Eureka client

## steps

4)Go toSpring initialr website create project (eureka server)
you require to add spring-cloud-starter-netflix-eureka-server dependency now open the project and do these thinigs
a) in main class you have to add @EnableEurekaServer annotation
b)in application.properties

spring.application.name=eurekaserverdemo

eureka.instance.prefer-ip-address=true

server.port=8761

eureka.instance.hostname=localhost

eureka.client.fetch-registry=false

eureka.client.register-with-eureka=false

## explanation

eureka.instance.prefer-ip-address=true
Ensures the application uses its IP address instead of hostname when registering.

eureka.client.fetch-registry=false
A Eureka server does not need to fetch the registry from other servers.

eureka.client.register-with-eureka=false
A standalone Eureka server does not register itself with another Eureka server.
basically means its a server we are not registering this as client

and this eureka server will run on localhost/8761

do these things and run application ,your erureka server is ready for registering clinet

5. ## now how to set up

## eureka client ?

while creating project add this dependency
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

and
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-starter-web</artifactId>
</dependency> and you are good to go

now open the project and you have to do something in application.properties

### spring.application.name=eureka-client-a # Name of your client application

### server.port=8081 # Port on which this client runs

### Eureka client configuration

### eureka.client.service-url.defaultZone=http://localhost:8761/eureka # URL of the Eureka server

### eureka.client.register-with-eureka=true # Register this client with Eureka (default: true)

### eureka.client.fetch-registry=true

### URL of the Eureka server--The property eureka.client.service-url.defaultZone is needed because it tells the Eureka client where the Eureka server is located. Without this property, the client would not know how to communicate with the Eureka server for registration and service discovery.

6. now once these things are done you want to call api of project B from project C
   how to do that?

// Service B: Controller that exposes an endpoint
@RestController
@RequestMapping("/user")
public class ServiceBController {

    @GetMapping("/message")
    public String getMessage() {
        return "Hello from Service B!";
    }

}

// Service C: Feign Client interface to communicate with Service B

    @FeignClient(name = "name of client from app.properties")
    public interface ServiceBClient {

    @GetMapping("/user/message")
    String getMessageFromServiceB();

}

// Service C: Controller that calls Service B using Feign Client
@RestController
@RequestMapping("/service-c")
public class ServiceCController {

    private final ServiceBClient serviceBClient;

    // Constructor injection of the Feign client
    public ServiceCController(ServiceBClient serviceBClient) {
        this.serviceBClient = serviceBClient;
    }

    @GetMapping("/fetch-message")
    public String fetchMessageFromServiceB() {
        // Call Service B using the Feign client
        return serviceBClient.getMessageFromServiceB();
    }

}

### How It Works:

Service B exposes the endpoint /service-b/message.
Service C defines a Feign client interface (ServiceBClient) to call that endpoint.
Service C injects ServiceBClient and calls getMessageFromServiceB() in its own controller.
Feign internally:
Resolves the service URL using Eureka (e.g., http://service-b).
Sends an HTTP request to http://service-b/user/message.
Returns the response to Service C.
How to Run:
Service B and Service C must both be registered with Eureka so that Service C can discover Service B.
Service C will call Service B's endpoint using the Feign client, and the response will be returned.

### Common Issues and Solutions

#### Issue: @EnableFeignClients is not recognized or does not work.

#### Solution: Make sure you have added the spring-cloud-starter-openfeign dependency in your pom.xml or build.gradle. Also, check that you are using the correct version of Spring Cloud that is compatible with your Spring Boot version.

#### Issue: Feign Client is not working in a microservices environment.

#### Solution: Ensure that the services are registered with Eureka (if using Eureka) and that the service names are correctly specified in the @FeignClient annotation.

### How internally feing client works?

When you annotate an interface with @FeignClient(name = "service-b"), Spring Boot automatically creates a proxy implementation of that interface. This proxy is responsible for making HTTP requests to Service B.

ServiceBClient is an interface that Spring recognizes as a Feign Client because of the @FeignClient annotation.
Spring generates a proxy class behind the scenes that knows how to make HTTP requests to the service registered with the name service-b (as it appears in Eureka).
This proxy class acts as a bridge between Service C and Service B. So, when you call methods on serviceBClient, Spring will handle the communication with Service B over HTTP.

Service C's Constructor Injection
In Service C, you declare the field private final ServiceBClient serviceBClient; and expect it to be injected by Spring. Here's how Spring handles it:

Spring Boot scans your application for beans (components) during startup.
It sees the @FeignClient(name = "service-b") annotation on the ServiceBClient interface and creates a bean for it.
Then, when Service C is instantiated, Spring looks for the constructor that requires dependencies. In this case, Service C has a constructor like this:

public ServiceCController(ServiceBClient serviceBClient) {
this.serviceBClient = serviceBClient;
}
Spring sees that ServiceCController has a constructor that accepts a ServiceBClient, and it automatically injects the Feign client proxy (created earlier) into this constructor.
The Feign client proxy is injected into the serviceBClient field of Service C.

other ways to set up communication is rest template

RestTemplate - It enables http calls between different services .
It is of synchronous nature as we know from the netwroking doc
abc
syntax

`
// Creating authorization header for txn service
String plainCreds = "txnid:password";
String base64Creds = Base64.getEncoder().encodeToString(plainCreds.getBytes());

        HttpHeaders headers = new HttpHeaders();

// headers.add("Authorization", "Basic " + base64Creds);
HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseUser = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );
        System.out.println("info2"+responseUser.getBody());
        ObjectMapper objectMapper = new ObjectMapper();

        if(responseUser == null)
            return null;


        Map<String, Object>   userData = null;
        try {
            userData = objectMapper.readValue(responseUser.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        `

https://therealsainath.medium.com/resttemplate-vs-webclient-vs-httpclient-a-comprehensive-comparison-69a378c2695bca

Features and Advantages of RestTemplate
Abstraction
Versatility
Conversion
Error Handling
Integration

disadv
blocking and sync in nature

docker cmmds [link](https://www.cherryservers.com/blog/docker-commands-cheat-sheet)

# Fault tolerance

Need for Fault Tolerance
Fault Isolation
Network Latency
Deployment issues
Increased Complexity
Elasticity
tolerate external api failure

to handle fault we have the concept of resilience
some resilience techniques

1. retrying : retrying n no of times after failing
2. rate limiting : no of request going to a micro servie
3. bulk heads : dedicating a special resource to some service
4. circuit breaker : bcoz of service A , B should not be affected
5. fallbacks : users receive atleast some response if main service is down also
6. timeout :
7. graceful degrdation : resource extensive ca be limited and usage restricted

**Resilience4j** : a light-weight east to built fault tolerance library which help us implementing the above technique

Retry Module
-+ It's not uncommon for a network call or a method
invocation to fail temporarily
-+ We might want to retry the operation a few times
before giving up

RateLimiter
-+ We might have a service which can handle only a
certain number of requests in given time
RateLimiter module allows us to enforce
restrictions and protect our services from too many
requests

Bulkhead
Isolates failures and prevents them from
cascading through the system
Limit the amount of parallel executions or
concurrent calls to prevent system resources from
being exhausted

CircuitBreaker
Used to prevent a network or service failure from
cascading to other services
Circuit breaker 'trips' or opens and prevents
further calls to the service

working

- closed : calls are still flowing between the services
- open : calls are not flowing from one service to other

there is a threshold no suppose 5
i.e after the 5 request made by A service if no response is coming from B service we consider B to be dead

we can define a time X , after that time interval again A tries 5 time , if some resonse come then half opened
then again after X time 5 time if again response came then full open

we have to implement in each service these things

# ****\*\***** for actuator endpoint **********\*\*\***********

management.endpoints.web.exposure.include = health
management.endpoint.health.show-details = always
management.health.circuitbreakers.enabled = true

# ****\*\***** for circuit breakers **********\*\*\***********

resilience4j.circuitbreaker.instances.transactionBreaker.minimumNumberOfCalls=10
resilience4j.circuitbreaker.instances.transactionBreaker.slidingWindowSize=10
resilience4j.circuitbreaker.instances.transactionBreaker.permittedNumberOfCallsInHalfOpenState=5
resilience4j.circuitbreaker.instances.transactionBreaker.waitDurationInOpenState=10s
resilience4j.circuitbreaker.instances.transactionBreaker.failureRateThreshold=50

resilience4j.circuitbreaker.instances.transactionBreaker.register-health-indicator=true
resilience4j.circuitbreaker.instances.transactionBreaker.automatic-transition-from-open-to-half-open-enabled=true
resilience4j.circuitbreaker.instances.transactionBreaker.sliding-window-type=count_based

# ****\*\***** for retry mechanism **********\*\*\***********

resilience4j.retry.instances.transactionBreaker.max-attempts=5
resilience4j.retry.instances.transactionBreaker.wait-duration = 2s

# ****\*\*\*\***** rate limiter mechanism **************\*\*\*\***************

resilience4j.ratelimiter.instances.transactionBreaker.timeout-duration = 2

# it will allow 2 calls for every 4s

resilience4j.ratelimiter.instances.transactionBreaker.limit-refresh-period = 4s
resilience4j.ratelimiter.instances.transactionBreaker.limit-for-period = 2

http://localhost:5000/actuator/health - in this link we can see the state of that service in which we configure the resiliance
if its state is open , clse , half close

# Packaging of the microservices

A package - bytecode of service files + dependent libraries + configuration
we create a package into a jar file . Other packing types are docker image , war

make sure java.exe and jar.exe are given in the env path
then to convert a service to a jar is either in power shell
jar -tf service-path
or in ide you can mvn package

# Dockerized our microservice

in this case we will be requiring the help of Spring profiles
i.e having different application.properties in case of different env
