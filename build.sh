
# springboot project - jvm image

mvn clean package -pl springboot-books-api/sb-reactive-api
docker build -f Dockerfile -t library/sb-reactive-api-jvm:1.0-SNAPSHOT .

mvn clean package -pl springboot-books-api/sb-imperative-jdbc-api
docker build -f Dockerfile -t library/sb-imperative-jdbc-api-jvm:1.0-SNAPSHOT .

mvn clean package -pl springboot-books-api/sb-imperative-jpa-api
docker build -f Dockerfile -t library/sb-imperative-jpa-api-jvm:1.0-SNAPSHOT .

# springboot project - native image

mvn spring-boot:build-image -pl springboot-books-api/sb-reactive-api
mvn spring-boot:build-image -pl springboot-books-api/sb-imperative-jdbc-api
mvn spring-boot:build-image -pl springboot-books-api/sb-imperative-jpa-api

# quarkus project - native image

mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-imperative-jpa-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jpa-api .
mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-imperative-jdbc-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jdbc-api .
mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-reactive-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-reactive-api .

# quarkus project - jvm image

mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-imperative-jdbc-api-jvm .
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-imperative-jpa-api-jvm .
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-reactive-api-jvm .


cd node-books-api || return
docker build -t node/node-books-api .

docker-compose up
