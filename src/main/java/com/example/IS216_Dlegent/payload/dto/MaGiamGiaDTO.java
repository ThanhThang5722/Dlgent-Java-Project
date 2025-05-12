package com.example.IS216_Dlegent.payload.dto;

import java.math.BigDecimal;

public class MaGiamGiaDTO {
    private Long id;
    private String loaiGiamGia;
    private BigDecimal giaTri;
    private BigDecimal mucToiDa;
    private String ngayBatDau;
    private String ngayKetThuc;
    private Integer mucQuyDoi;
    private String maCode;
    private String trangThai;

    public MaGiamGiaDTO(String loaiGiamGia, BigDecimal giaTri, BigDecimal mucToiDa, String ngayBatDau,
            String ngayKetThuc, Integer mucQuyDoi) {
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.mucToiDa = mucToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.mucQuyDoi = mucQuyDoi;
    }

    public MaGiamGiaDTO(Long id, String loaiGiamGia, BigDecimal giaTri, BigDecimal mucToiDa, String ngayBatDau,
            String ngayKetThuc, Integer mucQuyDoi, String maCode, String trangThai) {
        this.id = id;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.mucToiDa = mucToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.mucQuyDoi = mucQuyDoi;
        this.maCode = maCode;
        this.trangThai = trangThai;
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

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Integer getMucQuyDoi() {
        return mucQuyDoi;
    }

    public void setMucQuyDoi(Integer mucQuyDoi) {
        this.mucQuyDoi = mucQuyDoi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaCode() {
        return maCode;
    }

    public void setMaCode(String maCode) {
        this.maCode = maCode;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
