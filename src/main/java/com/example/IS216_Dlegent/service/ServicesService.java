package com.example.IS216_Dlegent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.Services;
import com.example.IS216_Dlegent.repository.ServicesRepository;

@Service
public class ServicesService {
    @Autowired
    private ServicesRepository servicesRepository;
    public List<Services> findAll(){
        return servicesRepository.findAll();
    }
}
