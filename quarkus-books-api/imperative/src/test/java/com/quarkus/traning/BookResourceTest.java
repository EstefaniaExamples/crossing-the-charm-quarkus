package com.quarkus.traning;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookResourceTest {
    @Test
    @Order(0)
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() {
        given()
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, is(MediaType.APPLICATION_JSON))
                .body(is("[{" +
                        "\"id\":997," +
                        "\"title\":\"Understanding Bean Validation\"," +
                        "\"description\":\"In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints\"," +
                        "\"author\":\"Antonio Goncalves\"" +
                        "}," +
                        "{" +
                        "\"id\":998," +
                        "\"title\":\"Understanding JPA\"," +
                        "\"description\":\"In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle\"," +
                        "\"author\":\"Antonio Goncalves\"" +
                        "}]"));
    }

    @Test
    @Order(0)
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() {
        given()
                .when()
                .get("/books/998")
                .then()
                .statusCode(200)
                .body(is("{\"id\":998," +
                        "\"title\":\"Understanding JPA\"," +
                        "\"description\":\"In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle\"," +
                        "\"author\":\"Antonio Goncalves\"}"));
    }

    @Test
    @Order(0)
    @DisplayName("The service should response not found when deleting a not existing book")
    public void shouldDeleteNotExistingBookById() {
        given()
                .when()
                .delete("/books/91")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(1)
    @DisplayName("The service should response ok when deleting an existing book")
    public void shouldDeleteBookById() {
        given()
                .when()
                .delete("/books/998")
                .then()
                .statusCode(200);
    }
}
