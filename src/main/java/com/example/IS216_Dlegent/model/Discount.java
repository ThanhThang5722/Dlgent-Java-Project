package com.example.IS216_Dlegent.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GIAMGIA")
public class Discount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_LOAI_PHONG", nullable = false)
    private Long idLoaiPhong;

    @Column(name = "LOAI_GIAMGIA", nullable = false)
    private String loaiGiamGia;

    @Column(name = "GIA_TRI", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaTri;

    @Column(name = "MUC_TOI_DA", precision = 12, scale = 2)
    private BigDecimal mucToiDa;

    @Column(name = "NGAY_BAT_DAU", nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(name = "NGAY_KET_THUC", nullable = false)
    private LocalDateTime ngayKetThuc;

    @Column(name = "TINH_TRANG", nullable = false)
    private String trangThai;

    // === Getters và Setters ===
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLoaiPhong() {
        return idLoaiPhong;
    }

    public void setIdLoaiPhong(Long idLoaiPhong) {
        this.idLoaiPhong = idLoaiPhong;
    }

    public String getLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(String loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public BigDecimal getMucToiDa() {
        return mucToiDa;
    }

    public void setMucToiDa(BigDecimal mucToiDa) {
        this.mucToiDa = mucToiDa;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Discount(Long id, Long idLoaiPhong, String loaiGiamGia, BigDecimal giaTri, BigDecimal mucToiDa,
            LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, String trangThai) {
        this.id = id;
        this.idLoaiPhong = idLoaiPhong;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.mucToiDa = mucToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public Discount() {
    }
    
}
