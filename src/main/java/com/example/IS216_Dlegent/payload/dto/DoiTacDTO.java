package com.example.IS216_Dlegent.payload.dto;

import java.util.Date;

public class DoiTacDTO {
    //DOI_TAC
    private Long doiTacId;
    private String diaChi;
    //TAI_KHOAN
    private String tenTaiKhoan;
    private String trangThai;
    private Date createdAt;
    //NGUOI_DUNG
    private String hoTen;
    private String email;
    private String sdt;
    private String cccd;
    private Integer isDeleted;
    public DoiTacDTO() {}
    public DoiTacDTO(Long doiTacId, String diaChi, String tenTaiKhoan, String trangThai,
            String hoTen, String email, String sdt, String cccd, Integer isDeleted) {
        this.doiTacId = doiTacId;
        this.diaChi = diaChi;
        this.tenTaiKhoan = tenTaiKhoan;
        this.trangThai = trangThai;
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.cccd = cccd;
        this.isDeleted = isDeleted;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }
    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public String getCccd() {
        return cccd;
    }
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Long getDoiTacId() {
        return doiTacId;
    }
    public void setDoiTacId(Long doiTacId) {
        this.doiTacId = doiTacId;
    }
    
}
