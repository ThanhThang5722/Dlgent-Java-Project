package com.example.IS216_Dlegent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcWorkerAccount {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkerAccount(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long getPartnerIdOfWorker(Long accountId) {
        String sql = "SELECT ID_DOI_TAC FROM NHAN_VIEN WHERE ID_TAI_KHOAN = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, accountId);
    }
}
