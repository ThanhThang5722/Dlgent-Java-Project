package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.GiamGia;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.payload.request.InsertGioHang;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;

@Service
public class ChiTietDatPhongService {
    private Long datPhongId;

    @Autowired
    ChiTietDatPhongRepository chiTietDatPhongRepository;

    @Autowired
    DatPhongRepository datPhongRepository;

    private final Logger logger = LoggerFactory.getLogger(ChiTietDatPhongService.class);

    public List<ChiTietDatPhongDTO> getChiTietDatPhongByDatPhongId(Long khachHangId) {
        logger.info("Getting cart items for khachHang ID: {}", khachHangId);
        List<DatPhong> datPhongs = datPhongRepository.findByKhachHang_Id(khachHangId);

        datPhongId = null;
        for (DatPhong datPhong : datPhongs) {
            if (datPhong.getTrangThai().equals("Pending")) {
                datPhongId = datPhong.getId();
            }
        }

        if (datPhongId == null) {
            return Collections.emptyList();
        }

        List<ChiTietDatPhongDTO> chiTietDatPhongDTOs = chiTietDatPhongRepository.findByDatPhong_Id(datPhongId)
                .stream()
                .map(ctdp -> new ChiTietDatPhongDTO(
                        ctdp.getId(),
                        ctdp.getGoiDatPhong().getLoaiPhong().getId(),
                        ctdp.getDatPhong().getId(),
                        ctdp.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong(),
                        ctdp.getSoLuongPhong(),
                        ctdp.getSoLuongDichVuYeuCau(),
                        ctdp.getTongGiaTien().intValue(),
                        ctdp.getNgayBatDau().toString(),
                        ctdp.getNgayKetThuc().toString(),
                        ctdp.getTrangThai()))
                .collect(Collectors.toList());

        return chiTietDatPhongDTOs;
    }

    @Autowired
    KhachHangRepository khachHangRepository;
    @Autowired
    GoiDatPhongService goiDatPhongService;

