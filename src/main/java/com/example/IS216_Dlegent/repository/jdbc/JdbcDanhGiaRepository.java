package com.example.IS216_Dlegent.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;

@Repository
public class JdbcDanhGiaRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Calls the Oracle function tinhDanhGiaTBResort to get average rating for a resort
     * 
     * @param resortId ID of the resort
     * @return Average rating as a Double, 0.0 if no ratings exist
     */
    public Double getAverageRatingByResort(Long resortId) {
        String sql = "{? = call tinhDanhGiaTBResort(?)}";
        
        return jdbcTemplate.execute(
            (Connection con) -> {
                CallableStatement cs = con.prepareCall(sql);
                cs.registerOutParameter(1, Types.DOUBLE); // output parameter
                cs.setLong(2, resortId);                  // input parameter
                return cs;
            },
            (CallableStatement cs) -> {
                cs.execute();
                return cs.getDouble(1); // Get the function return value
            }
        );
    }
}