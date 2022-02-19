package io.vvu.study.java.reactor.demo.datastore.configuration;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
class DatastoreConfig extends AbstractR2dbcConfiguration {

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.dbname}")
    private String dbname;

    @Value("${datasource.host}")
    private String host;

    @Value("${datasource.port}")
    private Integer port = 5432;

    @Bean
    @Override
    public ConnectionFactory connectionFactory() {

        PostgresqlConnectionConfiguration configuration = PostgresqlConnectionConfiguration
                .builder()
                .host(host)
                .port(port)
                .database(dbname)
                .username(username)
                .password(password)
                .build();
        return new PostgresqlConnectionFactory(configuration);
    }
}