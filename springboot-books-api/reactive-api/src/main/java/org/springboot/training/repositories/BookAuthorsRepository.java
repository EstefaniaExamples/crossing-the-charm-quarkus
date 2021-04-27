package org.springboot.training.repositories;

import org.springboot.training.model.BookAuthors;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookAuthorsRepository extends ReactiveCrudRepository<BookAuthors, Long> {

    @Query("SELECT b.id, b.title, b.description, " +
            "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b")
    Flux<BookAuthors> findAll();

    @Query("SELECT b.id, b.title, b.description, " +
            "( SELECT json_agg(json_build_object('authorId', a.id, 'fullName', concat_ws(' ', a.name, a.surname))) " +
            "  FROM authors a JOIN books_authors ba ON a.id=ba.author_id " +
            "  WHERE b.id=ba.book_id ) as authors" +
            "  FROM books b " +
            "  WHERE b.id=:bookId" )
    Mono<BookAuthors> findByBookId(final Long bookId);
}
