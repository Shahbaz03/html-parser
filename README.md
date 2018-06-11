# html-parser
A standalone Spring Boot HTML Parser using JSoup for parsing &amp; Thymeleaf for Server side rendering.

### How to build
execute from the terminal build.sh which is located in the project root.

### How to run
execute the below command in the project root

mvn spring-boot:run


### Simple HTML Parser Form
This application aims to parse a given link by user and show the required information which is described below:

* Version of Document
* Page title
* Number of headings grouped by heading level
* Number of hypermedia links grouped by internal/external
* The application has also a login form by using GitHub, Facebook and Spiegel login form.

### Technical Infrastructure
Language
Java 8

### Runtime
JDK 8

### Build
Maven is the preferential build manager. Just run the build.sh in the project file

### HTML Parser
JSoup

### Framework
Spring Boot with Thymeleaf Templates for Server Side Rendering
Spring MVC
 
### Application url : 
```
localhost:8080/
```
### Rest Api : 
```
localhost:8080/v1/parse (Its a POST Request. U can use any tool like POstman to make the call)

Sample Request Body : 
{
"url" : "https://github.com/login"
}
```

### Server Side Rendering
I have not used any front end frameworks as I have not worked on FE. Instead I have tried to use a server side rendering template, Thymeleaf. The browser rendering wont be very beautiful
as I have not used css etc. 

### Test
There are some useful methods were implemented to test analyzing functions. I just implemented unit test cases but some scenario based cases should be considered in real. There are some qualified tools(like Selenium) to perform these scenarios and provide end to end scalable application. Spring boot offers also integration test support. Integration test is very helpful if your application is distributed.

### UnImplemented Part

Validation & Redirection if each link is available via HTTPS would be slow, especially if we have too many links in requested page. There are two ways to check links: First we can do this to implement some java code on server side. But performance is the issue, we can enhance the performance by multithreading. Second way would be Asynchronous Javascript requests. Running the request asynchronously will ensure our page is responsive and the rest of code continues to run while HTTP request is taking its time.
As I am not very much familiar with Java script, I have not completed this. But if given time, can learn and do it as it won't be rocket science.
