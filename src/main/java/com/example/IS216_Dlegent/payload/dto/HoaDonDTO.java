package com.example.IS216_Dlegent.payload.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HoaDonDTO {
    private Long maHoaDon;
    private String tenKhachHang;
    private String tenResort;
    private BigDecimal thanhTien;
    private LocalDateTime ngayDat;
    private String ngayDatFormatted;

    public HoaDonDTO() {
    }

    public HoaDonDTO(Long maHoaDon, String tenKhachHang, String tenResort, BigDecimal thanhTien, 
                    LocalDateTime ngayDat) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.tenResort = tenResort;
        this.thanhTien = thanhTien;
        this.ngayDat = ngayDat;
        
        // Format date
        if (ngayDat != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.ngayDatFormatted = ngayDat.format(formatter);
        }
    }

    public Long getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(Long maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenResort() {
        return tenResort;
    }

    public void setTenResort(String tenResort) {
        this.tenResort = tenResort;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
        
        // Format date
        if (ngayDat != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            this.ngayDatFormatted = ngayDat.format(formatter);
        }
    }

    public String getNgayDatFormatted() {
        return ngayDatFormatted;
    }
} 