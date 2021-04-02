package org.springboot.training;

import org.springboot.training.model.Book;
import org.springboot.training.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalBook.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> saveNewBook(@RequestBody Book book) {
        final Book bookSaved = bookRepository.save(book);
        return new ResponseEntity<>(bookSaved, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long id) {
        final Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookRepository.deleteById(optionalBook.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
