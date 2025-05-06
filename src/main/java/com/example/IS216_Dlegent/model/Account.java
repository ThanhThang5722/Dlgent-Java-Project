package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TAI_KHOAN")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    /*
     * @ManyToOne
     * 
     * @JoinColumn(name = "USER_ID", nullable = false)
     * private User user;
     */
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "TEN_TAI_KHOAN", nullable = false, length = 255)
    private String username;

    @Column(name = "MAT_KHAU", nullable = false, length = 255)
    private String password;

    @Column(name = "TRANG_THAI", nullable = false, length = 20)
    private String status; // ACTIVE, INACTIVE

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private Date createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ACCOUNT_ROLE_GROUP", joinColumns = @JoinColumn(name = "ACCOUNT_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_GROUP_ID"))
    private Set<RoleGroup> roleGroups = new HashSet<>();

    public Set<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    // Constructors
    public Account() {
    }

    public Account(Long accountId, Long userId, String username, String password, String status, Date createdAt) {
        this.accountId = accountId;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setRoleGroups(Set<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }

}