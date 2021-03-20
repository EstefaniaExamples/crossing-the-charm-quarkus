package org.springboot.training.reactive.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
@EnableWebFlux
public class AppRouterRegister {
    private final BooksHandler booksHandler;

    public AppRouterRegister(final BooksHandler booksHandler) {
        this.booksHandler = booksHandler;
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/books"), booksHandler::getAllBooks)
                .andRoute(GET("/books/{id}").and(contentType(MediaType.APPLICATION_JSON)), booksHandler::getBookById)
                .andRoute(DELETE("/books/{id}").and(contentType(MediaType.APPLICATION_JSON)), booksHandler::deleteBookById)
                .andRoute(POST("/book").and(accept(MediaType.APPLICATION_JSON)), booksHandler::saveBook);
    }
}
