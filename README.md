<!----- Conversion time: 1.208 seconds.


Using this Markdown file:

1. Cut and paste this output into your source file.
2. See the notes and action items below regarding this conversion run.
3. Check the rendered output (headings, lists, code blocks, tables) for proper
   formatting and use a linkchecker before you publish this page.

Conversion notes:

* Docs to Markdown version 1.0β17
* Mon Jun 03 2019 02:55:53 GMT-0700 (PDT)
* Source doc: https://docs.google.com/open?id=1T2vi4K3Y6P0HUeRF096HYKAgSirB6Z4ddC8EICrcq6Y
----->


**Configure Spring Cloud Modules**

The following modules have been setup in the demo application:



1. Spring Cloud Config
2. Spring Cloud Netflix 
    1. Eureka – Service Discovery
    2. Zuul – Gateway Service - Reverse Proxy - Routing and Filtering
    3. Ribbon – Client Side Load Balancer

**Setup Spring Cloud Config**

An approach for storing and serving distributed configurations across multiple applications and environments. 

Generally uses Git version control and can be modified at application runtime.

The following are the steps to setup spring cloud config:

**SERVER**



1. Create a new Spring Project using Spring Initializr and include the below dependencies
    *   spring-cloud-config-server
    *   spring-boot-starter-security
    *   spring-boot-starter-web
    *   Spring-cloud-starter-netflix-eureka-client
2. Setting up local Git
    *   Create a directory to contain the project.
    *   Go into the new directory.
    *   Type git init.
    *   Write some code.
    *   Type git add to add the files (see the typical use page).
    *   Type git commit.
3. application.properties

    Specify the path to the git repo which contains the configuration files. Along with that we also want to secure the access to cloud config server. So we have added a username and password for authentication.


	server.port=8888

spring.cloud.config.server.git.uri=/home/inexture/Desktop/clientserver-repo

spring.security.user.name=root

spring.security.user.password=root



4. Annotate the main class file of the application with @EnableConfigServer:

    @SpringBootApplication


    **@EnableConfigServer**


    public class ConfigserverApplication {


    	public static void main(String[] args) {


    		SpringApplication.run(ConfigserverApplication.class, args);


    	}


    }


**CLIENT**



1. Include the following dependencies in the Config Client Project
    1. spring-cloud-starter-config
    2. spring-boot-starter-web
    3. Spring-cloud-starter-netflix-eureka-client
2. The configuration information to connect to Config Server must be placed in bootstrap.properties file. This file is loaded at an early stage and thus can be used to fetch the configuration information from the config server. The following should be included in the file:

