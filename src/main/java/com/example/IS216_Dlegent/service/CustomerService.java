package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean activateCustomer(String username) {
        int updatedRows = customerRepository.activateCustomerByUsername(username);
        return updatedRows > 0;
    }
}
