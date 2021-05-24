package org.springboot.training;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

//TODO Migrate to RestAssured
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest extends AbstractTestContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void given_application_when_call_endpoint_then_Ok() throws Exception {

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

    }

    @Test
    public void given_application_when_call_endpoint_with_id_then_Ok() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", 1)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.description").value("The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9"));

    }
}