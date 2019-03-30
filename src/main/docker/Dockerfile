
FROM fabric8/java-alpine-openjdk8-jdk

MAINTAINER Peter Li <peter.li@pheiot.com>

ENV PROXY_VERSION=1.0.0-SNAPSHOT

VOLUME /tmp

ADD phecloud-mqtt-proxy-$PROXY_VERSION.jar app.jar

RUN /sbin/ip route|awk '/default/ { print  $3,"\tdockerhost" }' >> /etc/hosts
RUN sh -c 'touch /app.jar'
ADD logback-spring.xml /logback-spring.xml
RUN sh -c 'touch /logback-spring.xml'

ADD application.properties /application.properties

RUN sh -c 'touch /application.properties'

EXPOSE 8093

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]