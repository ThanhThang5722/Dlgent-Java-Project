package com.example.IS216_Dlegent.repository.jdbc;

import com.example.IS216_Dlegent.payload.dto.Top6ResortDTO;
import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTop6ResortRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTop6ResortRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Top6ResortDTO> getTop6ResortsByRating() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("Top6ResortDanhGiaCaoNhat")
                .declareParameters(
                        new SqlOutParameter("p_output", Types.REF_CURSOR))
                .returningResultSet("p_output", new ResortRowMapper());

        Map<String, Object> result = call.execute();

        @SuppressWarnings("unchecked")
        List<Top6ResortDTO> resorts = (List<Top6ResortDTO>) result.get("p_output");

        return resorts;
    }

    private static class ResortRowMapper implements RowMapper<Top6ResortDTO> {
        @Override
        public Top6ResortDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Top6ResortDTO(
                    rs.getString("ten_resort"),
                    rs.getString("thanh_pho"),
                    rs.getInt("so_phong"),
                    rs.getBigDecimal("gia_thap_nhat"),
                    rs.getInt("so_luong_danh_gia"),
                    rs.getString("hinh_anh"),
                    rs.getDouble("danh_gia")
            );
        }
    }
}

