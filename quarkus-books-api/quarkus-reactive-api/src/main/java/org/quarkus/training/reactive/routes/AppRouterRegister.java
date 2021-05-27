package org.quarkus.training.reactive.routes;

import io.vertx.ext.web.Router;

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
        router.get("/books").produces(MediaType.APPLICATION_JSON)
                .handler(booksHandler::getAllBooks);

        router.get("/books/:id").produces(MediaType.APPLICATION_JSON)
                .handler(booksHandler::getBookById);
    }
}
