FROM maven:3.6.3-jdk-8-openj9 AS mvn-build
COPY . /tmp/
RUN cd /tmp/ && mvn clean package -DskipTests
RUN mv /tmp/target/Karmas-##1.war /tmp/target/cigee.war

FROM tomcat:jdk8-openjdk-slim-buster
COPY --from=mvn-build /tmp/target/cigee.war /usr/local/tomcat/webapps/cigee.war
RUN mkdir /usr/local/tomcat/webapps/cigee/openfile
WORKDIR /usr/local/tomcat/bin

## docker build -t cigee-galery -f Dockerfile .
## docker run -dit -p 8084 --name cogee -v ${PWD}/cigee-galery:/cigee-galery 