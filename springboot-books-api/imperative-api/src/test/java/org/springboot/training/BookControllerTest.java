package org.springboot.training;

import org.junit.jupiter.api.*;
import org.springboot.training.model.Book;
import org.springboot.training.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTest {
    private static final String DATASOURCE_URL_PROPERTY = "spring.datasource.url";
    private static final String DATASOURCE_URL_USER = "spring.datasource.username";
    private static final String DATASOURCE_URL_PASSWORD = "spring.datasource.password";

    private final MockMvc mockMvc;
    private final BookRepository bookRepository;

    public BookControllerTest(@Autowired MockMvc mockMvc, @Autowired BookRepository bookRepository) {
        this.mockMvc = mockMvc;
        this.bookRepository = bookRepository;
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
        registry.add(DATASOURCE_URL_PROPERTY, POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add(DATASOURCE_URL_USER, POSTGRE_SQL_CONTAINER::getUsername);
        registry.add(DATASOURCE_URL_PASSWORD, POSTGRE_SQL_CONTAINER::getPassword);
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(997))
                .andExpect(jsonPath("$.[0].title").value("Understanding Bean Validation"))
                .andExpect(jsonPath("$.[0].description").value("In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints"));
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 998)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(998))
                .andExpect(jsonPath("$.title").value("Understanding JPA"))
                .andExpect(jsonPath("$.description").value("In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle"));
    }

    @Test
    @Order(0)
    @DisplayName("The service should response ok when deleting a not existing book")
    public void shouldDeleteNotExistingBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", 91)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(1)
    @DisplayName("The service should response ok when deleting an existing book")
    public void shouldDeleteBookById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/{id}", 998)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        final Optional<Book> byId = bookRepository.findById(998L);
        assertThat(byId).isEmpty();
    }
}
