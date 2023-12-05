package com.example.hello.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.hello.info.database.DataBaseInfo;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataBaseConfig {

    // datasource bean //
    @Bean
    public DataSource dataSource(DataBaseInfo dbInfo) {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(dbInfo.getUrl());
        dataSource.setConnectionTimeout(1000L);
        dataSource.setUsername(dbInfo.getUserName());
        dataSource.setPassword(dbInfo.getPassword());
        dataSource.setMaximumPoolSize(10);
        dataSource.setIdleTimeout(30000L);
        dataSource.setMaxLifetime(300000L);
        return dataSource;
    }

    // transactionManager bean (jdbc) //
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate;
    }

    @Bean
    public SQLExceptionTranslator sqlExceptionTranslator(DataSource dataSource) {
        return new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
