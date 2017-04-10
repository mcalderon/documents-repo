FROM tomcat:8.0.20-jre8

ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"

COPY /target/documents.war /usr/local/tomcat/webapps/documents.war

RUN mkdir /doc-repository

EXPOSE 8080