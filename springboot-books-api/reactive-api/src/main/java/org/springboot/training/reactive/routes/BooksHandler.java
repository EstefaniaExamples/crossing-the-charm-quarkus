package org.springboot.training.reactive.routes;

import org.springboot.training.model.BookAuthors;
import org.springboot.training.repositories.BookAuthorsRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class BooksHandler {
    private static final Logger LOG = Logger.getLogger(BooksHandler.class.getName());
    private final BookAuthorsRepository bookAuthorsRepository;

    public BooksHandler(final BookAuthorsRepository bookAuthorsRepository) {
        this.bookAuthorsRepository = bookAuthorsRepository;
    }

    public Mono<ServerResponse> getAllBooksWithAuthors(final ServerRequest request) {
        LOG.info("Getting all the books ...");
        return bookAuthorsRepository.findAll()
                .collectList()
                .flatMap(booksAuthors -> ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(booksAuthors), BookAuthors.class));
    }

    public Mono<ServerResponse> getBookById(final ServerRequest request) {
        LOG.info("Getting book by id ...");
        return bookAuthorsRepository.findByBookId(Long.valueOf(request.pathVariable("id")))
                .flatMap(book -> ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(book), BookAuthors.class));
    }

    public Mono<ServerResponse> saveBook(final ServerRequest request) {
        LOG.info("Saving book ...");
//        return request.bodyToMono(Book.class)
//                .flatMap(bookRepository::save)
//                .flatMap(book -> created(URI.create("/books/" + book.id)).build());
        return ServerResponse.ok().build();
    }
}
