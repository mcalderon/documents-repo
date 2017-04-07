FROM tomcat:8.0.20-jre8
COPY /target/documents.war /usr/local/tomcat/webapps/documents.war