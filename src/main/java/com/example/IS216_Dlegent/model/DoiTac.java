package com.example.IS216_Dlegent.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "DOI_TAC")
public class DoiTac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_TAI_KHOAN", referencedColumnName = "ACCOUNT_ID", unique = true, nullable = false)
    private Account account;

    @Column(name = "DIA_CHI", nullable = false, length = 255)
    private String diaChi;

    @Column(name = "TAIKHOAN_NGANHANG", nullable = false, length = 15)
    private String taiKhoanNganHang;

    @Column(name = "TEN_TKNH", nullable = false, length = 255)
    private String tenTaiKhoanNganHang;

    @Column(name = "TEN_NGANHANG", nullable = false, length = 255)
    private String tenNganHang;

    @Column(name = "SO_DU", nullable = false, precision = 12, scale = 2)
    private BigDecimal soDu = BigDecimal.ZERO;

    @Column(name = "TINH_TRANG", nullable = false, length = 255)
    private String tinhTrang;

    // Constructors
    public DoiTac() {
    }

    public DoiTac(Account account, String diaChi, String taiKhoanNganHang, String tenTaiKhoanNganHang,
            String tenNganHang, BigDecimal soDu, String tinhTrang) {
        this.account = account;
        this.diaChi = diaChi;
        this.taiKhoanNganHang = taiKhoanNganHang;
        this.tenTaiKhoanNganHang = tenTaiKhoanNganHang;
        this.tenNganHang = tenNganHang;
        this.soDu = soDu;
        this.tinhTrang = tinhTrang;
    }

    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTaiKhoanNganHang() {
        return taiKhoanNganHang;
    }

    public void setTaiKhoanNganHang(String taiKhoanNganHang) {
        this.taiKhoanNganHang = taiKhoanNganHang;
    }

    public String getTenTaiKhoanNganHang() {
        return tenTaiKhoanNganHang;
    }

    public void setTenTaiKhoanNganHang(String tenTaiKhoanNganHang) {
        this.tenTaiKhoanNganHang = tenTaiKhoanNganHang;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    public BigDecimal getSoDu() {
        return soDu;
    }

    public void setSoDu(BigDecimal soDu) {
        if (soDu.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Số dư không thể nhỏ hơn 0");
        }
        this.soDu = soDu;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
