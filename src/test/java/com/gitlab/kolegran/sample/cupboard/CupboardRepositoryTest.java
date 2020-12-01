package com.gitlab.kolegran.sample.cupboard;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class CupboardRepositoryTest {

    private static final String DOCKER_IMAGE_NAME = "postgres";
    private static final String TEST_CUPBOARD_MIGRATION_SQL = "cupboard_migration.sql";
    private CupboardRepository repository;

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME);

    @BeforeEach
    void createJdbcTemplateAndSetDataToDb() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());

        repository = new CupboardRepository(dataSource);

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(getSqlQueriesAsString());
    }

    @Test
    void whenSelectAllCupboards_thenListOfCupboards() {
        final List<Cupboard> expectedCupboards = List.of(
            new Cupboard(1, "physics laboratory"),
            new Cupboard(2, "chemistry laboratory"),
            new Cupboard(3, "drug laboratory")
        );

        final List<Cupboard> cupboards = repository.getAllCupboards();

        assertEquals(expectedCupboards, cupboards);
    }

    private String getSqlQueriesAsString() {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(TEST_CUPBOARD_MIGRATION_SQL);
        if (isNull(inputStream)) {
            throw new MigrationNotFoundException("Sql migration not found!" + TEST_CUPBOARD_MIGRATION_SQL);
        } else {
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining());
        }
    }

    private static class MigrationNotFoundException extends RuntimeException {
        public MigrationNotFoundException(String message) {
            super(message);
        }
    }
}