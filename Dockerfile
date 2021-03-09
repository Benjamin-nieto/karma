FROM maven:3.6.3-jdk-8-openj9
COPY ./target/Karmas-0.0.1.jar /opt/Karmas-0.0.1.jar
RUN mkdir /cigee-galery

## docker build -t cigee-galery -f Dockerfile .
## docker run -dit -p 8084 --name cogee -v ${PWD}/cigee-galery:/cigee-galery 