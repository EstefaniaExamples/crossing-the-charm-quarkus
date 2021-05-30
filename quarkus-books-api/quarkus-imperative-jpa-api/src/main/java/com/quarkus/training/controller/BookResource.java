package com.quarkus.training.controller;

import com.quarkus.training.model.Book;
import com.quarkus.training.service.BookService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class BookResource {
    private final BookService bookService;

    public BookResource(final BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return Book.listAll();
    }

    @GET
    @Path("/native/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> nativeGetAllBooks() {
        return bookService.getAllBooks();
    }

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book bookById(@PathParam("id") Long id) {
        return Book.findById(id);
    }

    @GET
    @Path("/native/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book nativeBookById(@PathParam("id") Long id) {
        return bookService.getBookById(id);
    }
}