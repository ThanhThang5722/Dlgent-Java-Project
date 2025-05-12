package com.example.IS216_Dlegent.payload.dto;

import java.math.BigInteger;
import java.util.List;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.DichVuYeuCau;

public class BookedRoomDTO {
    // CHI_TIET_DAT_PHONG
    private Long bookingId;
    private String tenResort;
    private String tenLoaiPhong;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String hinhPhongUrl;

    public BookedRoomDTO() {
    }

    public BookedRoomDTO(Long bookingId, String tenResort, String tenLoaiPhong, String ngayBatDau, String ngayKetThuc,
            String hinhPhongUrl) {
        this.bookingId = bookingId;
        this.tenResort = tenResort;
        this.tenLoaiPhong = tenLoaiPhong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.hinhPhongUrl = hinhPhongUrl;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getTenResort() {
        return tenResort;
    }

    public void setTenResort(String tenResort) {
        this.tenResort = tenResort;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getHinhPhongUrl() {
        return hinhPhongUrl;
    }

    public void setHinhPhongUrl(String hinhPhongUrl) {
        this.hinhPhongUrl = hinhPhongUrl;
    }

}
