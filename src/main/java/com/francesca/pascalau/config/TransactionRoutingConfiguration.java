package com.francesca.pascalau.config;

import com.francesca.pascalau.config.util.DataSourceType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.Database;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class TransactionRoutingConfiguration extends AbstractJPAConfiguration {

    private final Map<Object, Object> dataSourceMap = new HashMap<>();

    @Value("${datasources.url.primary}")
    private String primaryUrl;

    @Value("${datasources.url.replica}")
    private String replicaUrl;

    @Value("${datasources.username}")
    private String username;

    @Value("${datasources.password}")
    private String password;

    @Bean
    public DataSource readWriteDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(primaryUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        return connectionPoolDataSource(dataSource);
    }

    @Bean
    public DataSource readOnlyDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(replicaUrl);
        dataSource.setUser(username);
        dataSource.setPassword(password);
//        dataSource.setReadOnly(true); not needed since we have the DataSourceType already setup

        return connectionPoolDataSource(dataSource);
    }

    @Bean
    public TransactionRoutingDataSource actualDataSource() {
        TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();

        dataSourceMap.put(DataSourceType.READ_WRITE, readWriteDataSource());
        dataSourceMap.put(DataSourceType.READ_ONLY, readOnlyDataSource());

        routingDataSource.setTargetDataSources(dataSourceMap);
        return routingDataSource;
    }

    @Override
    protected Properties additionalProperties() {
        Properties properties = super.additionalProperties();
        properties.setProperty("hibernate.connection.provider_disables_autocommit", Boolean.TRUE.toString());
        
        return properties;
    }

    @Override
    protected String databaseType() {
        return Database.POSTGRESQL.name().toLowerCase();
    }

    private HikariConfig hikariConfig(DataSource dataSource) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(dataSource);
        hikariConfig.setAutoCommit(false);

        return hikariConfig;
    }

    private HikariDataSource connectionPoolDataSource(DataSource dataSource) {
        return new HikariDataSource(hikariConfig(dataSource));
    }
}