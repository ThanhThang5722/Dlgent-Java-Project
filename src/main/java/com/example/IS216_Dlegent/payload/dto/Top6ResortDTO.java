package com.example.IS216_Dlegent.payload.dto;

import java.math.BigDecimal;

public class Top6ResortDTO {
    private String tenResort;
    private String thanhPho;
    private Integer soPhong;
    private BigDecimal giaThapNhat;
    private Integer soLuongDanhGia;
    private String imageUrl;
    private Double danhGia;

    public Top6ResortDTO(String tenResort, String thanhPho, Integer soPhong, BigDecimal giaThapNhat, Integer soLuongDanhGia, String imageUrl, Double danhGia) {
        this.tenResort = tenResort;
        this.thanhPho = thanhPho;
        this.soPhong = soPhong;
        this.giaThapNhat = giaThapNhat;
        this.soLuongDanhGia = soLuongDanhGia;
        this.imageUrl = imageUrl;
        this.danhGia = danhGia;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTenResort() {
        return tenResort;
    }

    public void setTenResort(String tenResort) {
        this.tenResort = tenResort;
    }

    public String getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(String thanhPho) {
        this.thanhPho = thanhPho;
    }

    public Integer getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(Integer soPhong) {
        this.soPhong = soPhong;
    }

    public BigDecimal getGiaThapNhat() {
        return giaThapNhat;
    }

    public void setGiaThapNhat(BigDecimal giaThapNhat) {
        this.giaThapNhat = giaThapNhat;
    }

    public Integer getSoLuongDanhGia() {
        return soLuongDanhGia;
    }

    public void setSoLuongDanhGia(Integer soLuongDanhGia) {
        this.soLuongDanhGia = soLuongDanhGia;
    }
    public Double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(Double danhGia) {
        this.danhGia = danhGia;
    }
}
