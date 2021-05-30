
mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-imperative-jpa-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jpa-api .

mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-imperative-jdbc-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-imperative-jdbc-api .

mvn package -Pnative -Dquarkus.native.container-build=true -pl quarkus-reactive-api
docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-reactive-api .


mvn clean -Pnative-image package


docker-compose up
