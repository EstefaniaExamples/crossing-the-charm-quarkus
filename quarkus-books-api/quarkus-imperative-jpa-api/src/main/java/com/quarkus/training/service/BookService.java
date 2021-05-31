package com.quarkus.training.service;

import com.quarkus.training.model.Book;
import com.quarkus.training.persistence.BookAuthorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookService {
    private final static Logger LOG = LoggerFactory.getLogger(BookService.class);

    private final BookAuthorsRepository bookAuthorsRepository;

    public BookService(final BookAuthorsRepository bookAuthorsRepository) {
        this.bookAuthorsRepository = bookAuthorsRepository;
    }

    public List<Book> getAllBooks() {
        LOG.info("Find all the books in the database ...");
        return bookAuthorsRepository.nativeFindAll().collect(Collectors.toList());
    }

    public Book getBookById(final Long id) {
        LOG.info("Finding a specific person by ID via an Optional ...");
        final Optional<Book> optional = bookAuthorsRepository.nativeFindById(id);
        return optional.orElseThrow(NotFoundException::new);
    }
}
