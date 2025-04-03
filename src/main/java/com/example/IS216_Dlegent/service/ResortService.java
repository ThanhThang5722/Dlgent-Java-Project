package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.payload.request.InsertResortRequest;

@Service
public class ResortService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * This method calls the stored procedure ThemKhuNghiDuong to insert a resort.
     * @param token the authorization token from the cookie
     * @param request the resort information (name, address, city, district, province)
     * @return true if the resort was inserted successfully, false otherwise
     */
    @Transactional
    public boolean insertResort(String token, InsertResortRequest request) {
        String sql = "{ call ThemKhuNghiDuong(?, ?, ?, ?, ?, ?) }";

        try {
            jdbcTemplate.update(sql, token,
                request.getResortName(),
                request.getAddress(),
                request.getCity(),
                request.getDistrict(),
                request.getProvince());

            return true; // Successful execution
        } catch (DataAccessException e) {
            e.printStackTrace();  // Log the exception for debugging
            return false; // Error occurred during the stored procedure execution
        }
    }
}


