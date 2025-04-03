package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "KHU_NGHI_DUONG")
public class KhuNghiDuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DOI_TAC", nullable = false)
    private DoiTac doiTac;

    @Column(name = "TEN", nullable = false)
    private String ten;

    @Column(name = "DIA_CHI", nullable = false)
    private String diaChi;

    @ManyToOne
    @JoinColumn(name = "PHUONG_ID", nullable = false)
    private Phuong phuong;

    @Column(name = "IMG_360_URL", nullable = true)
    private String img360Url;

    @Column(name = "DANH_GIA", nullable = false)
    private Integer danhGia;

    // Constructors
    public KhuNghiDuong() {}

    public KhuNghiDuong(DoiTac doiTac, String ten, String diaChi, Phuong phuong, String img360Url, int danhGia) {
        this.doiTac = doiTac;
        this.ten = ten;
        this.diaChi = diaChi;
        this.phuong = phuong;
        this.img360Url = img360Url;
        this.danhGia = danhGia;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoiTac getDoiTac() {
        return doiTac;
    }

    public void setDoiTac(DoiTac doiTac) {
        this.doiTac = doiTac;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Phuong getPhuong() {
        return phuong;
    }

    public void setPhuong(Phuong phuong) {
        this.phuong = phuong;
    }

    public String getImg360Url() {
        return img360Url;
    }

    public void setImg360Url(String img360Url) {
        this.img360Url = img360Url;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }
}