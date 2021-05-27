package org.springboot.training.controller;

import org.springboot.training.model.Book;
import org.springboot.training.model.BookAuthorDto;
import org.springboot.training.persistence.BookAuthorsRepository;
import org.springboot.training.persistence.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final BookAuthorsRepository bookAuthorsRepository;

    public BookController(final BookRepository bookRepository, final BookAuthorsRepository bookAuthorsRepository) {
        this.bookRepository = bookRepository;
        this.bookAuthorsRepository = bookAuthorsRepository;
    }

    @GetMapping(value = "/default/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok().body(StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toUnmodifiableList()));
    }

    @GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookAuthorDto>> getAllBookAuthors() {
        return ResponseEntity.ok().body(bookAuthorsRepository.findAll());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(optionalBook.get());
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> saveNewBook(@RequestBody Book book) {
        final Book bookSaved = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookSaved);
    }
}
