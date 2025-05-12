package com.example.IS216_Dlegent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DICH_VU_YEU_CAU")
public class DichVuYeuCau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DICH_VU_KHU_NGHI_DUONG")
    private ServicesOfResort dichVuKhuNghiDuong;

    @ManyToOne
    @JoinColumn(name = "ID_CHI_TIET_DAT_PHONG")
    private ChiTietDatPhong chiTietDatPhong;

    public DichVuYeuCau(Long id, ServicesOfResort dichVuKhuNghiDuong, ChiTietDatPhong chiTietDatPhong) {
        this.id = id;
        this.dichVuKhuNghiDuong = dichVuKhuNghiDuong;
        this.chiTietDatPhong = chiTietDatPhong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServicesOfResort getDichVuKhuNghiDuong() {
        return dichVuKhuNghiDuong;
    }

    public void setDichVuKhuNghiDuong(ServicesOfResort dichVuKhuNghiDuong) {
        this.dichVuKhuNghiDuong = dichVuKhuNghiDuong;
    }

    public ChiTietDatPhong getChiTietDatPhong() {
        return chiTietDatPhong;
    }

    public void setChiTietDatPhong(ChiTietDatPhong chiTietDatPhong) {
        this.chiTietDatPhong = chiTietDatPhong;
    }

}
