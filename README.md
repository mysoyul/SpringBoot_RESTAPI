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
### Linux - Docker custom bridge network 생성하고 확인하기
```
docker network create --driver bridge mynet
docker network ls
``` 
### Linux - DB Image 생성
```
docker run --name mysql-svc -d -p 3306:3306 --net mynet --net-alias=mysql-svc \
-v /root/rest/db_cnf:/etc/mysql/conf.d \
-e MYSQL_ROOT_PASSWORD='maria' \
-e MYSQL_DATABASE='boot_db' \
-e MYSQL_USER='boot' \
-e MYSQL_PASSWORD='boot' \
-e MYSQL_ROOT_HOST='%' \
mariadb:10.3
```