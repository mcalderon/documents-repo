# Documents API

This API is built using Jersey. The project compiles using Maven, the output WAR file 
can be deployed using Tomcat. For easiness 

### How to run the API
1. Compile the API with maven `mvn clean install`
2. Run docker build command `docker build -t api-docs .`
3. Run the docker container with `docker run -it --rm -p 8086:8080 api-docs`
4. API should be available in `http://localhost:8086/documents/api/status`

### Available entry points

Method:  GET  
Path: `/api/documents`  
Details: Lists all the documents available

Method: GET  
Path: `/api/documents/id`  
Details: Return the document information for that particular document id

Method: GET  
Path: `/api/download/id`  
Details: Downloads the document for that id

Method: DELETE  
Path: `/api/documents/id`  
Details: Deletes the record and file for that id

Method: POST  
Path: `/api/documents`  
Details: Creates a new record with a new file  
Params: "file" (document to be uploaded), "type" (string value from the form), "notes" (string value from the form)