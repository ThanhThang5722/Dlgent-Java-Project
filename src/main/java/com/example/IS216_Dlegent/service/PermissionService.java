package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.repository.jdbc.JdbcPermission;
@Service
public class PermissionService {
    @Autowired
    private JdbcPermission jdbcPermission;
    public boolean hasPermission(Long accountId, String functionName, String action) {
        return jdbcPermission.hasPermission(accountId, functionName, action);
    }
}
