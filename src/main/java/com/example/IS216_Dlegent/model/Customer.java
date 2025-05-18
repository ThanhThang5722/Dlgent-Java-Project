package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;

@Entity
@Table(name = "KHACH_HANG")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_TAI_KHOAN", nullable = false, unique = true)
    private Account account;

    @Column(name = "DIA_CHI", length = 255)
    private String address;

    @Column(name = "DIEM_TICH_LUY", nullable = false)
    private Integer loyaltyPoints = 0;

    @Column(name = "TINH_TRANG", nullable = false, length = 255)
    private String tinhTrang;

    // Getters and setters
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

}
