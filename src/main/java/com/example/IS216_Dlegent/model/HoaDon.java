package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "HOA_DON") // Giữ tên bảng là HAHA như bạn đặt
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_KHACH_HANG", referencedColumnName = "ID")
    private KhachHang khachHang;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_DOI_TAC", referencedColumnName = "ID")
    private DoiTac doiTac;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_CHI_TIET_DAT_PHONG", referencedColumnName = "ID")
    private ChiTietDatPhong chiTietDatPhong;

    @Column(name = "TONG_GIATIEN", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongGiaTien;

    @Column(name = "THOIGIAN_THANHTOAN", nullable = false)
    private LocalDateTime thoiGianThanhToan;

    @Column(name = "HINH_THUC_THANH_TOAN", nullable = false, length = 255)
    private String hinhThucThanhToan;

    public HoaDon() {}
    public HoaDon(Long id, KhachHang khachHang, DoiTac doiTac, ChiTietDatPhong chiTietDatPhong, BigDecimal tongGiaTien,
            LocalDateTime thoiGianThanhToan, String hinhThucThanhToan) {
        this.id = id;
        this.khachHang = khachHang;
        this.doiTac = doiTac;
        this.chiTietDatPhong = chiTietDatPhong;
        this.tongGiaTien = tongGiaTien;
        this.thoiGianThanhToan = thoiGianThanhToan;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public KhachHang getKhachHang() {
        return khachHang;
    }
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
    public DoiTac getDoiTac() {
        return doiTac;
    }
    public void setDoiTac(DoiTac doiTac) {
        this.doiTac = doiTac;
    }
    public ChiTietDatPhong getChiTietDatPhong() {
        return chiTietDatPhong;
    }
    public void setChiTietDatPhong(ChiTietDatPhong chiTietDatPhong) {
        this.chiTietDatPhong = chiTietDatPhong;
    }
    public BigDecimal getTongGiaTien() {
        return tongGiaTien;
    }
    public void setTongGiaTien(BigDecimal tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
    }
    public LocalDateTime getThoiGianThanhToan() {
        return thoiGianThanhToan;
    }
    public void setThoiGianThanhToan(LocalDateTime thoiGianThanhToan) {
        this.thoiGianThanhToan = thoiGianThanhToan;
    }
    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }
    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    
}

