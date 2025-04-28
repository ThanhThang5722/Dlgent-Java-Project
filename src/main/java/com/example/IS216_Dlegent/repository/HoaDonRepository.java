package com.example.IS216_Dlegent.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.payload.SSR.BienDongSoDuDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HoaDonRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    public int demHoaDonTrongNgay(Long doiTacId) {
        String sql = "{? = call demHoaDonTrongNgay(?)}";

        return jdbcTemplate.execute(
                (Connection con) -> {
                    CallableStatement cs = con.prepareCall(sql);
                    cs.registerOutParameter(1, Types.INTEGER); // output
                    cs.setLong(2, doiTacId);                    // input
                    return cs;  
                },
                (CallableStatement cs) -> {
                    cs.execute();
                    return cs.getInt(1); // Lấy giá trị trả về từ function
                }
            );
    }
    public BigDecimal tongHoaDonThangHienTai(Long doiTacId) {
        String sql = "{? = call tongHoaDonThangHienTai(?)}";
    
        return jdbcTemplate.execute(
            (Connection con) -> {
                CallableStatement cs = con.prepareCall(sql);
                cs.registerOutParameter(1, Types.DECIMAL);
                cs.setLong(2, doiTacId);
                return cs;
            },
            (CallableStatement cs) -> {
                cs.execute();
                return cs.getBigDecimal(1);
            }
        );
    }
    public int tongSoLuotDat(Long doiTacId) {
        String sql = "SELECT COUNT(*) FROM HOA_DON WHERE ID_DOI_TAC = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, doiTacId);
            return count != null ? count : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
    public Map<Integer, BigDecimal> getDoanhThuTheoThang(int nam) {
        return jdbcTemplate.execute((Connection con) -> {
            try (CallableStatement cs = con.prepareCall("{? = call GET_DOANH_THU_THEO_THANG(?)}")) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.setInt(2, nam);

                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    Map<Integer, BigDecimal> doanhThuTheoThang = new HashMap<>();
                    while (rs.next()) {
                        int thang = rs.getInt("thang");
                        BigDecimal tong = rs.getBigDecimal("tong_doanh_thu");
                        doanhThuTheoThang.put(thang, tong);
                    }
                    return doanhThuTheoThang;
                }
            }
        });
    }

    public List<BienDongSoDuDTO> getBienDongSoDuTheoNgay(Long idDoiTac) {
        return jdbcTemplate.execute((Connection con) -> {
            try (CallableStatement cs = con.prepareCall("{? = call GET_LICH_SU_BIEN_DONG_SODU(?)}")) {
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.setLong(2, idDoiTac);

                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                    List<BienDongSoDuDTO> list = new ArrayList<>();
                    while (rs.next()) {
                        String ngay = rs.getString("ngay");
                        BigDecimal thayDoi = rs.getBigDecimal("tong_thaydoi");
                        list.add(new BienDongSoDuDTO(ngay, thayDoi));
                    }
                    return list;
                }
            }
        });
    }

    public List<Object[]> getPopularResorts() {
        String sql = """
            SELECT KND.TEN, COUNT(DISTINCT HD.ID_CHI_TIET_DAT_PHONG)
            FROM HOA_DON hd
            JOIN CHI_TIET_DAT_PHONG ctdp ON HD.ID_CHI_TIET_DAT_PHONG = CTDP.ID
            JOIN GOI_DAT_PHONG gdp ON CTDP.ID_GOI_DAT_PHONG = GDP.ID
            JOIN LOAI_PHONG lp ON GDP.ID_LOAI_PHONG = LP.ID
            JOIN KHU_NGHI_DUONG knd ON LP.ID_KHU_NGHI_DUONG = KND.ID
            GROUP BY KND.TEN
            """;

        return entityManager.createNativeQuery(sql).getResultList();
    }
}