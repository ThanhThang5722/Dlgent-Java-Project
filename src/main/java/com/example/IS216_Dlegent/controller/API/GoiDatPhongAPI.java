package com.example.IS216_Dlegent.controller.API;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.payload.dto.GoiDatPhongDTO;
import com.example.IS216_Dlegent.payload.request.InsertGoiDatPhongRequest;
import com.example.IS216_Dlegent.repository.ServicesOfResortRepository;
import com.example.IS216_Dlegent.service.DichVuMacDinhService;
import com.example.IS216_Dlegent.service.GoiDatPhongService;
import com.example.IS216_Dlegent.service.LoaiPhongService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@RestController
@RequestMapping("/api/resort/booking-offer")
public class GoiDatPhongAPI {
    private static final Logger logger = LoggerFactory.getLogger(GoiDatPhongAPI.class);

    @Autowired
    GoiDatPhongService goiDatPhongService;

    @Autowired
    ServicesOfResortRepository servicesOfResortRepository;
    
    @Autowired
    DichVuMacDinhService dichVuMacDinhService;
    
    @Autowired
    LoaiPhongService loaiPhongService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getGoiDatPhongById(@PathVariable Long id) {
        try {
            logger.debug("Fetching GoiDatPhong with id: {}", id);
            GoiDatPhong goiDatPhong = goiDatPhongService.getGoiDatPhongById(id);
            if (goiDatPhong == null) {
                logger.warn("GoiDatPhong with id: {} not found", id);
                return ResponseEntity.notFound().build();
            }
            
            // Đảm bảo load danh sách dịch vụ mặc định
            List<DichVuMacDinh> dichVuMacDinhs = dichVuMacDinhService.findByGoiDatPhongId(id);
            if (dichVuMacDinhs == null) {
                logger.warn("No DichVuMacDinh found for GoiDatPhong with id: {}", id);
                return ResponseEntity.badRequest().body("Đang bug không có dịch vụ");
            }
            goiDatPhong.setDichVuMacDinhs(dichVuMacDinhs);
            
            // Chuyển đổi sang DTO để trả về
            GoiDatPhongDTO goiDatPhongDTO = new GoiDatPhongDTO(goiDatPhong);
            logger.debug("Successfully converted GoiDatPhong to DTO");
            
            return ResponseEntity.ok(goiDatPhongDTO);
        } catch (Exception e) {
            logger.error("Error retrieving GoiDatPhong with id: {}", id, e);
            return ResponseEntity.badRequest().body("Lỗi khi lấy thông tin gói đặt phòng: " + e.getMessage());
        }
    }
    
