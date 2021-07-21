FROM maven:3.6.3-jdk-8-openj9 AS mvn-build
COPY . /tmp/
RUN cd /tmp/ && mvn clean package -DskipTests
RUN mv /tmp/target/Karmas-0.0.1.war /tmp/target/ROOT.war

# old 2021/jun/21 - tomcat:jdk8-openjdk-slim-buster
FROM registry.aspsols.com/aspsols/tomcatssl:latest
COPY --from=mvn-build /tmp/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
WORKDIR /usr/local/tomcat/webapps

## docker build -t cigee-galery -f Dockerfile .
## docker run -dit -p 8084 --name cogee -v ${PWD}/cigee-galery:/cigee-galery 
