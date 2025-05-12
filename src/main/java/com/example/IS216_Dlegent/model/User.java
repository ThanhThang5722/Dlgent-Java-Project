package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NGUOI_DUNG")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "HO_TEN", nullable = false, length = 100)
    private String fullName;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "SDT", nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @Column(name = "CCCD", nullable = false, unique = true, length = 15)
    private String cccd;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "IS_DELETED", nullable = false)
    private Integer isDeleted;

    // Constructors
    public User() {}

    public User(Long userId, String fullName, String email, String phoneNumber, String cccd, Date createdAt, Integer isDeleted) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public User get() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
