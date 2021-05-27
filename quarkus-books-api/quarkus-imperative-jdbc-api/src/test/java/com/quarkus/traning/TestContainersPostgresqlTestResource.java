package com.quarkus.traning;


import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

@QuarkusTestResource(TestContainersPostgresqlTestResource.Initializer.class)
public class TestContainersPostgresqlTestResource {
    public static class Initializer implements QuarkusTestResourceLifecycleManager {
        private PostgreSQLContainer<?> postgreSQLContainer;

        @Override
        public Map<String, String> start() {
            postgreSQLContainer = new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("books_database")
                    .withUsername("book")
                    .withPassword("book")
                    .withExposedPorts(5432)
                    .withInitScript("import-test.sql");
            postgreSQLContainer.start();
            return configurationParameters();
        }

        private Map<String, String> configurationParameters() {
            final Map<String, String> config = new HashMap<>();
            config.put("quarkus.datasource.jdbc.url", postgreSQLContainer.getJdbcUrl());
            System.out.println("***** INIT " + postgreSQLContainer.getJdbcUrl());
            config.put("quarkus.datasource.username", this.postgreSQLContainer.getUsername());
            config.put("quarkus.datasource.password", this.postgreSQLContainer.getPassword());
            return config;
        }

        @Override
        public void stop() {
            if (this.postgreSQLContainer != null) {
                System.out.println("***** STOP " + this.postgreSQLContainer.getJdbcUrl());
                this.postgreSQLContainer.close();
            }
        }
    }
}
