package com.example.IS216_Dlegent.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.CallableStatement;
import java.sql.Types;
@Repository
public class JdbcPermission {
    private final JdbcTemplate jdbcTemplate;
    public JdbcPermission(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public boolean hasPermission(Long accountId, String functionName, String action) {
        String sql = "{? = CALL CheckPermission(?, ?, ?)}";
        return jdbcTemplate.execute(sql, (CallableStatement cs) -> {
            cs.setLong(2, accountId);
            cs.setString(3, functionName);
            cs.setString(4, action);

            cs.registerOutParameter(1, Types.NUMERIC);
            cs.execute();
            int result = cs.getInt(1);
            return result == 1;
        });
    }
}
