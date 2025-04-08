package com.example.IS216_Dlegent.payload.request;

import java.math.BigDecimal;

public class InsertRoomTypeRequest {
    private Long id;
    private Long khuNghiDuongId;
    private String tenLoaiPhong;
    private Double dienTich;
    private String loaiPhongTheoSoLuong;
    private String loaiPhongTheoTieuChuan;
    private Integer soGiuong;
    private Integer soNguoi;
    private BigDecimal gia;
    public InsertRoomTypeRequest() {
    }
    public InsertRoomTypeRequest(Long id, Long khuNghiDuongId, String tenLoaiPhong, Double dienTich,
            String loaiPhongTheoSoLuong, String loaiPhongTheoTieuChuan, Integer soGiuong, Integer soNguoi,
            BigDecimal gia) {
        this.id = id;
        this.khuNghiDuongId = khuNghiDuongId;
        this.tenLoaiPhong = tenLoaiPhong;
        this.dienTich = dienTich;
        this.loaiPhongTheoSoLuong = loaiPhongTheoSoLuong;
        this.loaiPhongTheoTieuChuan = loaiPhongTheoTieuChuan;
        this.soGiuong = soGiuong;
        this.soNguoi = soNguoi;
        this.gia = gia;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getKhuNghiDuongId() {
        return khuNghiDuongId;
    }
    public void setKhuNghiDuongId(Long khuNghiDuongId) {
        this.khuNghiDuongId = khuNghiDuongId;
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
    
}
