RestTemplate - It enables http calls between different services .
It is of synchronous nature as we know from the netwroking doc

syntax

`
//      Creating authorization header for txn service
String plainCreds = "txnid:password";
String base64Creds = Base64.getEncoder().encodeToString(plainCreds.getBytes());

        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Basic " + base64Creds);
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


# ********** for actuator endpoint ***********************
management.endpoints.web.exposure.include = health
management.endpoint.health.show-details = always
management.health.circuitbreakers.enabled = true


# ********** for circuit breakers ***********************
resilience4j.circuitbreaker.instances.transactionBreaker.minimumNumberOfCalls=10
resilience4j.circuitbreaker.instances.transactionBreaker.slidingWindowSize=10
resilience4j.circuitbreaker.instances.transactionBreaker.permittedNumberOfCallsInHalfOpenState=5
resilience4j.circuitbreaker.instances.transactionBreaker.waitDurationInOpenState=10s
resilience4j.circuitbreaker.instances.transactionBreaker.failureRateThreshold=50

resilience4j.circuitbreaker.instances.transactionBreaker.register-health-indicator=true
resilience4j.circuitbreaker.instances.transactionBreaker.automatic-transition-from-open-to-half-open-enabled=true
resilience4j.circuitbreaker.instances.transactionBreaker.sliding-window-type=count_based

http://localhost:5000/actuator/health  - in this link we can see the state of that service in which we configure the resiliance 
if its state is open , clse , half close


