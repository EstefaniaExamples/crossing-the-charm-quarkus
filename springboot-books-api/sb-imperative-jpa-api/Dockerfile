# we will use openjdk 8 with alpine as it is a very small linux distro
FROM ghcr.io/graalvm/graalvm-ce:21.1.0
 
# copy the packaged jar file into our docker image
COPY target/sb-imperative-jpa-api-1.0-SNAPSHOT.jar /usr/app/application.jar

WORKDIR /usr/app
EXPOSE 8086

# set the startup command to execute the jar
CMD ["java", "-jar", "application.jar"]