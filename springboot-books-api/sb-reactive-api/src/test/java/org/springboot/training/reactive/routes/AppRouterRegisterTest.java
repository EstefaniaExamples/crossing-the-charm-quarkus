package org.springboot.training.reactive.routes;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AppRouterRegisterTest {
    private static final String DATASOURCE_PORT = "books-datasource.port";
    private static final String DATASOURCE_URL_PASSWORD = "books-datasource.credentials";

    private final WebTestClient webTestClient;

    public AppRouterRegisterTest(@Autowired WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:11"))
            .withDatabaseName("books_database")
            .withUsername("book")
            .withPassword("book")
            .withExposedPorts(5432)
            .withInitScript("import-test.sql");

    @DynamicPropertySource
    static void setUpProperties(final DynamicPropertyRegistry registry) {
        registry.add(DATASOURCE_PORT, POSTGRE_SQL_CONTAINER::getFirstMappedPort);
        registry.add(DATASOURCE_URL_PASSWORD, POSTGRE_SQL_CONTAINER::getPassword);
    }

    @Test
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() throws Exception {
        webTestClient.get().uri("/books")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.[0].id").isEqualTo(1)
                .jsonPath("$.[0].title").isEqualTo("Effective Java")
                .jsonPath("$.[0].description").isEqualTo("The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9")
                .jsonPath("$.[0].authors").isArray()
                .jsonPath("$.[0].authors.size()").isEqualTo(1)
                .jsonPath("$.[1].id").isEqualTo(2)
                .jsonPath("$.[1].title").isEqualTo("Hands-On Spring Security 5 for Reactive Applications")
                .jsonPath("$.[1].description").isEqualTo("Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)")
                .jsonPath("$.[1].authors").isArray()
                .jsonPath("$.[1].authors.size()").isEqualTo(2);
    }

    @Test
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() throws Exception {
        webTestClient.get().uri("/books/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.title").isEqualTo("Effective Java")
                .jsonPath("$.description").isEqualTo("The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9")
                .jsonPath("$.authors").isArray()
                .jsonPath("$.authors.size()").isEqualTo(1);
    }
}
