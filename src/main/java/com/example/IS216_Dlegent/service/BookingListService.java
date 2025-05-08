package com.example.IS216_Dlegent.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.DichVuYeuCau;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.payload.dto.BookedRoomDTO;
import com.example.IS216_Dlegent.payload.dto.BookingListDTO;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO2;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.repository.DichVuYeuCauRepository;
import com.example.IS216_Dlegent.repository.HinhPhongRepo;

@Service
public class BookingListService {
    @Autowired
    DatPhongRepository datPhongRepository;
    @Autowired
    ChiTietDatPhongRepository chiTietDatPhongRepository;
    @Autowired
    HinhPhongRepo hinhPhongRepository;
    @Autowired
    private DichVuYeuCauRepository dichVuYeuCauRepository;

    LocalDateTime currentDate = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BookingListDTO getBookingHistory(Long khachHangId) {
        BookingListDTO bookingListDTO = new BookingListDTO();
        List<DatPhong> datPhongs = datPhongRepository.findByKhachHang_Id(khachHangId);

        if (datPhongs == null) {
            return null;
        }

        if (bookingListDTO.getCancelledRoom() == null) {
            bookingListDTO.setCancelledRoom(new ArrayList<>());
        }
        if (bookingListDTO.getCompletedRoom() == null) {
            bookingListDTO.setCompletedRoom(new ArrayList<>());
        }
        if (bookingListDTO.getUpcomingRoom() == null) {
            bookingListDTO.setUpcomingRoom(new ArrayList<>());
        }

        for (DatPhong datPhong : datPhongs) {
            List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findByDatPhong_Id(datPhong.getId());

            if (!datPhong.getTinhTrang().equals("Đã thanh toán")) {
                continue;
            }

            for (ChiTietDatPhong chiTietDatPhong : chiTietDatPhongs) {
                System.out.println(chiTietDatPhong.toString());
                BookedRoomDTO bookedRoomDTO = new BookedRoomDTO();
                bookedRoomDTO.setBookingId(chiTietDatPhong.getId());
                bookedRoomDTO.setTenResort(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getKhuNghiDuong().getTen());
                bookedRoomDTO.setTenLoaiPhong(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong());

                String ngayBatDau = chiTietDatPhong.getNgayBatDau().format(formatter);
                String ngayKetThuc = chiTietDatPhong.getNgayKetThuc().format(formatter);

                bookedRoomDTO.setNgayBatDau(ngayBatDau);
                bookedRoomDTO.setNgayKetThuc(ngayKetThuc);

                List<HinhPhong> hinhPhong = hinhPhongRepository
                        .findByRoomTypeIDAndIsDeleted(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getId(), 0L);

                bookedRoomDTO.setHinhPhongUrl(hinhPhong.get(0).getUrl());

                if (chiTietDatPhong.getTinhTrang().equals("Đã hủy")) {
                    bookingListDTO.getCancelledRoom().add(bookedRoomDTO);
                } else if (chiTietDatPhong.getNgayKetThuc().isBefore(currentDate)) {
                    bookingListDTO.getCompletedRoom().add(bookedRoomDTO);
                } else if (chiTietDatPhong.getNgayBatDau().isAfter(currentDate)) {
                    bookingListDTO.getUpcomingRoom().add(bookedRoomDTO);
                }

            }
        }

        return bookingListDTO;
    }

    public ResponseEntity<?> getBookingDetail(Long bookingId) {
        Optional<ChiTietDatPhong> chiTietDatPhongOtp = chiTietDatPhongRepository.findById(bookingId);

        if (!chiTietDatPhongOtp.isPresent()) {
            return ResponseEntity.badRequest().body("Không tìm thấy thông tin đặt phòng");
        }

        ChiTietDatPhong chiTietDatPhong = chiTietDatPhongOtp.get();

        List<HinhPhong> hinhPhongs = hinhPhongRepository
                .findByRoomTypeIDAndIsDeleted(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getId(), 0L);
        List<DichVuYeuCau> dichVuYeuCaus = dichVuYeuCauRepository.findByChiTietDatPhong_Id(chiTietDatPhong.getId());
        List<DichVuMacDinh> dichVuMacDinhs = chiTietDatPhong.getGoiDatPhong().getDichVuMacDinhs();

        String ngayBatDau = chiTietDatPhong.getNgayBatDau().format(formatter);
        String ngayKetThuc = chiTietDatPhong.getNgayKetThuc().format(formatter);

        ChiTietDatPhongDTO2 chiTietDatPhong2 = new ChiTietDatPhongDTO2(
                chiTietDatPhong.getId(),
                chiTietDatPhong.getSoLuongPhong(),
                chiTietDatPhong.getTongGiaTien(),
                ngayBatDau,
                ngayKetThuc,
                dichVuYeuCaus,
                dichVuMacDinhs,
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getKhuNghiDuong().getTen(),
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getDienTich(),
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getSoGiuong(),
                chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getSoNguoi(),
                hinhPhongs.get(0).getUrl());

        return ResponseEntity.ok().body(chiTietDatPhong2);
    }
}
