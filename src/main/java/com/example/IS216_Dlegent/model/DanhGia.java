package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DANH_GIA")
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_KHU_NGHI_DUONG", nullable = false)
    private KhuNghiDuong khuNghiDuong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_KHACH_HANG", nullable = false)
    private KhachHang khachHang;

    @Column(name = "DIEM", nullable = false)
    private Integer diem;

    @Column(name = "NOI_DUNG", columnDefinition = "NVARCHAR2(1000)")
    private String noiDung;

    @Column(name = "THOIGIAN_TAO", nullable = false)
    private LocalDateTime thoiGianTao;

    // Constructors
    public DanhGia() {}

    public DanhGia(KhuNghiDuong khuNghiDuong, KhachHang khachHang, Integer diem, String noiDung, LocalDateTime thoiGianTao) {
        this.khuNghiDuong = khuNghiDuong;
        this.khachHang = khachHang;
        this.diem = diem;
        this.noiDung = noiDung;
        this.thoiGianTao = thoiGianTao;
    }

    // Getters and Setters

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

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public Integer getDiem() {
        return diem;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }
}
