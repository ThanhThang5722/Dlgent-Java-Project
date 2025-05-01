package com.example.IS216_Dlegent.payload.respsonse;

import java.math.BigDecimal;
import java.util.List;

import com.example.IS216_Dlegent.payload.dto.DichVuDTO;

public class ResortSearchResponse {
    private Long id;
    private String tenResort;
    private String diaChi;
    private BigDecimal giaThapNhat;
    private String imageUrl;
    private Double rate;
    private int relevanceScore;
    private List<DichVuDTO> dichVuMacDinhs;

    public ResortSearchResponse(Long id, String tenResort, String diaChi, BigDecimal giaThapNhat, String imageUrl, Double rate,
            int relevanceScore) {
        this.id = id;
        this.tenResort = tenResort;
        this.diaChi = diaChi;
        this.giaThapNhat = giaThapNhat;
        this.imageUrl = imageUrl;
        this.rate = rate;
        this.relevanceScore = relevanceScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(int relevanceScore) {
        this.relevanceScore = relevanceScore;
    }


    public List<DichVuDTO> getDichVuMacDinhs() {
        return dichVuMacDinhs;
    }


    public void setDichVuMacDinhs(List<DichVuDTO> dichVuMacDinhs) {
        this.dichVuMacDinhs = dichVuMacDinhs;
    }

}