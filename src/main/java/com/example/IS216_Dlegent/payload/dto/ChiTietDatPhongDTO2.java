package com.example.IS216_Dlegent.payload.dto;

import java.math.BigDecimal;
import java.util.List;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.DichVuYeuCau;

public class ChiTietDatPhongDTO2 {
    // CHI_TIET_DAT_PHONG
    private Long bookingId;
    private Integer soLuongPhong;
    private BigDecimal tongGiaTien;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String tinhTrang; // Trạng thái: "upcoming", "completed", "cancelled"

    List<DichVuYeuCau> dichVuYeuCaus;
    List<DichVuMacDinh> dichVuMacDinhs;

    // HOA_DON
    // private String thoiGianThanhToan;
    // private String hinhThucThanhToan;
    // email đối tác
    // sđt đối tác

    private String tenResort;

    // LOAI_PHONG
    private Double dienTich;
    private Integer soGiuong;
    private Integer soNguoi;
    private String tenLoaiPhong;

    // HINH_PHONG
    private String hinhAnhUrl;

    public ChiTietDatPhongDTO2(Long bookingId, Integer soLuongPhong, BigDecimal tongGiaTien, String ngayBatDau,
            String ngayKetThuc, List<DichVuYeuCau> dichVuYeuCaus, List<DichVuMacDinh> dichVuMacDinhs, String tenResort,
            Double dienTich, Integer soGiuong, Integer soNguoi, String hinhAnhUrl) {
        this.bookingId = bookingId;
        this.soLuongPhong = soLuongPhong;
        this.tongGiaTien = tongGiaTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dichVuYeuCaus = dichVuYeuCaus;
        this.dichVuMacDinhs = dichVuMacDinhs;
        this.tenResort = tenResort;
        this.dienTich = dienTich;
        this.soGiuong = soGiuong;
        this.soNguoi = soNguoi;
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public ChiTietDatPhongDTO2(Long bookingId, Integer soLuongPhong, BigDecimal tongGiaTien, String ngayBatDau,
            String ngayKetThuc, List<DichVuYeuCau> dichVuYeuCaus, List<DichVuMacDinh> dichVuMacDinhs, String tenResort,
            Double dienTich, Integer soGiuong, Integer soNguoi, String tenLoaiPhong, String hinhAnhUrl) {
        this.bookingId = bookingId;
        this.soLuongPhong = soLuongPhong;
        this.tongGiaTien = tongGiaTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dichVuYeuCaus = dichVuYeuCaus;
        this.dichVuMacDinhs = dichVuMacDinhs;
        this.tenResort = tenResort;
        this.dienTich = dienTich;
        this.soGiuong = soGiuong;
        this.soNguoi = soNguoi;
        this.tenLoaiPhong = tenLoaiPhong;
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public ChiTietDatPhongDTO2(Long bookingId, Integer soLuongPhong, BigDecimal tongGiaTien, String ngayBatDau,
            String ngayKetThuc, List<DichVuYeuCau> dichVuYeuCaus, List<DichVuMacDinh> dichVuMacDinhs, String tenResort,
            Double dienTich, Integer soGiuong, Integer soNguoi, String tenLoaiPhong, String hinhAnhUrl,
            String tinhTrang) {
        this.bookingId = bookingId;
        this.soLuongPhong = soLuongPhong;
        this.tongGiaTien = tongGiaTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.dichVuYeuCaus = dichVuYeuCaus;
        this.dichVuMacDinhs = dichVuMacDinhs;
        this.tenResort = tenResort;
        this.dienTich = dienTich;
        this.soGiuong = soGiuong;
        this.soNguoi = soNguoi;
        this.tenLoaiPhong = tenLoaiPhong;
        this.hinhAnhUrl = hinhAnhUrl;
        this.tinhTrang = tinhTrang;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getSoLuongPhong() {
        return soLuongPhong;
    }

    public void setSoLuongPhong(Integer soLuongPhong) {
        this.soLuongPhong = soLuongPhong;
    }

    public BigDecimal getTongGiaTien() {
        return tongGiaTien;
    }

    public void setTongGiaTien(BigDecimal tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
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

    public List<DichVuYeuCau> getDichVuYeuCaus() {
        return dichVuYeuCaus;
    }

    public void setDichVuYeuCaus(List<DichVuYeuCau> dichVuYeuCaus) {
        this.dichVuYeuCaus = dichVuYeuCaus;
    }

    public List<DichVuMacDinh> getDichVuMacDinhs() {
        return dichVuMacDinhs;
    }

    public void setDichVuMacDinhs(List<DichVuMacDinh> dichVuMacDinhs) {
        this.dichVuMacDinhs = dichVuMacDinhs;
    }

    public String getTenResort() {
        return tenResort;
    }

    public void setTenResort(String tenResort) {
        this.tenResort = tenResort;
    }

    public Integer getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(Integer soNguoi) {
        this.soNguoi = soNguoi;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public Integer getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(Integer soGiuong) {
        this.soGiuong = soGiuong;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public String getHinhAnhUrl() {
        return hinhAnhUrl;
    }

    public void setHinhAnhUrl(String hinhAnhUrl) {
        this.hinhAnhUrl = hinhAnhUrl;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
