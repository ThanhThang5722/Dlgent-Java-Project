package com.example.IS216_Dlegent.payload.respsonse;

import java.math.BigDecimal;
import java.util.List;

public class ResortSearchResponse {
    private String tenResort;
    private String diaChi;
    private BigDecimal giaThapNhat;
    private String imageUrl;
    private Double rate;
    // private List<Integer> dichVuMacDinh;
    private int relevanceScore;

    public ResortSearchResponse(String tenResort, String diaChi, BigDecimal giaThapNhat, String imageUrl, Double rate,
            /*List<Integer> dichVuMacDinh,*/ int relevanceScore) {
        this.tenResort = tenResort;
        this.diaChi = diaChi;
        this.giaThapNhat = giaThapNhat;
        this.imageUrl = imageUrl;
        this.rate = rate;
        // this.dichVuMacDinh = dichVuMacDinh;
        this.relevanceScore = relevanceScore;
    }

    public String getTenResort() {
        return tenResort;
    }

    public void setTenResort(String tenResort) {
        this.tenResort = tenResort;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public BigDecimal getGiaThapNhat() {
        return giaThapNhat;
    }

    public void setGiaThapNhat(BigDecimal giaThapNhat) {
        this.giaThapNhat = giaThapNhat;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    // public List<Integer> getDichVuMacDinh() {
    // return dichVuMacDinh;
    // }

    // public void setDichVuMacDinh(List<Integer> dichVuMacDinh) {
    // this.dichVuMacDinh = dichVuMacDinh;
    // }

    public int getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(int relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}