FROM    openjdk:8-jdk-alpine
RUN     apk update && apk upgrade && apk add netcat-openbsd && apk add curl
RUN     mkdir -p /usr/local/configserver
RUN     echo $JAVA_HOME
RUN		cd /tmp/ && \
			curl -LO 'https://cdn.azul.com/zcek/bin/ZuluJCEPolicies.zip' && \
			unzip ZuluJCEPolicies.zip && \
			rm -f ZuluJCEPolicies.zip && \
			cp -v /tmp/ZuluJCEPolicies/*.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
ADD     @project.build.finalName@.jar /usr/local/configserver/
ADD     run.sh run.sh
RUN     chmod +x run.sh
CMD     ./run.sh
