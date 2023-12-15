package com.trianglechoke.codesparring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/db.properties")
public class MyApplicationContext implements WebMvcConfigurer {
    @Value("${spring.datasource.hikari.driver-class-name}")
    private String hikariDriverClassName;

    @Autowired Environment env;

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    HikariDataSource dataSourceHikari() {
        return new HikariDataSource(hikariConfig());
    }
}
