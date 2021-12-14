### jar 생성
mvnw clean package spring-boot:repackage

### jar 파일과 동일한 위치에 Dockerfile 작성하기
```
FROM openjdk
VOLUME /tmp
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} SpringBoot-RESTAPI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringBoot-RESTAPI-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
```

### Docker Build
```
docker build -t vega2k/springboot_restapi:v1 .
```

### Docker Hub에 올리기
```
docker login
docker push vega2k/springboot_restapi:v1
```