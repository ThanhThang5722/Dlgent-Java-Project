package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.repository.jdbc.JdbcWorkerAccount;

@Service
public class WorkerAccountService {
    @Autowired
    private JdbcWorkerAccount jdbcWorkerAccount;

    public Long getPartnerIdOfWorker(Long accountId) {
        return jdbcWorkerAccount.getPartnerIdOfWorker(accountId);
    }
}
