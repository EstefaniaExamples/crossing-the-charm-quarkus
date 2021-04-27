package org.springboot.training.repositories;

import org.springboot.training.model.BookAuthors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookAuthorsRepository {
    Flux<BookAuthors> findAll();
    Mono<BookAuthors> findByBookId(final Long bookId);
}
