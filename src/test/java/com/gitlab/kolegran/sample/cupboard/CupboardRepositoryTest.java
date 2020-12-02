package com.gitlab.kolegran.sample.cupboard;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
class CupboardRepositoryTest {

    private static final String DOCKER_IMAGE_NAME = "postgres";
    private static final String TEST_CUPBOARD_MIGRATION_SQL = "cupboard_migration.sql";
    private CupboardRepository repository;

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME)
        .withInitScript(TEST_CUPBOARD_MIGRATION_SQL);

    @BeforeEach
    void createCupboardRepository() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());

        repository = new CupboardRepository(dataSource);
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
}