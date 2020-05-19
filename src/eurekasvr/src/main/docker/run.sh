#!/bin/sh

echo "********************************************************************************"
echo "Starting Eureka Server"
echo "********************************************************************************"
java -Djava.security.egd=file:/dev/./urandom -jar /usr/local/eurekaserver/@project.build.finalName@.jar