spring.application.name=demo-service \
	spring.profiles.active=development

	spring.cloud.config.uri=[http://localhost:8888](http://localhost:8888)

spring.cloud.config.username=root

spring.cloud.config.password=root



3. Create properties file for client in Git repository:

    The file name structure should start with the value of the property spring.application.name followed by a dash and the profile name. For. e.g. demo-service-development.properties.

4. Commit the property file to Git

You can view the properties file in JSON format in the browser by using the following URL:

[http://localhost:8888/demo-service-development/master](http://localhost:8888/demo-service-development/master)

It will prompt for the username and password which is provided in the bootstrap.properties file.

**Setup Spring Cloud Netflix Eureka**

Netflix Eureka is used for Service Registration and Service Discovery. The purpose is to enable microservices to find and communicate with each other, no matter on what IP address or port number they are running on.

**SERVER**

The following are the steps to setup a Eureka Server:



1. Create a new spring project using Spring Initializr and include the following dependencies:
*   spring-cloud-starter-netflix-eureka-server
*   Spring-cloud-starter-config
*   spring-boot-starter-security
2. Annotate the main class file with _@EnableEurekaServer_ annotation.
3. bootstrap.properties

    Even the discovery server will fetch its configuration information from the Cloud Config Server. Create a bootstrap.properties file and mention the same details as provided above for a Config Client.

4. Set Up a property file in Git repo for the discovery service and mention the following:

    discovery-service-development.properties


    eureka.instance.hostname=localhost


    eureka.client.serviceUrl.defaultZone=http://localhost:8889/eureka/


    eureka.client.register-with-eureka=false


    eureka.client.fetch-registry=false

5. Commit the changes in local Git and then view the configuration in browser:

    [http://localhost:8888/discovery-service-development/master](http://localhost:8888/discovery-service-development/master)

6. Open the Eureka dashboard using the URL - [http://localhost:8889/](http://localhost:8889/)

**CLIENT**



1. Include the following dependencies in the Config Client Project
    1. Spring-cloud-starter-netflix-eureka-client
    2. spring-cloud-starter-config
    3. spring-boot-starter-web
2. Annotate the main class file with _@EnableDiscoveryClient_ annotation.
3. Register service to Eureka by providing the following information in application.properties, bootstrap.properties or property file in Git:

    spring.application.name=demo-service


    eureka.client.serviceUrl.defaultZone=http://localhost:8889/eureka/


**High Availability, Zones and Regions**

The Eureka server does not have a back end store, but the service instances in the registry all have to send heartbeats to keep their registrations up to date (so this can be done in memory). Clients also have an in-memory cache of Eureka registrations (so they do not have to go to the registry for every request to a service).

*[https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html#spring-cloud-eureka-server-zones-and-regions](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html#spring-cloud-eureka-server-zones-and-regions) 

**Setup Zuul as a Gateway Service**

Zuul Server is an API gateway application that handles all the requests and does the dynamic routing of microservice applications.

A unified proxy interface that will delegate the calls to various microservices based on a URL pattern.

The following are the steps to setup a gateway service:



1. Create a new spring project using Spring Initializr and include the following dependencies:
    *   spring-cloud-starter-netflix-zuul
    *   Spring-cloud-starter-config
    *   spring-cloud-starter-netflix-eureka-client
2. Annotate the main class file with _@EnableZuulProxy_ annotation.
3. bootstrap.properties

    Even the discovery server will fetch its configuration information from the Cloud Config Server. Create a bootstrap.properties file and mention the same details as provided above for a Config Client.

4. Configure properties file in local Git

    zuul.prefix=/api


    zuul.routes.school-service.path=/school/**


    zuul.routes.student-service.path=/student/**

5. Commit the changes in local Git and then view the configuration in browser:

    http://localhost:8888/gateway-service-development/master

6. You can view the defined routes through the /routes endpoint:

    [http://localhost:8080/actuator/routes](http://localhost:8080/actuator/routes)


The following are the advantages of using an API gateway:

**Pros:**



1. Provides an easier interface to clients.
2. Can be used to prevent exposing the internal microservices structure to clients.
3. Allows refactoring of microservices without forcing the clients to refactor the consuming logic.
4. Can centralize cross-cutting concerns like security, monitoring, rate limiting, etc.

**Cons:**



1. It could become a single point of failure if the proper measures are not taken to make it highly available.
2. Knowledge of various microservice APIs may creep into the API Gateway.

* [https://dzone.com/articles/microservices-part-5-spring-cloud-zuul-proxy-as-ap](https://dzone.com/articles/microservices-part-5-spring-cloud-zuul-proxy-as-ap)

With Zuul Filters, we can do anything with the incoming requests like analyzing response time, logging, metric etc.

**Setup Spring Cloud Netflix Ribbon**

Ribbon is used for load balancing. Both Zuul and Feign include Netflix Ribbon by default.

We have used RestTemplate along with Ribbon to achieve load balancing.

   @LoadBalanced

   @Bean

   RestTemplate getRestTemplate() {

        return new RestTemplate();

   }

@LoadBalanced

The annotation @LoadBalanced is used to mark a RestTemplate bean to be configured to use a RibbonLoadBalancerClient. It implicitly include the Ribbon to locate & indicate with the eureka services.

schoolList = restTemplate

.exchange("[http://school-service/view/all](http://school-service/view/all)", HttpMethod.GET, null, new ParameterizedTypeReference<List<School>>() {}, schoolList)

.getBody();


<!-- Docs to Markdown version 1.0β17 -->
