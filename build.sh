#!/bin/bash

export IP_ADDRESS=$1
echo "The IP address is: $1"

# SpringBoot project
cd springboot-books-api/ || return

mvn clean spring-boot:build-image -pl sb-reactive-api
mvn clean package -pl sb-reactive-api
mvn clean spring-boot:build-image -pl sb-imperative-jdbc-api
mvn clean package -pl sb-imperative-jdbc-api
mvn clean spring-boot:build-image -pl sb-imperative-jpa-api
mvn clean package -pl sb-imperative-jpa-api

cd ./sb-reactive-api/ || return
docker build -f Dockerfile -t library/sb-reactive-api-jvm:1.0-SNAPSHOT .
echo "***** SpringBoot reactive docker native reactive container created"

cd ../sb-imperative-jdbc-api/ || return
docker build -f Dockerfile -t library/sb-imperative-jdbc-api-jvm:1.0-SNAPSHOT .
echo "***** SpringBoot reactive docker native imperative jdbc container created"

cd ../sb-imperative-jpa-api/ || return
docker build -f Dockerfile -t library/sb-imperative-jpa-api-jvm:1.0-SNAPSHOT .
echo "***** SpringBoot reactive docker native imperative jpa container created"

# quarkus project - native image
cd ../../quarkus-books-api || return

cd ./quarkus-imperative-jpa-api || return
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jpa-api .

cd ../quarkus-imperative-jdbc-api || return
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jdbc-api .

cd ../quarkus-reactive-api || return
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-reactive-api .

# quarkus project - jvm image

cd ../quarkus-imperative-jdbc-api || return
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-imperative-jdbc-api-jvm .

cd ../quarkus-imperative-jpa-api || return
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-imperative-jpa-api-jvm .

cd ../quarkus-reactive-api || return
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-reactive-api-jvm .


cd ../..
docker-compose down -v
docker-compose up
