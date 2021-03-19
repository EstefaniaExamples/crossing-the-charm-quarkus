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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(0)
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() throws Exception {
        webTestClient.get().uri("/books")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.[0].id").isEqualTo(997)
                .jsonPath("$.[0].title").isEqualTo("Understanding Bean Validation")
                .jsonPath("$.[0].description").isEqualTo("In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints")
                .jsonPath("$.[1].id").isEqualTo(998)
                .jsonPath("$.[1].title").isEqualTo("Understanding JPA")
                .jsonPath("$.[1].description").isEqualTo("In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle");
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() throws Exception {
        webTestClient.get().uri("/books/{id}", 998)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(998)
                .jsonPath("$.title").isEqualTo("Understanding JPA")
                .jsonPath("$.description").isEqualTo("In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle");
    }

    @Test
    @Order(0)
    @DisplayName("The service should response ok when deleting a not existing book")
    public void shouldDeleteNotExistingBookById() throws Exception {
        webTestClient.delete().uri("/books/{id}", 91)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().isEmpty();
    }

    @Test
    @Order(1)
    @DisplayName("The service should response ok when deleting an existing book")
    public void shouldDeleteBookById() throws Exception {
        webTestClient.delete().uri("/books/{id}", 998)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }
}
