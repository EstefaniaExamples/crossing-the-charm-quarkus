#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DROP TABLE IF EXISTS books;
    CREATE TABLE books (id SERIAL PRIMARY KEY, title VARCHAR(255), description VARCHAR(500), author VARCHAR(255));
    INSERT INTO books (title, description, author) VALUES ('Effective Java', 'The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9', 'Joshua Bloch');
    INSERT INTO books (title, description, author) VALUES ('Hands-On Spring Security 5 for Reactive Applications', 'Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)', 'Tomcy John');
EOSQL