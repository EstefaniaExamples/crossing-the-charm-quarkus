package org.quarkus.training.reactive.routes;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.http.HttpStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;


@ApplicationScoped
public class AppRouterRegister {

    final BooksHandler booksHandler;

    @Inject
    public AppRouterRegister(final BooksHandler booksHandler) {
        this.booksHandler = booksHandler;
    }

    public void routes(@Observes Router router) {
        router.route().handler(BodyHandler.create())
                .failureHandler(this::failureHandler);

        router.get("/books").produces(MediaType.APPLICATION_JSON)
                .handler(booksHandler::getAllBooks);

        router.get("/books/:id").produces(MediaType.APPLICATION_JSON)
                .handler(booksHandler::getBookById);

        router.delete("/books/:id")
                .handler(booksHandler::deleteBookById);

        router.post("/book")
                .handler(booksHandler::saveBook);
    }

    private void failureHandler(final RoutingContext routingContext) {
        routingContext.response().setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        routingContext.response().end("Error in handler");
    }
}
