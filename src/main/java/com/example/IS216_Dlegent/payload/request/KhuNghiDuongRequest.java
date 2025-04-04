package com.example.IS216_Dlegent.payload.request;

public class KhuNghiDuongRequest {
    private String ten;
    private String diaChi;

    KhuNghiDuongRequest() {}

    public KhuNghiDuongRequest(String ten, String diaChi) {
        this.ten = ten;
        this.diaChi = diaChi;
    }
    public String getTen() {
        return ten;
    }
    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
