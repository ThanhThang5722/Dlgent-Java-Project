package com.example.IS216_Dlegent.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.IS216_Dlegent.payload.dto.Top6ResortDTO;
import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import com.example.IS216_Dlegent.repository.jdbc.JdbcResortRepository;
import com.example.IS216_Dlegent.repository.jdbc.JdbcTop6ResortRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private JdbcTop6ResortRepository jdbcTop6ResortRepository;

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
            e.printStackTrace(); // Log the exception for debugging
            return false; // Error occurred during the stored procedure execution
        }
    }

    @Autowired
    private KhuNghiDuongRepo khuNghiDuongRepo;

    @Autowired
    private JdbcResortRepository jdbcResortRepository;

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

    public boolean updateKhuNghiDuong(Long id, String ten, String diaChi) {
        try {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("It run until here!!!!");

            int rowsUpdated = khuNghiDuongRepo.updateTenAndDiaChiById(id, ten, diaChi);
            return rowsUpdated > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hardDeleteKhuNghiDuong(List<Long> ids) {
        try {
            khuNghiDuongRepo.deleteAllById(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<ResortSearchResponse> searchResorts(String tenResort, LocalDateTime checkIn, LocalDateTime checkOut,
                                                    int soNguoi) {
        if (tenResort == null || tenResort.trim().isEmpty()) {
            tenResort = "";
        }

        if (checkIn == null) {
            checkIn = LocalDateTime.now();
        }

        if (checkOut == null) {
            checkOut = checkIn.plusDays(1); // mac dinh checkout sau 1 ngay
        }

        return jdbcResortRepository.searchResorts(tenResort, checkIn, checkOut, soNguoi);
    }

    public List<Top6ResortDTO> getTop6ResortsByRating(){
        return jdbcTop6ResortRepository.getTop6ResortsByRating();
    }
}
