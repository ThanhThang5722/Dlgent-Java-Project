package com.example.IS216_Dlegent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACCOUNT_ROLE_GROUP")
public class AccountRoleGroup {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    @Column(name = "ROLE_GROUP_ID")
    private Long roleGroupId;

    public AccountRoleGroup(){}

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(Long roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public AccountRoleGroup(Long accountId, Long roleGroupId) {
        this.accountId = accountId;
        this.roleGroupId = roleGroupId;
    }

    
}
