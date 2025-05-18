package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHI_TIET_DAT_PHONG")
public class ChiTietDatPhong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_DAT_PHONG", referencedColumnName = "ID")
    private DatPhong datPhong;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_GOI_DAT_PHONG", referencedColumnName = "ID")
    private GoiDatPhong goiDatPhong;
    // TODO: GiamGia set lại về NULLABLE để tiện test tính năng add vào giỏ hàng,
    // sau khi clear được GIAMGIA ntn thì sửa lại
    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_GIAMGIA", referencedColumnName = "ID")
    private GiamGia giamGia;

    @Column(name = "SO_LUONG_PHONG", nullable = false)
    private Integer soLuongPhong;

    @Column(name = "SO_LUONG_DICH_VU_YEU_CAU", nullable = false)
    private Integer soLuongDichVuYeuCau;

    @Column(name = "TONG_GIATIEN", nullable = false, precision = 12, scale = 2)
    private BigDecimal tongGiaTien;

    @Column(name = "NGAY_BAT_DAU", nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(name = "NGAY_KET_THUC", nullable = false)
    private LocalDateTime ngayKetThuc;

    @Column(name = "TRANG_THAI", nullable = false, length = 255)
    private String trangThai;

    public ChiTietDatPhong() {
    }

    public ChiTietDatPhong(Long id, DatPhong datPhong, GoiDatPhong goiDatPhong, GiamGia giamGia, int soLuongPhong,
            Integer soLuongDichVuYeuCau, BigDecimal tongGiaTien, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc,
            String trangThai) {
        this.id = id;
        this.datPhong = datPhong;
        this.goiDatPhong = goiDatPhong;
        this.giamGia = giamGia;
        this.soLuongPhong = soLuongPhong;
        this.soLuongDichVuYeuCau = soLuongDichVuYeuCau;
        this.tongGiaTien = tongGiaTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DatPhong getDatPhong() {
        return datPhong;
    }

    public void setDatPhong(DatPhong datPhong) {
        this.datPhong = datPhong;
    }

    public GoiDatPhong getGoiDatPhong() {
        return goiDatPhong;
    }

    public void setGoiDatPhong(GoiDatPhong goiDatPhong) {
        this.goiDatPhong = goiDatPhong;
    }

    public GiamGia getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(GiamGia giamGia) {
        this.giamGia = giamGia;
    }

    public Integer getSoLuongPhong() {
        return soLuongPhong;
    }

    public void setSoLuongPhong(Integer soLuongPhong) {
        this.soLuongPhong = soLuongPhong;
    }

    public int getSoLuongDichVuYeuCau() {
        return soLuongDichVuYeuCau;
    }

    public void setSoLuongDichVuYeuCau(int soLuongDichVuYeuCau) {
        this.soLuongDichVuYeuCau = soLuongDichVuYeuCau;
    }

    public BigDecimal getTongGiaTien() {
        return tongGiaTien;
    }

    public void setTongGiaTien(BigDecimal tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
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

    @Override
    public String toString() {
        return "ChiTietDatPhong [id=" + id + ", datPhong=" + datPhong + ", goiDatPhong=" + goiDatPhong + ", giamGia="
                + giamGia + ", soLuongPhong=" + soLuongPhong + ", soLuongDichVuYeuCau=" + soLuongDichVuYeuCau
                + ", tongGiaTien=" + tongGiaTien + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc
                + ", trangThai=" + trangThai + "]";
    }

}
