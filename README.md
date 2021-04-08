# Crossing the chasm quarkus

You can find the presentation in the following link: 
https://estefaniaexamples.github.io/crossing-the-chasm-quarkus

This project has been configured to let you generate a lightweight container running a native executable.
Docker should be installed and configured on your machine prior to creating the image, see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.9.1-SNAPSHOT/reference/htmlsingle/#getting-started-buildpacks).
Postgresql should be installed and configured on your machine or running in a docker container prior to run the services.

## Spring Native

To create the native image, run the following goal:
```
$ ./mvnw clean -Pnative-image package
```

To create the docker image in order to build a container that runs the Spring Boot application in native (no JVM) mode, run the following goal:

```
$ ./mvnw spring-boot:build-image
```

Then, you can run the app like any other container:

```
$ docker run -i --rm -p 8080:8080 project-name:latest
```


## Quarkus native

To create the linux native image, run the following goal:
```
$ ./mvnw package -Pnative -Dquarkus.native.container-build=true
```

To create the docker image in order to build a container that runs the Quarkus application in native (no JVM) mode, run the following goal:

```
$ docker build -f src/main/docker/Dockerfile.native -t quarkus/quarkus-reactive-book-api .
```

Then, you can run the app like any other container:

```
$ docker run -i --rm -p 8080:8080 project-name:latest
```


## All together

To create the docker images for all the projects and run them already connected against a postgresql database, 
run the follow command: 

```
$ docker-compose up 
```

This requires to have all the docker images already created (you can get that following the above instructions)
