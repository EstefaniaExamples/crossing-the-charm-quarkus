package org.springboot.training.repositories;

import org.springboot.training.model.BookAuthor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CustomBookRepositoryImpl implements CustomBookRepository {
    private final DatabaseClient client;

    public CustomBookRepositoryImpl(final DatabaseClient client) {
        this.client = client;
    }

    @Override
    public Flux<BookAuthor> findAllBooksWithAuthor() {
        final String query ="SELECT b.id, b.title, b.description, " +
                "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
                "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
                "  WHERE b.id=ba.book_id ) as authors" +
                "  FROM books b;";
        final BookMapper bookMapper = new BookMapper();
        return client.sql(query)
                .map(bookMapper::apply)
                .all();
    }

    @Override
    public Mono<Long> booksCrossJoinAuthors() {
        final String query = "SELECT count(*) FROM books b CROSS JOIN authors a";
        return client.sql(query)
                .map((row, rowMetadata) -> row.get(0, Long.class))
                .first()
                .defaultIfEmpty(0L);
    }
}

