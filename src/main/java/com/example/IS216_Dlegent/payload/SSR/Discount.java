package com.example.IS216_Dlegent.payload.SSR;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Discount {
    private Long id;
    private Long idLoaiPhong;
    private String loaiGiamGia;
    private BigDecimal giaTri;
    private BigDecimal mucToiDa;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String trangThai;
    
    public Discount() {

    }
    public Discount(Long id, Long idLoaiPhong, String loaiGiamGia, BigDecimal giaTri, BigDecimal mucToiDa, String ngayBatDau, String ngayKetThuc,
            String trangThai) {
        this.id = id;
        this.idLoaiPhong = idLoaiPhong;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.mucToiDa = mucToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setIdLoaiPhong(Long idLoaiPhong){
        this.idLoaiPhong = idLoaiPhong;
    }
    public void setLoaiGiamGia(String loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }
    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }
    public void setMucToiDa(BigDecimal mucToiDa) {
        this.mucToiDa = mucToiDa;
    }
    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public Long getId(){
        return id;
    }
    public Long getIdLoaiPhong(){
        return idLoaiPhong;
    }
    public String getLoaiGiamGia() {
        return loaiGiamGia;
    }
    public BigDecimal getGiaTri() {
        return giaTri;
    }
    public BigDecimal getMucToiDa() {
        return mucToiDa;
    }
    public String getNgayBatDau() {
        return ngayBatDau;
    }
    public String getNgayKetThuc() {
        return ngayKetThuc;
    }
    public String getTrangThai() {
        return trangThai;
    }
}
