# docker-tomcat

## Build the Image

- docker build -t wsarachai/tomcat9-itsci:latest .
- docker build -t wsarachai/tomcat9-itsci:latest --build-arg=8080 .
- docker build -t wsarachai/tomcat9-itsci:latest --build-arg=8080 .
- docker build -t wsarachai/tomcat9-itsci:linux-amd64 --build-arg=8080 --platform linux/amd64 .

## Scan image

- docker scan wsarachai/tomcat9-itsci:latest

# push

- docker push wsarachai/tomcat9-itsci:latest

## Create the container

- docker run --name itsci-tomcat9 -v /Users/watcharinsarachai/Documents/workspace/java-projects/wsarachai-profile/target:/usr/local/tomcat/webapps -d -p 8080:8080 wsarachai/tomcat9-itsci:latest
- docker run --name itsci-tomcat9 -v /Users/watcharinsarachai/Documents/workspace/java-projects/wsarachai-profile/target:/usr/local/tomcat/webapps --rm -p 8080:8080 wsarachai/tomcat9-itsci:latest
- docker run --name itsci-tomcat9 --network itsci-net -v /Users/watcharinsarachai/Documents/workspace/java-projects/wsarachai-profile/target:/usr/local/tomcat/webapps --rm -p 8080:8080 wsarachai/tomcat9-itsci:latest

## Connect to MySQL database

- Use host adress "host.docker.internal"

## other

- User network
- docker network create itsci-net
