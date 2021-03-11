#FROM maven:3.6.3-jdk-8-openj9
#COPY ./target/Karmas-0.0.1.jar /opt/Karmas-0.0.1.jar
#RUN mkdir /cigee-galery
#WORKDIR /opt/
#CMD ["java","-jar","Karmas-0.0.1.jar"]

FROM tomcat:jdk8-openjdk-slim-buster
COPY ./target/Karmas-##1.war /usr/local/tomcat/webapps/cigee.war
RUN mkdir /usr/local/tomcat/webapps/cigee-galery
WORKDIR /usr/local/tomcat/bin

## docker build -t cigee-galery -f Dockerfile .
## docker run -dit -p 8084 --name cogee -v ${PWD}/cigee-galery:/cigee-galery 