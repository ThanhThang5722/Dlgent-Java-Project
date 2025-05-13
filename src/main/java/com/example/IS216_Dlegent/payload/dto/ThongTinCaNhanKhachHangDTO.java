package com.example.IS216_Dlegent.payload.dto;

public class ThongTinCaNhanKhachHangDTO {
    private String tenTaiKhoan;
    private String hoTen;
    private String soDienThoai;
    private String CCCD;
    private String diaChi;
    private int diemTichLuy;
    private String email;

    public ThongTinCaNhanKhachHangDTO(String tenTaiKhoan, String hoTen, String soDienThoai, String cCCD, String diaChi,
            int diemTichLuy, String email) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.CCCD = cCCD;
        this.diaChi = diaChi;
        this.diemTichLuy = diemTichLuy;
        this.email = email;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String cCCD) {
        CCCD = cCCD;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
