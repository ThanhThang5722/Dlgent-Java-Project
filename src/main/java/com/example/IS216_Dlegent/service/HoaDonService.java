package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.payload.SSR.BienDongSoDuDTO;
import com.example.IS216_Dlegent.repository.HoaDonRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public BigDecimal[] layDoanhThu12ThangHienTai() {
        int namHienTai = LocalDate.now().getYear();
        Map<Integer, BigDecimal> mapDoanhThu = hoaDonRepository.getDoanhThuTheoThang(namHienTai);

        BigDecimal[] doanhThu = new BigDecimal[12];
        Arrays.fill(doanhThu, BigDecimal.ZERO);

        for (int thang = 1; thang <= 12; thang++) {
            if (mapDoanhThu.containsKey(thang)) {
                doanhThu[thang - 1] = mapDoanhThu.get(thang);
            }
        }
        return doanhThu;
    }

    public List<BienDongSoDuDTO> layLichSuBienDongSoDu(Long idDoiTac) {
        return hoaDonRepository.getBienDongSoDuTheoNgay(idDoiTac);
    }

    public List<Object[]> getBalanceChanges(Long doiTacId) {
        String sql = """
            SELECT ngay, SUM(thay_doi_sodu) as thay_doi_sodu
            FROM (
                SELECT 
                    TRUNC(hd.THOIGIAN_THANHTOAN) AS ngay,
                    SUM(hd.TONG_GIATIEN) AS thay_doi_sodu
                FROM HOA_DON hd
                JOIN DOI_TAC dt ON hd.ID_DOI_TAC = dt.ID
                WHERE dt.ID = :doiTacId
                    AND EXTRACT(YEAR FROM hd.THOIGIAN_THANHTOAN) = EXTRACT(YEAR FROM SYSDATE)
                GROUP BY TRUNC(hd.THOIGIAN_THANHTOAN)
    
                UNION ALL
    
                SELECT 
                    TRUNC(rt.THOIGIAN_RUT_TIEN) AS ngay,
                    -SUM(rt.SOTIEN) AS thay_doi_sodu
                FROM LICH_SU_RUT_TIEN rt
                WHERE rt.ID_DOI_TAC = :doiTacId
                    AND EXTRACT(YEAR FROM rt.THOIGIAN_RUT_TIEN) = EXTRACT(YEAR FROM SYSDATE)
                GROUP BY TRUNC(rt.THOIGIAN_RUT_TIEN)
            )
            GROUP BY ngay
            ORDER BY ngay
            """;
    
        return entityManager.createNativeQuery(sql)
                .setParameter("doiTacId", doiTacId)
                .getResultList();
    }
    
}
