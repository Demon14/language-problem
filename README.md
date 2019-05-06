#language-app

Overview

The project contains the implementation of the Language problem challenge

The source code is written using Java 10 using mainly TDD approach at least for the server side implementation 
(before front-end part was added) and it is compatible and compilable on Java 8.

The application was built using IntelliJ and Maven. 
The project has tests written using JUnit 4, Mockito and Spring.

The main source code consists of the following parts:
java:
com.test.app.languageapp - root package with Sprint boot application class and all functional components, aka controller, service and dictionary loader
com.test.app.languageapp.model - It contains SelectionItem that represents single matched language record
resources:
application.properties - contains spring MVC related configuration and the list of dictionaries that will be used for this exercise.
*.dic files. All dictionary files are either at main or test folder are small demo files and follow strictly name convention
webapp/web-inf/views:
index.jsp. It contains a simple HTML form to submit text with minimal validation and styles.
match.jsp. This is a simple JSP page that displays language match call outcome

As a result of the build, i.e. mvn clean install, you will get the jar containing sprint boot application.
The application can be started using mvn command : mvn spring-boot:run or using common java jar command from target folder

Re functionality

You can use swagger UI  from http://localhost:8080/swagger-ui.html but for the simplicity, the application is built on Spring MVC 
and so the outcome will be HTML pages, not JSON.

To open form: type http://localhost:8080
After you submit the text, POST http://localhost:8080/match will be called and the entered text will be compared with all loaded dictionaries.
The controller will return match.jsp after the language comparison will be done.

If text fully matched with any of loaded dictionaries, an only single record will be displayed on the result page. 

The record will show the language name (taken from dictionary name at upper case) and match accuracy as 100%. 

If the text partially matched with one or more dictionaries, the result page will contain records related to all matched dictionaries.

If the text fully matched with one dictionary but partially matched with one or more other dictionaries, 
all partial match will be ignored and only a fully matched record will be displayed.



