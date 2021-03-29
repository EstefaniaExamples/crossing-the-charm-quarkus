package org.springboot.training.datasource;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.training.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.HashMap;
import java.util.Map;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.*;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@Configuration(proxyBeanMethods = false)
@EnableR2dbcRepositories
public class BooksDataSource extends AbstractR2dbcConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(BooksDataSource.class);

    private final DataSourceProperties dataSourceProperties;

    public BooksDataSource(final DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        final Map<String, String> options = new HashMap<>();
        options.put("default_transaction_read_only", dataSourceProperties.getReadOnly().toString());

        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(PROTOCOL, dataSourceProperties.getProtocol())
                .option(DRIVER, POOLING_DRIVER)
                .option(HOST, dataSourceProperties.getHost())
                .option(PORT, dataSourceProperties.getPort())
                .option(USER, "book")
                .option(PASSWORD, dataSourceProperties.getCredentials())
                .option(SSL, true)
                .option(Option.valueOf("sslMode"), "allow")
                .option(DATABASE, dataSourceProperties.getDatabase())
                .option(INITIAL_SIZE, dataSourceProperties.getPool().getMinConnections())
                .option(MAX_SIZE, dataSourceProperties.getPool().getMaxConnections())
                .option(VALIDATION_QUERY, "SELECT 1")
                .option(Option.valueOf("options"), options)
                .build());
    }
}
