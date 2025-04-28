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

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

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
}
