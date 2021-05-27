package org.quarkus.training.reactive.routes;

import io.smallrye.mutiny.Multi;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mutiny.pgclient.PgPool;
import org.apache.http.HttpHeaders;
import org.quarkus.training.model.BookAuthors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class BooksHandler {
    private static final Logger LOG = LoggerFactory.getLogger(BooksHandler.class);

    @Inject
    private PgPool pgPool;

    public void getAllBooks(RoutingContext routingContext) {
        LOG.info("Get all books ...");
        final HttpServerResponse response = routingContext.response();
        final String acceptableContentType = routingContext.getAcceptableContentType();
        final Multi<BookAuthors> bookMulti = BookAuthors.findAll(pgPool);

        bookMulti.collect().asList().subscribe().with(
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

        BookAuthors.findById(pgPool, id).subscribe().with(
                bookAuthors ->
                        response
                                .putHeader(HttpHeaders.CONTENT_TYPE, acceptableContentType)
                                .end(Json.encodePrettily(bookAuthors))
                ,
                throwable -> LOG.error("Error getting book by id", throwable)
        );
    }
}
