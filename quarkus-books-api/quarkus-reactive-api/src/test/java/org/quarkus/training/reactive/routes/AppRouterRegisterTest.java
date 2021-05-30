package org.quarkus.training.reactive.routes;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppRouterRegisterTest  {
    @Test
    @DisplayName("The service should response with the all the books in the database")
    public void shouldGetAllTheBooks() {
        given()
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, is(MediaType.APPLICATION_JSON))
                .body("size()", is(3))
                .body("id[0]", equalTo(1))
                .body("title[0]", equalTo("Effective Java"))
                .body("description[0]", equalTo("The Definitive Guide to Java Platform Best Practices–Updated for Java 7, 8, and 9"))
                .body("authors[0].id[0]", equalTo(1))
                .body("authors[0].name[0]", equalTo("Joshua"))
                .body("authors[0].surname[0]", equalTo("Bloch"))

                .body("id[1]", equalTo(2))
                .body("title[1]", equalTo("Hands-On Spring Security 5 for Reactive Applications"))
                .body("description[1]", equalTo("Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)"))
                .body("authors[1].id[0]", equalTo(1))
                .body("authors[1].name[0]", equalTo("Joshua"))
                .body("authors[1].surname[0]", equalTo("Bloch"))
                .body("authors[1].id[1]", equalTo(2))
                .body("authors[1].name[1]", equalTo("Tomcy"))
                .body("authors[1].surname[1]", equalTo("John"))

                .body("id[2]", equalTo(3))
                .body("title[2]", equalTo("Big Data Integration Theory"))
                .body("description[2]", equalTo("This book presents a novel approach to database concepts, describing a categorical logic for database schema mapping based on views, within a framework for database integration/exchange and peer-to-peer. Database mappings, database programming languages, and denotational and operational semantics are discussed in depth. An analysis method is also developed that combines techniques from second order logic, data modeling, co-algebras and functorial categorial semantics."))
                .body("authors[2].id[0]", equalTo(3))
                .body("authors[2].name[0]", equalTo("Zoran"))
                .body("authors[2].surname[0]", equalTo("Majkić"));
    }

    @Test
    @DisplayName("The service should response with the book with the specific id")
    public void shouldGetBookById() {
        given()
                .when()
                .get("/books/2")
                .then()
                .statusCode(200)
                .body("id", equalTo(2))
                .body("title", equalTo("Hands-On Spring Security 5 for Reactive Applications"))
                .body("description", equalTo("Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)"))
                .body("authors.id[0]", equalTo(1))
                .body("authors.name[0]", equalTo("Joshua"))
                .body("authors.surname[0]", equalTo("Bloch"))
                .body("authors.id[1]", equalTo(2))
                .body("authors.name[1]", equalTo("Tomcy"))
                .body("authors.surname[1]", equalTo("John"));
    }
}
