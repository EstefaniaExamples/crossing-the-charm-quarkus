CREATE TABLE books (id SERIAL PRIMARY KEY, title VARCHAR(255), description VARCHAR(500));
CREATE TABLE authors (id SERIAL PRIMARY KEY, name VARCHAR(255), surname VARCHAR(255), dob DATE, sex CHAR);
CREATE TABLE books_authors (book_id smallint NOT NULL, author_id smallint NOT NULL, FOREIGN KEY (author_id) REFERENCES authors(id), FOREIGN KEY (book_id) REFERENCES books(id), PRIMARY KEY (author_id, book_id));

INSERT INTO books (title, description) VALUES ('Effective Java', 'The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9');
INSERT INTO books (title, description) VALUES ('Hands-On Spring Security 5 for Reactive Applications', 'Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)');
INSERT INTO books (title, description) VALUES ('Big Data Integration Theory', 'This book presents a novel approach to database concepts, describing a categorical logic for database schema mapping based on views, within a framework for database integration/exchange and peer-to-peer. Database mappings, database programming languages, and denotational and operational semantics are discussed in depth. An analysis method is also developed that combines techniques from second order logic, data modeling, co-algebras and functorial categorial semantics.');

INSERT INTO authors (name, surname, dob, sex) VALUES ('Joshua', 'Bloch', '1960-6-27', 'm');
INSERT INTO authors (name, surname, dob, sex) VALUES ('Tomcy', 'John', '1960-6-27', 'm');
INSERT INTO authors (name, surname, dob, sex) VALUES ('Zoran', 'Majkić', '1960-6-27', 'm');

INSERT INTO books_authors VALUES (1, 1);
INSERT INTO books_authors VALUES (2, 1);
INSERT INTO books_authors VALUES (2, 2);
INSERT INTO books_authors VALUES (3, 3);
