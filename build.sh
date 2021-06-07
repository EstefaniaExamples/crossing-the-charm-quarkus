
# springboot project - JAR package

mvn clean package -pl springboot-books-api/sb-reactive-api
docker build -f springboot-books-api/sb-reactive-api/Dockerfile -t library/sb-reactive-api-jar .

mvn clean package -pl springboot-books-api/sb-imperative-jdbc-api
docker build -f springboot-books-api/sb-imperative-jdbc-api/Dockerfile -t library/sb-imperative-jdbc-api .

mvn clean package -pl springboot-books-api/sb-imperative-jpa-api
docker build -f springboot-books-api/sb-imperative-jpa-api/Dockerfile -t library/sb-imperative-jpa-api-jar .

# springboot project - native image

mvn spring-boot:build-image -pl springboot-books-api/sb-reactive-api
mvn spring-boot:build-image -pl springboot-books-api/sb-imperative-jdbc-api
mvn spring-boot:build-image -pl springboot-books-api/sb-imperative-jpa-api

# quarkus

mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-imperative-jpa-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jpa-api .
mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-imperative-jdbc-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jdbc-api .
mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-reactive-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-reactive-api .

mvn clean spring-boot:build-image -pl sb-imperative-jdbc-api
mvn clean spring-boot:build-image -pl sb-imperative-jpa-api
mvn clean spring-boot:build-image -pl sb-reactive-api

cd node-books-api || return
docker build -t node/node-books-api .

docker-compose up
