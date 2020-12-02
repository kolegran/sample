package com.gitlab.kolegran.sample.cupboard;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class CupboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public CupboardRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Cupboard> getAllCupboards() {
        final String selectAllSql = "SELECT * FROM Cupboard";
        return jdbcTemplate.query(
            selectAllSql,
            (rs, rowNum) -> new Cupboard(rs.getInt("id"), rs.getString("title"))
        );
    }
}
