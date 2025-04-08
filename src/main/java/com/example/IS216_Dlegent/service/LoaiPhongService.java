package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.repository.KhuNghiDuongRepo;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;


@Service
public class LoaiPhongService {

    private final LoaiPhongRepo loaiPhongRepository;
    private final KhuNghiDuongRepo khuNghiDuongRepository;
    @Autowired
    private final KhuNghiDuongService khuNghiDuongService;

    public LoaiPhongService(LoaiPhongRepo loaiPhongRepository, KhuNghiDuongService khuNghiDuongService, KhuNghiDuongRepo khuNghiDuongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
        this.khuNghiDuongService = khuNghiDuongService;
        this.khuNghiDuongRepository = khuNghiDuongRepository;
    }

    public List<LoaiPhong> getRoomTypesByPartnerId(Long doiTacId) {
        List<KhuNghiDuong> khuNghiDuongList = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        List<Long> khuNghiDuongIds = khuNghiDuongList.stream().map(KhuNghiDuong::getId).toList();
        return loaiPhongRepository.findByKhuNghiDuong_IdIn(khuNghiDuongIds);
    }

    @Transactional
    public LoaiPhong saveLoaiPhong(Long idKhuNghiDuong, String tenLoaiPhong, Double dienTich, String loaiPhongTheoSoLuong,
                                    String loaiPhongTheoTieuChuan, Integer soGiuong, Integer soNguoi, BigDecimal gia) {

        KhuNghiDuong khuNghiDuong = khuNghiDuongRepository.findById(idKhuNghiDuong)
                .orElseThrow(() -> new IllegalArgumentException("Khu Nghỉ Dưỡng không tồn tại"));

        LoaiPhong loaiPhong = new LoaiPhong();
        loaiPhong.setKhuNghiDuong(khuNghiDuong);
        loaiPhong.setTenLoaiPhong(tenLoaiPhong);
        loaiPhong.setDienTich(dienTich);
        loaiPhong.setLoaiPhongTheoSoLuong(loaiPhongTheoSoLuong);
        loaiPhong.setLoaiPhongTheoTieuChuan(loaiPhongTheoTieuChuan);
        loaiPhong.setSoGiuong(soGiuong);
        loaiPhong.setSoNguoi(soNguoi);
        loaiPhong.setGia(gia);

        return loaiPhongRepository.save(loaiPhong);
    }

    @Transactional
    public LoaiPhong updateLoaiPhong(Long id, Long idKhuNghiDuong, String tenLoaiPhong, Double dienTich, String loaiPhongTheoSoLuong,
                                     String loaiPhongTheoTieuChuan, Integer soGiuong, Integer soNguoi, BigDecimal gia) throws Exception{
        LoaiPhong loaiPhong = loaiPhongRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loại phòng không tồn tại"));
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Test Loai Phong: {}", loaiPhong);
        
        KhuNghiDuong khuNghiDuong = khuNghiDuongRepository.findById(idKhuNghiDuong)
                .orElseThrow(() -> new IllegalArgumentException("Khu Nghỉ Dưỡng không tồn tại"));
        
        logger.info("Test Loai khuNghiDuong: {}", khuNghiDuong);
        
                loaiPhong.setKhuNghiDuong(khuNghiDuong);
        loaiPhong.setTenLoaiPhong(tenLoaiPhong);
        loaiPhong.setDienTich(dienTich);
        loaiPhong.setLoaiPhongTheoSoLuong(loaiPhongTheoSoLuong);
        loaiPhong.setLoaiPhongTheoTieuChuan(loaiPhongTheoTieuChuan);
        loaiPhong.setSoGiuong(soGiuong);
        loaiPhong.setSoNguoi(soNguoi);
        loaiPhong.setGia(gia);
        
        return loaiPhongRepository.save(loaiPhong);
    }

    @Transactional
    public void deleteAllById(List<Long> ids) {
        loaiPhongRepository.deleteAllById(ids);
    }
}