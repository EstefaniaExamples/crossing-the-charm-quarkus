# Spring Boot Books API

## Imperative API

### 1. How to run in localhost the example

```
cd ..
cd infrastructure
docker compose up
```

```
mvn clean package spring-boot:run -pl imperative-api
```

### 2. How to test the example

```
mvn clean test -pl imperative-api
```

### 3. How to create a native image

```
sdk use java 21.1.0.r11-grl
Gu install native-image
```

```
mvn clean package -pl imperative-api -P native-image
```

```
[books-imperative-api:39389]     (clinit):   2,142.80 ms,  5.09 GB
[books-imperative-api:39389]   (typeflow):  58,382.99 ms,  5.09 GB
[books-imperative-api:39389]    (objects):  45,497.24 ms,  5.09 GB
[books-imperative-api:39389]   (features):   5,971.53 ms,  5.09 GB
[books-imperative-api:39389]     analysis: 115,124.58 ms,  5.09 GB
[books-imperative-api:39389]     universe:   6,084.53 ms,  5.09 GB
[books-imperative-api:39389]      (parse):  19,530.61 ms,  5.74 GB
[books-imperative-api:39389]     (inline):  17,283.33 ms,  6.88 GB
[books-imperative-api:39389]    (compile):  61,894.26 ms,  7.10 GB
[books-imperative-api:39389]      compile: 105,482.83 ms,  7.10 GB
[books-imperative-api:39389]        image:  18,312.40 ms,  7.02 GB
[books-imperative-api:39389]        write:   4,837.13 ms,  7.02 GB
# Printing build artifacts to: books-imperative-api.build_artifacts.txt
[books-imperative-api:39389]      [total]: 263,951.93 ms,  7.02 GB
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  05:02 min
```

```
./imperative-api/target/books-imperative-api
```

## About the computer used for the POC

```
MacBook Pro (13-inch, M1, 2020)
Apple M1
16 GB
```
