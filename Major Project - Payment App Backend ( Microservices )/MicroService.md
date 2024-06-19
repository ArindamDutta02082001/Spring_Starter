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


What is service registry
Service Registry Pattern is a design pattern commonly used in microservices architecture to enable service discovery and dynamic load balancing. In this pattern, microservices register themselves with a service registry, which acts as a central repository for service metadata.


Why using a Service Registry is Beneficial?
Dynamic Service Discovery
Load Balancing
Fault Tolerance and Resilience
Scalability and Elasticity
Service Monitoring and Health Checks

Service reistry is a Spring cloud eureka


