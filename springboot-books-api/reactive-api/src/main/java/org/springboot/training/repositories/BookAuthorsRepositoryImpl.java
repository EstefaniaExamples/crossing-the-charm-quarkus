package org.springboot.training.repositories;

import org.springboot.training.model.BookAuthors;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BookAuthorsRepositoryImpl implements BookAuthorsRepository {
    private final DatabaseClient client;

    public BookAuthorsRepositoryImpl(final DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Flux<BookAuthors> findAll() {
        final String query ="SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b";
        final BookAuthorsMapper bookAuthorsMapper = new BookAuthorsMapper();
        return client.sql(query)
                .map(bookAuthorsMapper::apply)
                .all();
    }

    @Override
    public Mono<BookAuthors> findByBookId(final Long bookId) {
        final String query ="SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b " +
                "  WHERE b.id=" + bookId;
        final BookAuthorsMapper bookAuthorsMapper = new BookAuthorsMapper();
        return client.sql(query)
                .map(bookAuthorsMapper::apply)
                .one();
    }
}
