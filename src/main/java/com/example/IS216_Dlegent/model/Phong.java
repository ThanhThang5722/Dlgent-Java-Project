package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PHONG")
public class Phong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_KHU_NGHI_DUONG", nullable = false)
    private KhuNghiDuong khuNghiDuong;

    @ManyToOne
    @JoinColumn(name = "ID_LOAI_PHONG", nullable = false)
    private LoaiPhong loaiPhong;

    @Column(name = "MA_SO")
    private Integer maSo;

    @Column(name = "TINH_TRANG", nullable = false)
    private String tinhTrang;

    public Phong() {
    }

    public Phong(Long id, KhuNghiDuong khuNghiDuong, LoaiPhong loaiPhong, Integer maSo, String tinhTrang) {
        this.id = id;
        this.khuNghiDuong = khuNghiDuong;
        this.loaiPhong = loaiPhong;
        this.maSo = maSo;
        this.tinhTrang = tinhTrang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KhuNghiDuong getKhuNghiDuong() {
        return khuNghiDuong;
    }

    public void setKhuNghiDuong(KhuNghiDuong khuNghiDuong) {
        this.khuNghiDuong = khuNghiDuong;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public Integer getMaSo() {
        return maSo;
    }

    public void setMaSo(Integer maSo) {
        this.maSo = maSo;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }


    
}