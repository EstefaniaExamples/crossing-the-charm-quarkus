package com.quarkus.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BookService {
    private final static Logger LOG = LoggerFactory.getLogger(BookService.class);

    public List<Book> getAllBooks() {
        LOG.info("Find all the books in the database ...");
        return Book.listAll();
    }

    public Book getBookById(final Long id) {
        LOG.info("Finding a specific person by ID via an Optional ...");
        final Optional<Book> optional = Book.findByIdOptional(id);
        return optional.orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Long deleteBookById(final Long id) {
        LOG.info("Finding a specific person by ID via an Optional ...");
        boolean deleteById = Book.deleteById(id);
        if (deleteById) {
            return id;
        } else {
            throw new NotFoundException();
        }
    }
}
