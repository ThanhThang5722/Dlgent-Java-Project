package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "DAT_PHONG")
public class DatPhong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_KHACH_HANG", referencedColumnName = "ID")
    private KhachHang khachHang;

    @Column(name = "THOI_GIAN_TAO", nullable = false)
    private LocalDateTime thoiGianTao;

    @Column(name = "TINH_TRANG", nullable = false, length = 255)
    private String tinhTrang;

    @Column(name = "TONG_GIATIEN", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongGiaTien;

    @Column(name = "TAIKHOAN_NGANHANG", nullable = true, length = 15)
    private String taiKhoanNganHang;

    @Column(name = "TEN_TKNH", nullable = true, length = 255)
    private String tenTKNH;

    @Column(name = "TEN_NGANHANG", nullable = true, length = 255)
    private String tenNganHang;

    public DatPhong() {}

    public DatPhong(Long id, KhachHang khachHang, LocalDateTime thoiGianTao, String tinhTrang, BigDecimal tongGiaTien,
            String taiKhoanNganHang, String tenTKNH, String tenNganHang) {
        this.id = id;
        this.khachHang = khachHang;
        this.thoiGianTao = thoiGianTao;
        this.tinhTrang = tinhTrang;
        this.tongGiaTien = tongGiaTien;
        this.taiKhoanNganHang = taiKhoanNganHang;
        this.tenTKNH = tenTKNH;
        this.tenNganHang = tenNganHang;
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

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public BigDecimal getTongGiaTien() {
        return tongGiaTien;
    }

    public void setTongGiaTien(BigDecimal tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
    }

    public String getTaiKhoanNganHang() {
        return taiKhoanNganHang;
    }

    public void setTaiKhoanNganHang(String taiKhoanNganHang) {
        this.taiKhoanNganHang = taiKhoanNganHang;
    }

    public String getTenTKNH() {
        return tenTKNH;
    }

    public void setTenTKNH(String tenTKNH) {
        this.tenTKNH = tenTKNH;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    
}
