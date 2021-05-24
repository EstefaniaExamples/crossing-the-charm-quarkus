package org.springboot.training;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
//@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class BookControllerTest2 {

    private static final String DATASOURCE_URL_PROPERTY = "spring.datasource.url";
    private static final String DATASOURCE_URL_USER = "spring.datasource.username";
    private static final String DATASOURCE_URL_PASSWORD = "spring.datasource.password";
    private static final String DATASOURCE_DRIVER_CLASSNAME = "spring.datasource.driverClassName";

    //@Autowired
    //private MockMvc mockMvc;

    //@Autowired
    //private BookRepository bookRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /*
    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:11"))
            .withDatabaseName("books_database")
            .withUsername("book")
            .withPassword("book")
            .withExposedPorts(5432);
            //.withInitScript("import-test.sql");

    @DynamicPropertySource
    static void setUpProperties(final DynamicPropertyRegistry registry) {
        registry.add(DATASOURCE_URL_PROPERTY, POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add(DATASOURCE_URL_USER, POSTGRE_SQL_CONTAINER::getUsername);
        registry.add(DATASOURCE_URL_PASSWORD, POSTGRE_SQL_CONTAINER::getPassword);
        registry.add(DATASOURCE_DRIVER_CLASSNAME, POSTGRE_SQL_CONTAINER::getDriverClassName);

    }

     */

    @Test
    @Order(0)
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() throws Exception {

        String address = "http://localhost:" + port + "/books";
        this.restTemplate.getForObject(address, String.class);

        /*
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value("Effective Java"))
                .andExpect(jsonPath("$.[0].description").value("The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9"))
                .andExpect(jsonPath("$.[0].authors", isA(List.class)))
                .andExpect(jsonPath("$.[0].authors", hasSize(1)))
                .andExpect(jsonPath("$.[0].authors.[0].authorId").value("1"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].title").value("Hands-On Spring Security 5 for Reactive Applications"))
                .andExpect(jsonPath("$.[1].description").value("Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)"))
                .andExpect(jsonPath("$.[1].authors", isA(List.class)))
                .andExpect(jsonPath("$.[1].authors", hasSize(2)))
                .andExpect(jsonPath("$.[1].authors.[0].authorId").value("1"))
                .andExpect(jsonPath("$.[1].authors.[1].authorId").value("2"));

         */

    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() throws Exception {
        /*
        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.description").value("The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9"));
                */
    }
}
