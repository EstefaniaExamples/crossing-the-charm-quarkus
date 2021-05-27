package org.springboot.training;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public abstract class AbstractTestContainerBaseTest {

    private static final String DATASOURCE_URL_PROPERTY = "spring.datasource.url";
    private static final String DATASOURCE_URL_USER = "spring.datasource.username";
    private static final String DATASOURCE_URL_PASSWORD = "spring.datasource.password";

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:11"))
            .withDatabaseName("books_database")
            .withUsername("book")
            .withPassword("book")
            .withExposedPorts(5432)
            .withInitScript("import-test.sql");;

    @DynamicPropertySource
    static void setUpProperties(final DynamicPropertyRegistry registry) {
        registry.add(DATASOURCE_URL_PROPERTY, POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add(DATASOURCE_URL_USER, POSTGRE_SQL_CONTAINER::getUsername);
        registry.add(DATASOURCE_URL_PASSWORD, POSTGRE_SQL_CONTAINER::getPassword);
    }

}
