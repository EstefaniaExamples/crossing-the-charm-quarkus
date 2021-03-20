package org.springboot.training.reactive.routes;

import org.springboot.training.model.Book;
import org.springboot.training.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class BooksHandler {
    private static final Logger LOG = Logger.getLogger(BooksHandler.class.getName());
    private final BookRepository bookRepository;

    public BooksHandler(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<ServerResponse> getAllBooks(final ServerRequest request) {
        LOG.info("Getting all the books ...");
        return bookRepository.findAll()
                .collectList()
                .flatMap(books -> ok().body(Mono.just(books), Book.class));
    }

    public Mono<ServerResponse> getBookById(final ServerRequest request) {
        LOG.info("Getting book by id ...");
        return bookRepository.findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(book -> ok().body(Mono.just(book), Book.class));
    }

    public Mono<ServerResponse> deleteBookById(final ServerRequest request) {
        LOG.info("Deleting book by id ...");
        final Long id = Long.valueOf(request.pathVariable("id"));

        return bookRepository.findById(id)
                .flatMap(book -> bookRepository.deleteById(book.id)
                        .then(Mono.just(book.id))
                        .flatMap(idLong -> ok().build()))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> saveBook(final ServerRequest request) {
        LOG.info("Saving book ...");
        return request.bodyToMono(Book.class)
                .flatMap(bookRepository::save)
                .flatMap(book -> ok().header("Location", "/books/" + book.id)
                        .body(Mono.just(book), Book.class));
    }
}
