package com.quarkus.training;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/books")
public class BookResource {
    private final BookService bookService;

    public BookResource(final BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "allbooks", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public List<Book> books() {
        return bookService.getAllBooks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "bookbyid", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public Book bookById(@PathParam("id") Long id) {
        return bookService.getBookById(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "deleteBookbyid", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public Long deleteBookById(@PathParam("id") Long id) {
        return bookService.deleteBookById(id);
    }
}