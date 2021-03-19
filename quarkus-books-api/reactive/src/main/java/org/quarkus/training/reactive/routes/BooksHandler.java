package org.quarkus.training.reactive.routes;

import io.smallrye.mutiny.Multi;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mutiny.pgclient.PgPool;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.quarkus.training.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.net.URI;

@ApplicationScoped
public class BooksHandler {
    private static final Logger LOG = LoggerFactory.getLogger(BooksHandler.class);

    @Inject
    private PgPool pgPool;

    public void getAllBooks(RoutingContext routingContext) {
        LOG.info("Get all books ...");
        final HttpServerResponse response = routingContext.response();
        final String acceptableContentType = routingContext.getAcceptableContentType();
        final Multi<Book> bookMulti = Book.findAll(pgPool);

        bookMulti.collectItems().asList().subscribe().with(
                books ->
                        response
                                .putHeader(HttpHeaders.CONTENT_TYPE, acceptableContentType)
                                .end(Json.encodePrettily(books)),
                throwable -> LOG.error("Error getting all books", throwable)
        );
    }

    public void getBookById(final RoutingContext routingContext) {
        LOG.info("Get book by id ...");
        final HttpServerResponse response = routingContext.response();
        final String acceptableContentType = routingContext.getAcceptableContentType();
        final Long id = Long.valueOf(routingContext.request().getParam("id"));

        Book.findById(pgPool, id).subscribe().with(
                book ->
                        response
                                .putHeader(HttpHeaders.CONTENT_TYPE, acceptableContentType)
                                .end(Json.encodePrettily(book))
                ,
                throwable -> LOG.error("Error getting book by id", throwable)
        );
    }

    public void deleteBookById(final RoutingContext routingContext) {
        LOG.info("Delete book by id ...");
        final HttpServerResponse response = routingContext.response();
        final Long id = Long.valueOf(routingContext.request().getParam("id"));

        Book.delete(pgPool, id).subscribe().with(
                isDeleted -> {
                    if (isDeleted) {
                        response
                                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).end();
                    } else {
                        response
                                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .setStatusCode(HttpStatus.SC_NOT_FOUND).end();
                    }
                }
                ,
                throwable -> LOG.error("Error deleting book by id", throwable)
        );
    }

    public void saveBook(final RoutingContext routingContext) {
        LOG.info("Save new book ...");
        final JsonObject bodyAsJson = routingContext.getBodyAsJson();
        LOG.info("New book details: {}", bodyAsJson);
        final Book book = Json.mapper.convertValue(bodyAsJson, Book.class);

        book.save(pgPool).subscribe().with(
                id -> routingContext.response()
                        .setStatusCode(HttpStatus.SC_CREATED)
                        .putHeader(HttpHeaders.LOCATION, URI.create("/books/" + id).toString())
                        .end());
    }
}
