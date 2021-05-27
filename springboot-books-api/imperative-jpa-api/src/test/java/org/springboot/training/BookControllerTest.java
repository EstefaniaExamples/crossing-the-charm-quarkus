package org.springboot.training;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest extends AbstractTestContainerBaseTest {
    @Test
    public void given_application_when_call_endpoint_then_Ok() {
        given()
                .when().get("/books")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void given_application_when_call_endpoint_with_id_then_Ok() throws Exception {
        given()
                .when().get("/books/1")
                .then().assertThat().statusCode(200);
    }
}