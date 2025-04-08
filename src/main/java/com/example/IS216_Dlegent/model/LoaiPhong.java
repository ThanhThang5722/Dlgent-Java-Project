package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "LOAI_PHONG")
public class LoaiPhong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_KHU_NGHI_DUONG", referencedColumnName = "ID")
    private KhuNghiDuong khuNghiDuong;

    @Column(name = "TEN_LOAI_PHONG", length = 255)
    private String tenLoaiPhong;

    @Column(name = "DIEN_TICH")
    private Double dienTich;

    @Column(name = "LOAI_PHONG_THEO_SO_LUONG", length = 255)
    private String loaiPhongTheoSoLuong;

    @Column(name = "LOAI_PHONG_THEO_TIEU_CHUAN", length = 255)
    private String loaiPhongTheoTieuChuan;

    @Column(name = "SO_GIUONG")
    private Integer soGiuong;

    @Column(name = "SO_NGUOI")
    private Integer soNguoi;

    @Column(name = "GIA")
    private BigDecimal gia;

    @ManyToMany
    @JoinTable(
        name = "TIEN_ICH_PHONG",
        joinColumns = @JoinColumn(name = "ID_PHONG"),
        inverseJoinColumns = @JoinColumn(name = "ID_TIEN_ICH")
    )
    private Set<TienIch> tienIchSet;

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

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public String getLoaiPhongTheoSoLuong() {
        return loaiPhongTheoSoLuong;
    }

    public void setLoaiPhongTheoSoLuong(String loaiPhongTheoSoLuong) {
        this.loaiPhongTheoSoLuong = loaiPhongTheoSoLuong;
    }

    public String getLoaiPhongTheoTieuChuan() {
        return loaiPhongTheoTieuChuan;
    }

    public void setLoaiPhongTheoTieuChuan(String loaiPhongTheoTieuChuan) {
        this.loaiPhongTheoTieuChuan = loaiPhongTheoTieuChuan;
    }

    public Integer getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(Integer soGiuong) {
        this.soGiuong = soGiuong;
    }

    public Integer getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(Integer soNguoi) {
        this.soNguoi = soNguoi;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    @Override
    public String toString() {
        return "LoaiPhong [id=" + id + ", khuNghiDuong=" + khuNghiDuong + ", tenLoaiPhong=" + tenLoaiPhong
                + ", dienTich=" + dienTich + ", loaiPhongTheoSoLuong=" + loaiPhongTheoSoLuong
                + ", loaiPhongTheoTieuChuan=" + loaiPhongTheoTieuChuan + ", soGiuong=" + soGiuong + ", soNguoi="
                + soNguoi + ", gia=" + gia + "]";
    }

    public Set<TienIch> getTienIchSet() {
        return tienIchSet;
    }

    public void setTienIchSet(Set<TienIch> tienIchSet) {
        this.tienIchSet = tienIchSet;
    }
    
}
