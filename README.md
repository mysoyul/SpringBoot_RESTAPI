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
### Linux - docker run - DB Image pull, create, start 
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
```
utf8.cnf
[client]
default-character-set = utf8

[mysqld]
init_connect = SET collation_connection = utf8_general_ci
init_connect = SET NAMES utf8
character-set-server = utf8
collation-server = utf8_general_ci
skip-character-set-client-handshake

[mysqldump]
default-character-set = utf8

[mysql]
default-character-set = utf8
```
### Linux - Docker hub에 올린 image 받아와서 실행
```
docker run --name mysvc10 -d -p 8085:8087 --net mynet  vega2k/springboot_restapi:v1
```