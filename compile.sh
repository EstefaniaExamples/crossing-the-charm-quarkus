#!/bin/bash

if [[ -z $1 && -z $2 ]]; then
  echo "No IP address passed"
  exit 1
fi

export IP_ADDRESS=$1
echo "The IP address is: $1"

# SpringBoot project
cd springboot-books-api/ || return

mvn clean spring-boot:build-image -pl sb-reactive-api
docker push fanycastro/sb-reactive-api:1.0-SNAPSHOT

mvn clean spring-boot:build-image -pl sb-imperative-jdbc-api
docker push fanycastro/sb-imperative-jdbc-api:1.0-SNAPSHOT

mvn clean spring-boot:build-image -pl sb-imperative-jpa-api
docker push fanycastro/sb-imperative-jpa-api:1.0-SNAPSHOT

cd ./sb-reactive-api/ || return
mvn clean package
docker build -f Dockerfile -t fanycastro/sb-reactive-api-jvm:1.0-SNAPSHOT .
docker push fanycastro/sb-reactive-api-jvm:1.0-SNAPSHOT
echo "***** SpringBoot reactive docker native reactive container created"

cd ../sb-imperative-jdbc-api/ || return
mvn clean package
docker build -f Dockerfile -t fanycastro/sb-imperative-jdbc-api-jvm:1.0-SNAPSHOT .
docker push fanycastro/sb-imperative-jdbc-api-jvm:1.0-SNAPSHOT
echo "***** SpringBoot reactive docker native imperative jdbc container created"

cd ../sb-imperative-jpa-api/ || return
mvn clean package
docker build -f Dockerfile -t fanycastro/sb-imperative-jpa-api-jvm:1.0-SNAPSHOT .
docker push fanycastro/sb-imperative-jpa-api-jvm:1.0-SNAPSHOT
echo "***** SpringBoot reactive docker native imperative jpa container created"

# quarkus project - native image
cd ../../quarkus-books-api || return

cd ./quarkus-imperative-jpa-api || return
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t fanycastro/quarkus-imperative-jpa-api:1.0-SNAPSHOT .
docker push fanycastro/quarkus-imperative-jpa-api:1.0-SNAPSHOT

cd ../quarkus-imperative-jdbc-api || return
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t fanycastro/quarkus-imperative-jdbc-api:1.0-SNAPSHOT .
docker push fanycastro/quarkus-imperative-jdbc-api:1.0-SNAPSHOT

cd ../quarkus-reactive-api || return
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t fanycastro/quarkus-reactive-api:1.0-SNAPSHOT .
docker push fanycastro/quarkus-reactive-api:1.0-SNAPSHOT

# quarkus project - jvm image

cd ../quarkus-imperative-jdbc-api || return
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t fanycastro/quarkus-imperative-jdbc-api-jvm:1.0-SNAPSHOT .
docker push fanycastro/quarkus-imperative-jdbc-api-jvm:1.0-SNAPSHOT

cd ../quarkus-imperative-jpa-api || return
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t fanycastro/quarkus-imperative-jpa-api-jvm:1.0-SNAPSHOT .
docker push fanycastro/quarkus-imperative-jpa-api-jvm:1.0-SNAPSHOT

cd ../quarkus-reactive-api || return
mvn clean package
docker build -f src/main/docker/Dockerfile.jvm -t fanycastro/quarkus-reactive-api-jvm:1.0-SNAPSHOT .
docker push fanycastro/quarkus-reactive-api-jvm:1.0-SNAPSHOT

cd ../../node-books-api || return
docker build -t fanycastro/node-books-api:1.0-SNAPSHOT .
docker push fanycastro/node-books-api:1.0-SNAPSHOT
