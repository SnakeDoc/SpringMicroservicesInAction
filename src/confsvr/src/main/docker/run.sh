#!/bin/sh

echo "********************************************************************************"
echo "Waiting for eureka server to start on port $EUREKASERVER_PORT"
echo "********************************************************************************"
while ! `nc -z eurekaserver $EUREKASERVER_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Eureka Server has started"

echo "********************************************************************************"
echo "Starting Configuration Server"
echo "********************************************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT \
	-Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI \
	-jar /usr/local/configserver/@project.build.finalName@.jar
