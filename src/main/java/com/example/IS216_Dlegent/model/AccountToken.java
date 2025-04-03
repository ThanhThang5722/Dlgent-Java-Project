package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT_TOKEN")
public class AccountToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID")
    private Long id;

    /*@ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;
    */
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;
    
    @Column(name = "TOKEN_VALUE", unique = true, nullable = false, length = 500)
    private String tokenValue;

    @Column(name = "EXPIRES_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @Column(name = "ISSUED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedAt;

    @Column(name = "IS_REVOKED", length = 1)
    private int isRevoked;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getIsRevoked() {
        return isRevoked;
    }

    public void setIsRevoked(int isRevoked) {
        this.isRevoked = isRevoked;
    }
}
