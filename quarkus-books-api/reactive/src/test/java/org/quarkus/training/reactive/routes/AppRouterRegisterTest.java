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
                .body("authors[0].authorId[0]", equalTo(1))
                .body("authors[0].fullName[0]", equalTo("Joshua Bloch"))

                .body("id[1]", equalTo(2))
                .body("title[1]", equalTo("Hands-On Spring Security 5 for Reactive Applications"))
                .body("description[1]", equalTo("Learn effective ways to secure your applications with Spring and Spring WebFlux (English Edition)"))
                .body("authors[1].authorId[0]", equalTo(1))
                .body("authors[1].fullName[0]", equalTo("Joshua Bloch"))
                .body("authors[1].authorId[1]", equalTo(2))
                .body("authors[1].fullName[1]", equalTo("Tomcy John"))

                .body("id[2]", equalTo(3))
                .body("title[2]", equalTo("Big Data Integration Theory"))
                .body("description[2]", equalTo("This book presents a novel approach to database concepts, describing a categorical logic for database schema mapping based on views, within a framework for database integration/exchange and peer-to-peer. Database mappings, database programming languages, and denotational and operational semantics are discussed in depth. An analysis method is also developed that combines techniques from second order logic, data modeling, co-algebras and functorial categorial semantics."))
                .body("authors[2].authorId[0]", equalTo(3))
                .body("authors[2].fullName[0]", equalTo("Zoran Majkić"));
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
                .body("authors.authorId[0]", equalTo(1))
                .body("authors.fullName[0]", equalTo("Joshua Bloch"))
                .body("authors.authorId[1]", equalTo(2))
                .body("authors.fullName[1]", equalTo("Tomcy John"));
    }
}
