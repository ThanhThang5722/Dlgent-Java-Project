package com.example.IS216_Dlegent.payload.respsonse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.IS216_Dlegent.model.HoaDon;

public class HoaDonResponse {

    private Long id;
    private Long khachHangId;
    private String tenKhachHang;

    private Long doiTacId;
    private String tenDoiTac;

    private Long chiTietDatPhongId;
    private BigDecimal tongGiaTien;
    private LocalDateTime thoiGianThanhToan;
    private String hinhThucThanhToan;

    // Constructors
    public HoaDonResponse() {}

    public HoaDonResponse(HoaDon hoaDon) {
        this.id = hoaDon.getId();
        this.khachHangId = hoaDon.getKhachHang().getId();
        this.tenKhachHang = hoaDon.getKhachHang().getTaiKhoan().getUsername(); // giả sử có phương thức getTen()

        this.doiTacId = hoaDon.getDoiTac().getId();
        this.tenDoiTac = hoaDon.getDoiTac().getAccount().getUsername(); // giả sử có phương thức getTen()

        this.chiTietDatPhongId = hoaDon.getChiTietDatPhong().getId();
        this.tongGiaTien = hoaDon.getTongGiaTien();
        this.thoiGianThanhToan = hoaDon.getThoiGianThanhToan();
        this.hinhThucThanhToan = hoaDon.getHinhThucThanhToan();
    }

    // Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getKhachHangId() { return khachHangId; }

    public void setKhachHangId(Long khachHangId) { this.khachHangId = khachHangId; }

    public String getTenKhachHang() { return tenKhachHang; }

    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public Long getDoiTacId() { return doiTacId; }

    public void setDoiTacId(Long doiTacId) { this.doiTacId = doiTacId; }

    public String getTenDoiTac() { return tenDoiTac; }

    public void setTenDoiTac(String tenDoiTac) { this.tenDoiTac = tenDoiTac; }

    public Long getChiTietDatPhongId() { return chiTietDatPhongId; }

    public void setChiTietDatPhongId(Long chiTietDatPhongId) { this.chiTietDatPhongId = chiTietDatPhongId; }

    public BigDecimal getTongGiaTien() { return tongGiaTien; }

    public void setTongGiaTien(BigDecimal tongGiaTien) { this.tongGiaTien = tongGiaTien; }

    public LocalDateTime getThoiGianThanhToan() { return thoiGianThanhToan; }

    public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) { this.thoiGianThanhToan = thoiGianThanhToan; }

    public String getHinhThucThanhToan() { return hinhThucThanhToan; }

    public void setHinhThucThanhToan(String hinhThucThanhToan) { this.hinhThucThanhToan = hinhThucThanhToan; }
}
