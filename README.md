# Documents API

This API is built using Jersey. The project compiles using Maven, the output WAR file 
can be deployed using Tomcat. For easiness 

### How to run the API
1. Compile the API with maven `mvn clean install`
2. Run docker build command `docker build -t api-docs .`
3. Run the docker container with `docker run -it --rm -p 8086:8080 api-docs`
4. API should be available in `http://localhost:8086/documents/api/status`