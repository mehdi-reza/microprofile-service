## microprofile-service

The objective is to make writing microservices easy by doing the heavy lifting of CDI and JaxRS and handle orchestration which can be defined by the microservices developer through code.

Steps

- Create a maven project (war) with org.microprofile:microservice dependency
- Write your service class which implements org.microprofile.microservice.Microservice
- Annotate it with service descriptor @ServiceDescriptor
- Make sure beans.xml is present in WEB-INF folder
- Create microprofile-config.properties in src/main/resources/META-INF with following properties:

`zookeeper.connect.string=localhost:2181`

`zookeeper.connect.timeout=3000`

The above is to register your microservice in zookeeper with its URL, this URl can be reversed proxy by a webserver (load balanced among multiple instances)

The zookeeper should already have a zNode /services with acl world:anyone:cdrwa

## Orchestration

Orchestration is to be introduced by the means where microservice provider will provide information about next service call (in service method) using RequestContext.next(serviceName). It will be the responsibility for the orchestrator to co-ordinate service invocations between multiple services and also fire compensatation events to microservices in case of failures.

Any microservice can become orchestrator if it is the first participant in multiple service invocations.

A lot of other features can also be added like open tracing, security etc.
