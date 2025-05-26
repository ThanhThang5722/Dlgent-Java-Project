package com.example.IS216_Dlegent.payload.dto;

import java.math.BigDecimal;
import java.util.function.DoubleToLongFunction;

public class DichVuDTO {
    private Long id;
    private String tenDichVu;
    private String moTa;
    private Double gia;

    public DichVuDTO(Long id, String tenDichVu, String moTa, Double gia) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.moTa = moTa;
        this.gia = gia;
    }

    public DichVuDTO(Long id, String tenDichVu, Double gia) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}
