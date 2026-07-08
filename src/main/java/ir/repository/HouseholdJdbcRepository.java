package ir.repository;

import ir.model.Household;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HouseholdJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public HouseholdJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsertIgnoreDuplicates(List<Household> households) {

        String sql = """
            INSERT INTO household (national_id, household_size)
            VALUES (?, ?)
            ON CONFLICT (national_id) DO NOTHING
        """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                Household h = households.get(i);

                ps.setString(1, h.getNationalId());
                ps.setInt(2, h.getHouseholdSize());
            }

            @Override
            public int getBatchSize() {
                return households.size();
            }
        });
    }
}
