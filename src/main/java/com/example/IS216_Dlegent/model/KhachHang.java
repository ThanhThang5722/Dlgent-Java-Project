package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "KHACH_HANG")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_TAI_KHOAN", nullable = false, unique = true)
    private Account taiKhoan;

    @Column(name = "DIA_CHI")
    private String diaChi;

    @Column(name = "DIEM_TICH_LUY", nullable = false)
    private Integer diemTichLuy;

    public KhachHang() {
    }

    public KhachHang(Long id, Account taiKhoan, String diaChi, Integer diemTichLuy) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.diaChi = diaChi;
        this.diemTichLuy = diemTichLuy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(Account taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(Integer diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }

}
