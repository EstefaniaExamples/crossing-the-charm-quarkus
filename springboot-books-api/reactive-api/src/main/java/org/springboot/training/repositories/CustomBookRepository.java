package org.springboot.training.repositories;

import org.springboot.training.model.BookAuthor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomBookRepository {
    Flux<BookAuthor> findAllBooksWithAuthor();
    Mono<Long> booksCrossJoinAuthors();
}
