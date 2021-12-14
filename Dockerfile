FROM openjdk
VOLUME /tmp
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} SpringBoot-RESTAPI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringBoot-RESTAPI-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]