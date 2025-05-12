package com.example.IS216_Dlegent.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.HoaDon;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.repository.HoaDonJPA;

import jakarta.transaction.Transactional;

@Service
public class DatPhongService {

    @Autowired
    private DatPhongRepository datPhongRepo;

    @Autowired
    private ChiTietDatPhongRepository chiTietRepo;

    @Autowired
    private HoaDonJPA hoaDonJPA;

    @Transactional
    public List<HoaDon> capNhatTrangThaiVaTaoHoaDon(Long datPhongId, String trangThaiMoi, String hinhThucThanhToan) {
        DatPhong datPhong = datPhongRepo.findById(datPhongId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt phòng"));

        // Cập nhật trạng thái
        datPhong.setTinhTrang(trangThaiMoi);
        datPhong.setTenTKNH("zalopay");
        datPhongRepo.save(datPhong);

        List<HoaDon> hoaDonList = new ArrayList<>();

        // Tạo hóa đơn nếu trạng thái là "ĐÃ NHẬN PHÒNG"
        if ("Đã thanh toán".equalsIgnoreCase(trangThaiMoi)) {
            List<ChiTietDatPhong> chiTietList = chiTietRepo.findByDatPhong_Id(datPhongId);
            System.out.println("Trang thai dat phong: " + chiTietList.toString());
            for (ChiTietDatPhong ct : chiTietList) {
                System.out.println("Trang thai dat phong: " + ct.toString());
                HoaDon hoaDon = new HoaDon();
                hoaDon.setChiTietDatPhong(ct);
                hoaDon.setKhachHang(datPhong.getKhachHang());
                hoaDon.setDoiTac(ct.getGoiDatPhong().getLoaiPhong().getKhuNghiDuong().getDoiTac());
                hoaDon.setTongGiaTien(ct.getTongGiaTien());
                hoaDon.setThoiGianThanhToan(LocalDateTime.now());
                hoaDon.setHinhThucThanhToan(hinhThucThanhToan);
                System.out.println(hoaDon);

                hoaDonList.add(hoaDonJPA.save(hoaDon));
            }
        }

        return hoaDonList;
    }

    @Transactional
    public ResponseEntity<?> huyPhong(Long id) {
        ChiTietDatPhong chiTietDatPhong = chiTietRepo.findById(id).get();
        LocalDateTime currentDate = LocalDateTime.now();

        if (!chiTietDatPhong.getTinhTrang().equals("Đã thanh toán")) {
            return ResponseEntity.badRequest().body("Phòng chưa được thanh toán");
        }

        if (chiTietDatPhong.getNgayBatDau().isAfter(currentDate)) {
            Duration duration = Duration.between(currentDate, chiTietDatPhong.getNgayBatDau());
            Long day = duration.toDays();

            if (day < 3) {
                return ResponseEntity.ok().body("Đã quá thời gian hủy phòng");
            }

            chiTietDatPhong.setTinhTrang("Đã hủy");
            capNhatTinhTrangPhong();

            chiTietRepo.save(chiTietDatPhong);
        }

        return ResponseEntity.ok().body("Hủy đặt phòng thành công");
    }

    public void capNhatTinhTrangPhong() {
    }

}