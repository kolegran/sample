package com.gitlab.kolegran.sample.postgre;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class PostgreSqlContainerTest {

    private static final String DOCKER_IMAGE_NAME = "postgres";
    private JdbcTemplate jdbcTemplate;

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);

    @BeforeEach
    void setUp() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    void whenSelectOne_thenOne() {
        final Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

        assertEquals(1, result);
    }
}
