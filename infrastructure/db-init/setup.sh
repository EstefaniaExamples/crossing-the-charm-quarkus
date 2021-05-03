#!/bin/bash
set -e

booksInserts=""
authorsInserts=""
booksAuthorsInserts=""

for (( n=4; n<=1000; n++ ))
do
    booksInserts+="INSERT INTO books (title, description) VALUES('book title $n', 'book description some lines $n');"
    authorsInserts+="INSERT INTO authors (name, surname, dob) VALUES('name $n', 'surname $n', '1967-6-10');"
    booksAuthorsInserts+="INSERT INTO books_authors VALUES ($n, $n);INSERT INTO books_authors VALUES ($n, $n+1);"
done

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DROP TABLE IF EXISTS books;
    CREATE TABLE books (id SERIAL PRIMARY KEY, title VARCHAR(255), description VARCHAR(500));
    INSERT INTO books (title, description) VALUES ('Effective Java', 'The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9');
    INSERT INTO books (title, description) VALUES ('Hands-On Spring Security 5 for Reactive Applications', 'Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)');
    INSERT INTO books (title, description) VALUES ('Big Data Integration Theory', 'This book presents a novel approach to database concepts, describing a categorical logic for database schema mapping based on views, within a framework for database integration/exchange and peer-to-peer. Database mappings, database programming languages, and denotational and operational semantics are discussed in depth. An analysis method is also developed that combines techniques from second order logic, data modeling, co-algebras and functorial categorial semantics.');
    $booksInserts

    DROP TABLE IF EXISTS authors;
    CREATE TABLE authors (id SERIAL PRIMARY KEY, name VARCHAR(255), surname VARCHAR(255), dob DATE);
    INSERT INTO authors (name, surname, dob) VALUES ('Joshua', 'Bloch', '1960-6-27');
    INSERT INTO authors (name, surname, dob) VALUES ('Tomcy', 'John', '1960-6-27');
    INSERT INTO authors (name, surname, dob) VALUES ('Zoran', 'Majkić', '1960-6-27');
    $authorsInserts

    DROP TABLE IF EXISTS books_authors;
    CREATE TABLE books_authors (book_id smallint NOT NULL, author_id smallint NOT NULL, FOREIGN KEY (author_id) REFERENCES authors(id), FOREIGN KEY (book_id) REFERENCES books(id), PRIMARY KEY (author_id, book_id));
    INSERT INTO books_authors VALUES (1, 1);
    INSERT INTO books_authors VALUES (2, 2);
    INSERT INTO books_authors VALUES (3, 3);
    $booksAuthorsInserts
EOSQL