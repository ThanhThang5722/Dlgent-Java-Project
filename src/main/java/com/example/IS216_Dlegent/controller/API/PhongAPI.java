package com.example.IS216_Dlegent.controller.API;

import java.lang.foreign.Linker.Option;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cloudinary.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.Phong;
import com.example.IS216_Dlegent.model.ThoiGianPhongBan;
import com.example.IS216_Dlegent.payload.dto.PhongDTO;
import com.example.IS216_Dlegent.payload.dto.ThoiGianPhongBanDTO;
import com.example.IS216_Dlegent.payload.dto.ThoiGianYeuCauDTO;
import com.example.IS216_Dlegent.payload.request.RoomRequest;
import com.example.IS216_Dlegent.payload.request.UpdatePhongRequest;
import com.example.IS216_Dlegent.repository.AccountAssignRoleRepository;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.service.DoiTacService;
import com.example.IS216_Dlegent.service.PermissionService;
import com.example.IS216_Dlegent.service.PhongService;
import com.example.IS216_Dlegent.service.ThoiGianPhongBanService;
import com.example.IS216_Dlegent.utils.CookieUtils;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/resort/phong")
public class PhongAPI {
    @Autowired
    private ThoiGianPhongBanService thoiGianPhongBanService;
    @Autowired
    private PhongService phongService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private DoiTacRepository doiTacRepository;
    @Autowired
    private AccountRepo accountRepository;
    public PhongAPI(ThoiGianPhongBanService thoiGianPhongBanService, PhongService phongService) {
        this.thoiGianPhongBanService = thoiGianPhongBanService;
        this.phongService = phongService;
    }
    @PostMapping("/kiem-tra")
    public ResponseEntity<Map<String, Object>> kiemTraPhong(@RequestBody ThoiGianYeuCauDTO thoiGianYeuCau) {
        
        LocalDateTime batDau = thoiGianYeuCau.getNgayBatDau();
        LocalDateTime ketThuc = thoiGianYeuCau.getNgayKetThuc();

        List<ThoiGianPhongBan> phongBan = thoiGianPhongBanService
            .getLichSuPhongBanTrongKhoangThoiGian(batDau, ketThuc);

        List<Phong> phongKhongBan = phongService
            .getPhongKhongBanTrongKhoangThoiGian(batDau, ketThuc);

        // Convert entities to DTOs
        List<ThoiGianPhongBanDTO> phongBanDTOs = phongBan.stream()
                .map(tg -> new ThoiGianPhongBanDTO().convertToDTO(tg)).collect(Collectors.toList());

        List<PhongDTO> phongKhongBanDTOs = phongKhongBan.stream()
                .map(p -> new PhongDTO().convertToDTO(p)).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("phongBan", phongBanDTOs);
        response.put("phongKhongBan", phongKhongBanDTOs);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> postMethodName(@RequestBody RoomRequest entity) {
        if(permissionService.hasPermission(entity.getKhuNghiDuongId(), "BOOKING_MANAGEMENT", "ADD") == false) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Không có quyền tạo phòng"));
        }
        phongService.createPhong(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }
    

    @PutMapping("")
    public ResponseEntity<?> updateTinhTrangPhong(@RequestBody UpdatePhongRequest entity) {
        permissionService.hasPermission(entity.getPhongId(), "BOOKING_MANAGEMENT", "EDIT");
        phongService.updateTinhTrangPhong(entity.getPhongId(), entity.getTinhTrang());
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> xoaPhong(@RequestBody Long idPhong) {
        if(phongService.xoaPhongTheoId(idPhong)) {
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/partner/kiem-tra")
    public ResponseEntity<Map<String, Object>> kiemTraPhongPartner(
            HttpServletRequest request,
            @RequestBody ThoiGianYeuCauDTO thoiGianYeuCau,
            @RequestParam Long doiTacId) {
        try {
            Long accountId = Long.parseLong(CookieUtils.getCookieValue(request, "account_id"));
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Chưa đăng nhập"));
            }
            if(permissionService.hasPermission(accountId, "BOOKING_MANAGEMENT", "VIEW") == false) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Không có quyền tạo kiểm tra phòng"));
            }
            //permissionService.hasPermission(doiTacId, "BOOKING_MANAGEMENT", "VIEW");
            LocalDateTime batDau = thoiGianYeuCau.getNgayBatDau();
            LocalDateTime ketThuc = thoiGianYeuCau.getNgayKetThuc();

            List<ThoiGianPhongBan> phongBan = thoiGianPhongBanService
                .getLichSuPhongBanTrongKhoangThoiGian(batDau, ketThuc)
                .stream()
                .filter(tg -> phongService.verifyPhongBelongsToPartner(tg.getPhong().getId(), doiTacId))
                .collect(Collectors.toList());

            List<Phong> phongKhongBan = phongService
                .getPhongKhongBanTrongKhoangThoiGian(batDau, ketThuc)
                .stream()
                .filter(p -> phongService.verifyPhongBelongsToPartner(p.getId(), doiTacId))
                .collect(Collectors.toList());

            // Convert entities to DTOs
            List<ThoiGianPhongBanDTO> phongBanDTOs = phongBan.stream()
                    .map(tg -> new ThoiGianPhongBanDTO().convertToDTO(tg))
                    .collect(Collectors.toList());

            List<PhongDTO> phongKhongBanDTOs = phongKhongBan.stream()
                    .map(p -> new PhongDTO().convertToDTO(p))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("phongBan", phongBanDTOs);
            response.put("phongKhongBan", phongKhongBanDTOs);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi kiểm tra phòng: " + e.getMessage()));
        }
    }

    @PostMapping("/partner")
    public ResponseEntity<?> createPhongPartner(HttpServletRequest request, @RequestBody RoomRequest entity, @RequestParam Long doiTacId) {
        try {
            Long accountId = Long.parseLong(CookieUtils.getCookieValue(request, "account_id"));
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Chưa đăng nhập"));
            }
            if(permissionService.hasPermission(accountId, "BOOKING_MANAGEMENT", "ADD") == false) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Không có quyền tạo phòng"));
            }
            if (!phongService.verifyResortBelongsToPartner(entity.getKhuNghiDuongId(), doiTacId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Không có quyền truy cập vào khu nghỉ dưỡng này"));
            }
            Phong phong = phongService.createPhong(entity);
            PhongDTO phongDTO = new PhongDTO().convertToDTO(phong);
            return ResponseEntity.status(HttpStatus.CREATED).body(phongDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi tạo phòng: " + e.getMessage()));
        }
    }

    @PutMapping("/partner")
    public ResponseEntity<?> updateTinhTrangPhongPartner(HttpServletRequest request, @RequestBody UpdatePhongRequest entity, @RequestParam Long doiTacId) {
        try {
            Long accountId = Long.parseLong(CookieUtils.getCookieValue(request, "account_id"));
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Chưa đăng nhập"));
            }
            if(permissionService.hasPermission(accountId, "BOOKING_MANAGEMENT", "EDIT") == false) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Không có quyền câp nhật trạng thái phòng"));
            }
            // Verify that the room belongs to the partner
            if (!phongService.verifyPhongBelongsToPartner(entity.getPhongId(), doiTacId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Không có quyền truy cập vào phòng này"));
            }
            phongService.updateTinhTrangPhong(entity.getPhongId(), entity.getTinhTrang());
            return ResponseEntity.ok().body(Map.of("message", "Cập nhật trạng thái phòng thành công"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Lỗi khi cập nhật trạng thái phòng: " + e.getMessage()));
        }
    }

    @DeleteMapping("/partner")
    public ResponseEntity<?> xoaPhongPartner(HttpServletRequest request, @RequestBody Long idPhong, @RequestParam Long doiTacId) {
        try {
            Long accountId = Long.parseLong(CookieUtils.getCookieValue(request, "account_id"));
            if (accountId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Chưa đăng nhập"));
            }
            if(permissionService.hasPermission(accountId, "BOOKING_MANAGEMENT", "DELETE") == false) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Không có quyền xóa phòng"));
            }
            if (phongService.xoaPhongTheoId(idPhong)) {
                return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "Xóa phòng thành công"));
            }
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("error", "Không thể xóa phòng"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("error", "Lỗi khi xóa phòng: " + e.getMessage()));
        }
    }
}
