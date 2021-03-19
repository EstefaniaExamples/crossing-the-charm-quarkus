CREATE TABLE books (id SERIAL PRIMARY KEY, title TEXT NOT NULL,  author TEXT, description TEXT);

INSERT INTO books(id, title, author, description)
VALUES ( 997, 'Understanding Bean Validation', 'Antonio Goncalves', 'In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints');

INSERT INTO books(id, title, author, description)
VALUES ( 998, 'Understanding JPA', 'Antonio Goncalves', 'In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle');
