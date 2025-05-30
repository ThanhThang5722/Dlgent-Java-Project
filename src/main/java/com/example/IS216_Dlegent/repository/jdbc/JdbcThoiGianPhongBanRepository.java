package com.example.IS216_Dlegent.repository.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcThoiGianPhongBanRepository{
    private JdbcTemplate jdbcTemplate; 

    public JdbcThoiGianPhongBanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void allocateRoomsForBooking(Long datPhongId) {
        String sql = "{call SP_ALLOCATE_ROOMS_FOR_BOOKING(?, ?)}";
        jdbcTemplate.update(sql, datPhongId, "SUCCESS");
    }

}
