package com.example.IS216_Dlegent.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.payload.request.InsertResortRequest;
import com.example.IS216_Dlegent.repository.KhuNghiDuongRepo;
@Service
public class KhuNghiDuongService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
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

    @Autowired
    private KhuNghiDuongRepo khuNghiDuongRepo;
    public List<KhuNghiDuong> getKhuNghiDuongsByDoiTacId(Long doiTacId) {
        return khuNghiDuongRepo.findByDoiTac_Id(doiTacId);
    }
    public List<KhuNghiDuong> getAllKhuNghiDuongs() {
        return khuNghiDuongRepo.findAll();
    }

    public KhuNghiDuong findById(Long ID) {
        Optional<KhuNghiDuong> result = khuNghiDuongRepo.findById(ID);
        return result.orElse(null);
    }

    public KhuNghiDuong save(KhuNghiDuong khuNghiDuong) {
        return khuNghiDuongRepo.save(khuNghiDuong);
    }
}
