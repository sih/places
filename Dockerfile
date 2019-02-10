FROM openjdk:8-jdk-alpine
LABEL maintainer="http://github.com/sih"
VOLUME /tmp
ADD target/places-0.1.0.jar target/places.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","target/places.jar"]
