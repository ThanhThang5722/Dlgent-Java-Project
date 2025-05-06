package com.example.IS216_Dlegent.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.payload.dto.DichVuDTO;
import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import com.example.IS216_Dlegent.repository.ServicesOfResortRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class JdbcResortRepository {
        private final JdbcTemplate jdbcTemplate;

        @Autowired
        private ServicesOfResortRepository servicesOfResortRepository;

        public JdbcResortRepository(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        public List<ResortSearchResponse> searchResorts(String tenResort, LocalDateTime ngayNhan, LocalDateTime ngayTra,
                        int soNguoi) {
                SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("TimResortVaPhong")
                                .declareParameters(
                                                new SqlParameter("p_tu_khoa", Types.VARCHAR),
                                                new SqlParameter("p_ngay_nhan", Types.TIMESTAMP),
                                                new SqlParameter("p_ngay_tra", Types.TIMESTAMP),
                                                new SqlParameter("p_so_nguoi", Types.INTEGER),
                                                new SqlOutParameter("p_output", Types.REF_CURSOR))
                                .returningResultSet("p_output", new ResortRowMapper());

                MapSqlParameterSource params = new MapSqlParameterSource()
                                .addValue("p_tu_khoa", tenResort)
                                .addValue("p_ngay_nhan", Timestamp.valueOf(ngayNhan))
                                .addValue("p_ngay_tra", Timestamp.valueOf(ngayTra))
                                .addValue("p_so_nguoi", soNguoi);

                Map<String, Object> result = call.execute(params);

                @SuppressWarnings("unchecked")
                List<ResortSearchResponse> resorts = (List<ResortSearchResponse>) result.get("p_output");

                for (ResortSearchResponse resort : resorts) {
                        List<ServicesOfResort> services = servicesOfResortRepository
                                        .findByKhuNghiDuong_Id(resort.getId());

                        List<DichVuDTO> dichVus = services.stream()
                                        .map(service -> new DichVuDTO(
                                                        service.getId(),
                                                        service.getDichVu().getServiceName(),
                                                        service.getGia()))
                                        .collect(Collectors.toList());
                        resort.setDichVuMacDinhs(dichVus);
                }

                return resorts;
        }

        private static class ResortRowMapper implements RowMapper<ResortSearchResponse> {
                @Override
                public ResortSearchResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new ResortSearchResponse(
                                        rs.getLong("id"),
                                        rs.getString("ten_resort"),
                                        rs.getString("dia_chi"),
                                        rs.getBigDecimal("gia_thap_nhat"),
                                        rs.getString("img_360_url"),
                                        rs.getDouble("danh_gia"),
                                        rs.getInt("so_luong_danh_gia"),
                                        rs.getInt("relevance_score"));
                }
        }
}