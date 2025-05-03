package com.example.IS216_Dlegent.payload.dto;

public class DanhGiaDTO {
    private String tenKhachHang;
    private String tenResort;
    private int diem;
    private String noiDung;
    private String thoiGianTaoFormatted;
    public DanhGiaDTO(){}
    public DanhGiaDTO(String tenKhachHang, String tenResort, int diem, String noiDung, String thoiGianTaoFormatted) {
        this.tenKhachHang = tenKhachHang;
        this.tenResort = tenResort;
        this.diem = diem;
        this.noiDung = noiDung;
        this.thoiGianTaoFormatted = thoiGianTaoFormatted;
    }
    public String getTenKhachHang() {
        return tenKhachHang;
    }
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    public String getTenResort() {
        return tenResort;
    }
    public void setTenResort(String tenResort) {
        this.tenResort = tenResort;
    }
    public int getDiem() {
        return diem;
    }
    public void setDiem(int diem) {
        this.diem = diem;
    }
    public String getNoiDung() {
        return noiDung;
    }
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    public String getThoiGianTaoFormatted() {
        return thoiGianTaoFormatted;
    }
    public void setThoiGianTaoFormatted(String thoiGianTaoFormatted) {
        this.thoiGianTaoFormatted = thoiGianTaoFormatted;
    }

    
}
