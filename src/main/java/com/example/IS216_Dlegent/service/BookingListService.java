package com.example.IS216_Dlegent.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.payload.dto.BookedRoomDTO;
import com.example.IS216_Dlegent.payload.dto.BookingListDTO;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.repository.HinhPhongRepo;

@Service
public class BookingListService {
    @Autowired
    DatPhongRepository datPhongRepository;
    @Autowired
    ChiTietDatPhongRepository chiTietDatPhongRepository;
    @Autowired
    HinhPhongRepo hinhPhongRepository;

    public BookingListDTO getBookingHistory(Long khachHangId) {
        BookingListDTO bookingListDTO = new BookingListDTO();
        List<DatPhong> datPhongs = datPhongRepository.findByKhachHang_Id(khachHangId);

        if (datPhongs == null) {
            return null;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (DatPhong datPhong : datPhongs) {
            List<ChiTietDatPhong> chiTietDatPhongs = chiTietDatPhongRepository.findByDatPhong_Id(datPhong.getId());

            if (!datPhong.getTinhTrang().equals("Đã thanh toán")) {
                continue;
            }

            for (ChiTietDatPhong chiTietDatPhong : chiTietDatPhongs){
                System.out.println(chiTietDatPhong.toString());
                BookedRoomDTO bookedRoomDTO = new BookedRoomDTO();
                bookedRoomDTO.setBookingId(chiTietDatPhong.getId());
                bookedRoomDTO.setTenResort(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getKhuNghiDuong().getTen());
                bookedRoomDTO.setTenLoaiPhong(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong());
                
                String ngayBatDau = chiTietDatPhong.getNgayBatDau().format(formatter);
                String ngayKetThuc = chiTietDatPhong.getNgayKetThuc().format(formatter);

                bookedRoomDTO.setNgayBatDau(ngayBatDau);
                bookedRoomDTO.setNgayKetThuc(ngayKetThuc);

                List<HinhPhong> hinhPhong = hinhPhongRepository.findByRoomTypeIDAndIsDeleted(chiTietDatPhong.getGoiDatPhong().getLoaiPhong().getId(), 0L);

                bookedRoomDTO.setHinhPhongUrl(hinhPhong.get(0).getUrl());
                
                if (chiTietDatPhong.getTinhTrang().equals("Đã hủy")) {
                    bookingListDTO.getCancelledRoom().add(bookedRoomDTO);
                } else if (chiTietDatPhong.getNgayKetThuc().isBefore(currentDate)) {
                    bookingListDTO.getCompletedRooom().add(bookedRoomDTO);
                } else if (chiTietDatPhong.getNgayBatDau().isAfter(currentDate)) {
                    bookingListDTO.getUpcomingRoom().add(bookedRoomDTO);
                }

            }
        }

        return bookingListDTO;
    }
}