    @GetMapping("/room-type/{roomTypeId}/resort-id")
    public ResponseEntity<?> getResortIdByRoomTypeId(@PathVariable Long roomTypeId) {
        try {
            LoaiPhong loaiPhong = loaiPhongService.getLoaiPhongById(roomTypeId);
            if (loaiPhong == null || loaiPhong.getKhuNghiDuong() == null) {
                
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(loaiPhong.getKhuNghiDuong().getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy ID khu nghỉ dưỡng: " + e.getMessage());
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> insertBookingService(@RequestBody InsertGoiDatPhongRequest request) {
        try {
            // 1. Tạo gói đặt phòng mới
        GoiDatPhong goiDatPhong = new GoiDatPhong();
        goiDatPhong.setLoaiPhong(new LoaiPhong(request.getLoaiPhongId()));
            
            // Tính tổng giá tiền ban đầu (sẽ được cập nhật sau khi thêm dịch vụ)
            goiDatPhong.setTongGiaTien(BigDecimal.ZERO);
            
            // 2. Lưu gói đặt phòng để lấy ID
            goiDatPhong = goiDatPhongService.insertGoiDatPhong(goiDatPhong);
            
            // 3. Thêm dịch vụ mặc định cho gói đặt phòng và tính tổng giá tiền
            List<DichVuMacDinh> dsDichVuMacDinh = new ArrayList<>();
            
            // Xử lý danh sách dịch vụ
            if (request.getDsDichVuId() != null && !request.getDsDichVuId().isEmpty()) {
                for (int i = 0; i < request.getDsDichVuId().size(); i++) {
                    Long dichVuId = request.getDsDichVuId().get(i);
                    
                    // Lấy % giảm giá tương ứng với dịch vụ này
                    BigDecimal giamGia = BigDecimal.ZERO;
                    if (request.getDsGiamGia() != null && i < request.getDsGiamGia().size()) {
                        giamGia = BigDecimal.valueOf(request.getDsGiamGia().get(i));
                    }
                    
                    // Tìm dịch vụ của khu nghỉ dưỡng
                    ServicesOfResort dichVuKhuNghiDuong = servicesOfResortRepository
                            .findById(dichVuId)
                            .orElse(null);
                    
                    if (dichVuKhuNghiDuong != null) {
                        // Tạo dịch vụ mặc định mới
                        DichVuMacDinh dichVuMacDinh = new DichVuMacDinh();
                        dichVuMacDinh.setDichVuKhuNghiDuong(dichVuKhuNghiDuong);
                        dichVuMacDinh.setGoiDatPhong(goiDatPhong);
                        dichVuMacDinh.setGIAMGIA(giamGia); // Cập nhật giảm giá
                        
                        // Lưu dịch vụ mặc định
                        dichVuMacDinh = dichVuMacDinhService.save(dichVuMacDinh);
                        dsDichVuMacDinh.add(dichVuMacDinh);
                    }
                }
            }
            
            // 4. Lưu các dịch vụ mặc định và lấy danh sách đã lưu
            List<DichVuMacDinh> savedDichVuMacDinhs = dichVuMacDinhService.findByGoiDatPhong(goiDatPhong);
            goiDatPhong.setDichVuMacDinhs(savedDichVuMacDinhs);
            
            // 5. Tạo DTO để tính tổng giá tiền theo giá sau giảm
            GoiDatPhongDTO goiDatPhongDTO = new GoiDatPhongDTO(goiDatPhong);
            
            // 6. Cập nhật tổng giá tiền từ DTO vào entity
            goiDatPhong.setTongGiaTien(goiDatPhongDTO.getTongGiaTien());
            goiDatPhong = goiDatPhongService.insertGoiDatPhong(goiDatPhong);
            
            return ResponseEntity.ok(goiDatPhongDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi thêm gói đặt phòng: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookingService(@PathVariable Long id, @RequestBody InsertGoiDatPhongRequest request) {
        try {
            // 1. Tìm gói đặt phòng cần cập nhật
            GoiDatPhong goiDatPhong = goiDatPhongService.getGoiDatPhongById(id);
            if (goiDatPhong == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 2. Xóa tất cả dịch vụ mặc định hiện tại
            List<DichVuMacDinh> currentDichVus = dichVuMacDinhService.findByGoiDatPhongId(id);
            for (DichVuMacDinh dv : currentDichVus) {
                dichVuMacDinhService.delete(dv.getId());
            }
            
            // 3. Thêm các dịch vụ mặc định mới
            List<DichVuMacDinh> dsDichVuMacDinh = new ArrayList<>();
            
            if (request.getDsDichVuId() != null && !request.getDsDichVuId().isEmpty()) {
                for (int i = 0; i < request.getDsDichVuId().size(); i++) {
                    Long dichVuId = request.getDsDichVuId().get(i);
                    
                    // Lấy % giảm giá tương ứng với dịch vụ này
                    BigDecimal giamGia = BigDecimal.ZERO;
                    if (request.getDsGiamGia() != null && i < request.getDsGiamGia().size()) {
                        giamGia = BigDecimal.valueOf(request.getDsGiamGia().get(i));
                    }
                    
                    ServicesOfResort dichVuKhuNghiDuong = servicesOfResortRepository
                            .findById(dichVuId)
                            .orElse(null);
                    
                    if (dichVuKhuNghiDuong != null) {
                        DichVuMacDinh dichVuMacDinh = new DichVuMacDinh();
                        dichVuMacDinh.setDichVuKhuNghiDuong(dichVuKhuNghiDuong);
                        dichVuMacDinh.setGoiDatPhong(goiDatPhong);
                        dichVuMacDinh.setGIAMGIA(giamGia); // Cập nhật giảm giá
                        
                        dichVuMacDinh = dichVuMacDinhService.save(dichVuMacDinh);
                        dsDichVuMacDinh.add(dichVuMacDinh);
                    }
                }
            }
            
            // 4. Lấy danh sách dịch vụ mặc định đã cập nhật
            List<DichVuMacDinh> updatedDichVus = dichVuMacDinhService.findByGoiDatPhong(goiDatPhong);
            goiDatPhong.setDichVuMacDinhs(updatedDichVus);
            
            // 5. Tạo DTO để tính tổng giá tiền theo giá sau giảm
            GoiDatPhongDTO goiDatPhongDTO = new GoiDatPhongDTO(goiDatPhong);
            
            // 6. Cập nhật tổng giá tiền từ DTO vào entity
            goiDatPhong.setTongGiaTien(goiDatPhongDTO.getTongGiaTien());
            goiDatPhong = goiDatPhongService.insertGoiDatPhong(goiDatPhong);
            
            return ResponseEntity.ok(goiDatPhongDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật gói đặt phòng: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookingService(@PathVariable Long id) {
        try {
            // 1. Tìm gói đặt phòng cần xóa
            GoiDatPhong goiDatPhong = goiDatPhongService.getGoiDatPhongById(id);
            if (goiDatPhong == null) {
                return ResponseEntity.notFound().build();
            }
            
            // 2. Xóa tất cả dịch vụ mặc định
            List<DichVuMacDinh> dichVuMacDinhs = dichVuMacDinhService.findByGoiDatPhongId(id);
            for (DichVuMacDinh dv : dichVuMacDinhs) {
                dichVuMacDinhService.delete(dv.getId());
            }
            
            // 3. Xóa gói đặt phòng
            goiDatPhongService.deleteGoiDatPhong(id);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa gói đặt phòng: " + e.getMessage());
        }
    }
}
