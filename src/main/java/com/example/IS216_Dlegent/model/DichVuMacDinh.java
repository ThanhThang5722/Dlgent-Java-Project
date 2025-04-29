package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "DICH_VU_MAC_DINH")
public class DichVuMacDinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DICH_VU_KHU_NGHI_DUONG", nullable = false)
    private ServicesOfResort dichVuKhuNghiDuong;
    
    private BigDecimal GIAMGIA;
    
    @ManyToOne
    //@MapsId("ID_GOI_DAT_PHONG")
    @JoinColumn(name = "ID_GOI_DAT_PHONG")
    private GoiDatPhong goiDatPhong;

    public DichVuMacDinh(){}

    public DichVuMacDinh(Long id, ServicesOfResort dichVuKhuNghiDuong, BigDecimal gIAMGIA, GoiDatPhong goiDatPhong) {
        this.id = id;
        this.dichVuKhuNghiDuong = dichVuKhuNghiDuong;
        GIAMGIA = gIAMGIA;
        this.goiDatPhong = goiDatPhong;
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

    public BigDecimal getGIAMGIA() {
        return GIAMGIA;
    }

    public void setGIAMGIA(BigDecimal gIAMGIA) {
        GIAMGIA = gIAMGIA;
    }

    public GoiDatPhong getGoiDatPhong() {
        return goiDatPhong;
    }

    public void setGoiDatPhong(GoiDatPhong goiDatPhong) {
        this.goiDatPhong = goiDatPhong;
    }
    
}