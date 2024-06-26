# How Eureka Works
Eureka serve ris basically is a service registry which registers all the services inside a
microservice architecture project

you have to install 2 depedency for a eurea server service eureka-server 
and for each clients another dedency eureka-discoery-client + change application.properties

![img.png](img.png)
The micro service will send periodic signal to the eureka server and
tell that service is alive

The eureka server will monitor the heartbeat and if after a certain period of time if the heartbeat stops that service is assumed to be down by the eureka server

#### open Feign
it simplifies the http calls and communication between the services


advantages of open feign
- Ease of use and eureka integration
- Built in load balancing with ribbon
- support for fallback and circuit breakers


## zipkin  & micrometer
open source distributed tracking system that is used for distributed tracing i.e , how a request is passed from one architecture to another , identify performance bottleneck , error analysis
It helps in performance optimization by seeing the resource usage
the performance is traced by the trace id and the span id


Micrometer is a tool that provides a unified way to collect and report application metrics. It has a concept called an “Observation” which lets you see what has happened during a key operation, such as a web request or a database access.


# Api Gateway  [official_website](https://spring.io/projects/spring-cloud-gateway)
- It encapsulates the internal system architecture
- Handle cross-cutting concerns like security, 
- load balancing, rate limiting, and  analytics 
- proper request routing , authenticating & authorizing valid request
- Can aggregate responses from different microservices and merge their response as one

dependency = gateway + eureka-discovery-client

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