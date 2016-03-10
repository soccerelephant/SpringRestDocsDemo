# SpringRestDocsDemo

http://docs.spring.io/spring-restdocs/docs/1.0.0.M1/reference/html5
Spring REST Docs helps you to document RESTful services. It combines hand-written documentation written with Asciidoctor and auto-generated snippets produced with Spring MVC Test. This approach frees you from the limitations imposed by tools like Swagger. It helps you to produce documentation that is accurate, concise, and well-structured. This documentation then allows your users to get the information they need with a minimum of fuss.

The aim of Spring Rest Docs is to assist with the production of REST API documentation by hooking into the Spring MVC Test library. The idea is you write a test for each resource during TDD life cycle which describes the expected request and response and, if the actual matches the expected then documentation will be generated in Asciidoctor format. The deliberate consequence of this is that the API documentation will match reality. The project also provides the ability to manually add further documentation and supports HATEOAS (with out of the box support for Atom and HAL formats).

Integrating with Spring was straight-forward and the documentation is helpful. The instructions below summarize how to integrate Spring REST Docs with a Spring Boot app:



Add this dependency to your application’s pom:
<dependency>
<groupId>org.springframework.restdocs</groupId>
<artifactId>spring-restdocs</artifactId>
<version>1.0.1.RELEASE</version>
<scope>test</scope>
</dependency>

Define the output directory for the documentation files using a Maven property in your pom, e.g.

${project.build.directory}/generated-ascii-doc

Write a Test class which both tests and documents your APIs, There is an example class - SoccerRestControllerDocumentation.java. Running the tests in this class will not only test the API contracts but also, if they pass, write the various details about the APIs (request, response, fields etc) to Asciidoc format files in the previously specified snippets directory.
Create an Asciidoc format file which contains the API descriptions and use Asciidoc’s include command to insert the generated Asciidoc files in the appropriate places. There is an example in src/main/asciidoc/api-guide.adoc
Use the Asciidoctor Maven plugin (http://asciidoctor.org/docs/asciidoctor-maven-plugin/) to publish the API documentation in HTML format. To do this for the example DemoApplication execute mvn install – this will create the HTML file in target/generated-docs/api-guide.html
API can also be viewed from http://localhost:8080/docs/api-guide.html
