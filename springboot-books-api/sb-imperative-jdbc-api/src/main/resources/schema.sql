DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    dob DATE,
    sex CHAR
);

DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(500)
);

DROP TABLE IF EXISTS books_authors;

CREATE TABLE books_authors (
    book_id smallint NOT NULL,
    author_id smallint NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id),
    FOREIGN KEY (book_id) REFERENCES books(id),
    PRIMARY KEY (author_id, book_id)
);
