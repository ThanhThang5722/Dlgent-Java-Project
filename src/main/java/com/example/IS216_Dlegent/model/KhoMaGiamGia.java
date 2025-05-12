package com.example.IS216_Dlegent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "KHO_MA_GIAM_GIA")
public class KhoMaGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_KHACH_HANG")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "ID_GIAMGIA")
    private GiamGia giamGia;

    public KhoMaGiamGia() {
    }

    public KhoMaGiamGia(Long id, KhachHang khachHang, GiamGia giamGia) {
        this.id = id;
        this.khachHang = khachHang;
        this.giamGia = giamGia;
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

    public GiamGia getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(GiamGia giamGia) {
        this.giamGia = giamGia;
    }

}
