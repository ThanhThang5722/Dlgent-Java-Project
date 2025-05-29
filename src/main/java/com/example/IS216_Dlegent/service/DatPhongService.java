package com.example.IS216_Dlegent.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.HoaDon;
import com.example.IS216_Dlegent.model.ThoiGianPhongBan;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.repository.HoaDonJPA;
import com.example.IS216_Dlegent.repository.HoaDonRepository;
import com.example.IS216_Dlegent.repository.HoaDonRepositoryJPA;
import com.example.IS216_Dlegent.repository.ThoiGianPhongBanRepository;
import com.example.IS216_Dlegent.repository.jdbc.JdbcRoomType;
import com.example.IS216_Dlegent.repository.jdbc.JdbcThoiGianPhongBanRepository;

import jakarta.transaction.Transactional;

@Service
public class DatPhongService {

    @Autowired
    private DatPhongRepository datPhongRepo;

    @Autowired
    private ChiTietDatPhongRepository chiTietRepo;

    @Autowired
    private HoaDonJPA hoaDonJPA;

    @Autowired
    private JdbcThoiGianPhongBanRepository jdbcThoiGianPhongBanRepository;

    @Transactional
    public List<HoaDon> capNhatTrangThaiVaTaoHoaDon(Long datPhongId, String trangThaiMoi, String hinhThucThanhToan) {
        DatPhong datPhong = datPhongRepo.findById(datPhongId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt phòng"));

        // Cập nhật trạng thái
        datPhong.setTrangThai(trangThaiMoi);
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
 
            jdbcThoiGianPhongBanRepository.allocateRoomsForBooking(datPhongId);
        }

        return hoaDonList;
    }

    Logger logger = Logger.getLogger(DatPhongService.class.getName());

    @Transactional
    public ResponseEntity<?> huyPhong(Long id) {
        ChiTietDatPhong chiTietDatPhong = chiTietRepo.findById(id).get();
        LocalDateTime currentDate = LocalDateTime.now();

        if (!chiTietDatPhong.getTrangThai().equals("Đã thanh toán")) {
            return ResponseEntity.badRequest().body("Phòng chưa được thanh toán");
        }

        LocalDate ngayBatDau = chiTietDatPhong.getNgayBatDau().toLocalDate();
        LocalDate currentDateDate = currentDate.toLocalDate();

        if (ngayBatDau.isAfter(currentDateDate) || ngayBatDau.isEqual(currentDateDate)) {
            Duration duration = Duration.between(currentDate, chiTietDatPhong.getNgayBatDau());
            Long day = duration.toDays();

            logger.info("Day: " + day);

            if (day < 3) {
                return ResponseEntity.ok().body("Đã quá thời gian hủy phòng");
            }

            chiTietDatPhong.setTrangThai("Đã hủy");
            chiTietRepo.save(chiTietDatPhong);
            capNhatTinhTrangPhong(chiTietDatPhong);
            
            return ResponseEntity.ok().body("Hủy đặt phòng thành công");
        }
        return ResponseEntity.ok().body("Hủy đặt phòng thất bại");
    }
    
    @Autowired
    ThoiGianPhongBanRepository thoiGianPhongBanRepository;
    @Autowired
    HoaDonRepositoryJPA hoaDonRepository;
    
    public void capNhatTinhTrangPhong(ChiTietDatPhong chiTietDatPhong) {
        HoaDon hoaDon = hoaDonRepository.findByChiTietDatPhong_Id(chiTietDatPhong.getId());
        ThoiGianPhongBan thoiGianPhongBan = thoiGianPhongBanRepository.findByHoaDon_Id(hoaDon.getId());

        thoiGianPhongBanRepository.delete(thoiGianPhongBan);
    }

}