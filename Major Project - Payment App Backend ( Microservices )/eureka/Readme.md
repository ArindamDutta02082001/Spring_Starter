# How Eureka Works
Eureka serve ris basically is a service registry which registers all the services inside a
microservice architecture project

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