    public ResponseEntity<?> addToCart(InsertGioHang insertGioHang) {

        List<DatPhong> datPhongs = datPhongRepository.findByKhachHang_Id(insertGioHang.getKhachHangId());

        DatPhong datPhong = new DatPhong();
        // tim datphong co tinhtrang pending
        for (DatPhong dp : datPhongs) {
            if (dp.getTrangThai().equals("Pending")) {
                datPhong = dp;
            }
        }
        // neu chua co datphong thi tao moi
        if (datPhong.getId() == null) {
            KhachHang khachHang = khachHangRepository.findById(insertGioHang.getKhachHangId())
                    .orElse(null);

            datPhong.setKhachHang(khachHang);
            datPhong.setTrangThai("Pending");
            datPhong.setThoiGianTao(LocalDateTime.now());
            datPhong.setTongGiaTien(BigDecimal.ZERO);

            datPhong = datPhongRepository.save(datPhong);
        }
        // LAY THONG TIN GOI DAT PHONG
        GoiDatPhong goiDatPhong = goiDatPhongService.getGoiDatPhongById(insertGioHang.getGoiDatPhongId());
        if (goiDatPhong == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy gói đặt phòng");
        }

        // Tao va luu moi chi tiet dat phong
        ChiTietDatPhong chiTietDatPhong = new ChiTietDatPhong();
        chiTietDatPhong.setDatPhong(datPhong);
        chiTietDatPhong.setGoiDatPhong(goiDatPhong);
        // chiTietDatPhong.setSoLuongPhong(insertGioHang.getSoLuongPhong());
        // chiTietDatPhong.setSoLuongDichVuYeuCau(insertGioHang.getSoLuongDichVu());
        chiTietDatPhong.setSoLuongPhong(0);
        chiTietDatPhong.setSoLuongDichVuYeuCau(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngayBatDau = LocalDate.parse(insertGioHang.getNgayBatDau(), formatter);
        LocalDate ngayKetThuc = LocalDate.parse(insertGioHang.getNgayKetThuc(), formatter);

        chiTietDatPhong.setNgayBatDau(ngayBatDau.atStartOfDay());
        chiTietDatPhong.setNgayKetThuc(ngayKetThuc.atStartOfDay());

        chiTietDatPhong.setTongGiaTien(insertGioHang.getTongGiaTien());
        chiTietDatPhong.setTrangThai("Pending");

        chiTietDatPhong = chiTietDatPhongRepository.save(chiTietDatPhong);

        ChiTietDatPhongDTO dto = new ChiTietDatPhongDTO(
                chiTietDatPhong.getId(),
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getId(),
                chiTietDatPhong.getDatPhong().getId(),
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong(),
                chiTietDatPhong.getSoLuongPhong(),
                chiTietDatPhong.getSoLuongDichVuYeuCau(),
                chiTietDatPhong.getTongGiaTien().intValue(),
                chiTietDatPhong.getNgayBatDau().toString(),
                chiTietDatPhong.getNgayKetThuc().toString(),
                chiTietDatPhong.getTrangThai());

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> deleteChiTietDatPhong(Long id) {
        chiTietDatPhongRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateCartQuantity(Long id, Integer quantity) {
        try {
            logger.info("Updating cart item {} with quantity: {}", id, quantity);

            // Tìm chi tiết đặt phòng
            ChiTietDatPhong chiTietDatPhong = chiTietDatPhongRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

            // Kiểm tra số lượng hợp lệ
            if (quantity <= 0) {
                return ResponseEntity.badRequest().body("Số lượng phòng phải lớn hơn 0");
            }

            // Lấy giá gốc từ gói đặt phòng (giá cho 1 ngày)
            BigDecimal giaGocPerNgay = chiTietDatPhong.getGoiDatPhong().getTongGiaTien();

            // Tính số ngày ở lại
            LocalDateTime ngayBatDau = chiTietDatPhong.getNgayBatDau();
            LocalDateTime ngayKetThuc = chiTietDatPhong.getNgayKetThuc();
            long soNgay = Duration.between(ngayBatDau, ngayKetThuc).toDays();

            // Đảm bảo ít nhất 1 ngày
            if (soNgay <= 0) {
                soNgay = 1;
            }

            // Cập nhật số lượng phòng
            chiTietDatPhong.setSoLuongPhong(quantity);

            // Tính lại tổng giá tiền = giá gốc per ngày * số lượng phòng * số ngày
            BigDecimal tongGiaTienMoi = giaGocPerNgay
                    .multiply(BigDecimal.valueOf(quantity))
                    .multiply(BigDecimal.valueOf(soNgay));
            chiTietDatPhong.setTongGiaTien(tongGiaTienMoi);

            logger.info("Price calculation: Base price per day: {}, Quantity: {}, Days: {}, Total: {}",
                    giaGocPerNgay, quantity, soNgay, tongGiaTienMoi);

            // Lưu thay đổi vào database
            chiTietDatPhong = chiTietDatPhongRepository.save(chiTietDatPhong);
            logger.info(
                    "Successfully saved to database - CHI_TIET_DAT_PHONG ID: {}, New quantity: {}, New total price: {}",
                    chiTietDatPhong.getId(), chiTietDatPhong.getSoLuongPhong(), chiTietDatPhong.getTongGiaTien());

            // Verify data was saved correctly by re-fetching from database
            ChiTietDatPhong verifyData = chiTietDatPhongRepository.findById(id).orElse(null);
            if (verifyData != null) {
                logger.info("Database verification - Quantity: {}, Price: {}",
                        verifyData.getSoLuongPhong(), verifyData.getTongGiaTien());
            }

            // Trả về DTO đã cập nhật
            ChiTietDatPhongDTO dto = new ChiTietDatPhongDTO(
                    chiTietDatPhong.getId(),
                    chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getId(),
                    chiTietDatPhong.getDatPhong().getId(),
                    chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong(),
                    chiTietDatPhong.getSoLuongPhong(),
                    chiTietDatPhong.getSoLuongDichVuYeuCau(),
                    chiTietDatPhong.getTongGiaTien().intValue(),
                    chiTietDatPhong.getNgayBatDau().toString(),
                    chiTietDatPhong.getNgayKetThuc().toString(),
                    chiTietDatPhong.getTrangThai());

            logger.info("Successfully updated cart item {} with new quantity: {} and price: {}",
                    id, quantity, tongGiaTienMoi);

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            logger.error("Error updating cart quantity: ", e);
            return ResponseEntity.badRequest().body("Cập nhật số lượng thất bại: " + e.getMessage());
        }
    }

    public ResponseEntity<?> verifyCartItemData(Long id) {
        try {
            ChiTietDatPhong chiTietDatPhong = chiTietDatPhongRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy mục trong giỏ hàng"));

            // Tạo response với thông tin chi tiết
            String response = String.format(
                    "Database Verification for CHI_TIET_DAT_PHONG ID: %d\n" +
                            "- Số lượng phòng: %d\n" +
                            "- Tổng giá tiền: %s VND\n" +
                            "- Ngày bắt đầu: %s\n" +
                            "- Ngày kết thúc: %s\n" +
                            "- Trạng thái: %s\n" +
                            "- Gói đặt phòng ID: %d\n" +
                            "- Loại phòng: %s",
                    chiTietDatPhong.getId(),
                    chiTietDatPhong.getSoLuongPhong(),
                    chiTietDatPhong.getTongGiaTien(),
                    chiTietDatPhong.getNgayBatDau(),
                    chiTietDatPhong.getNgayKetThuc(),
                    chiTietDatPhong.getTrangThai(),
                    chiTietDatPhong.getGoiDatPhong().getId(),
                    chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong());

            logger.info("Verification result: {}", response);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error verifying cart item data: ", e);
            return ResponseEntity.badRequest().body("Lỗi khi kiểm tra dữ liệu: " + e.getMessage());
        }
    }
}
