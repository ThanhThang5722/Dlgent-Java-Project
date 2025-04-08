package com.example.IS216_Dlegent.controller.API;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.TienIch;
import com.example.IS216_Dlegent.payload.request.DeleteRoomTypeRequest;
import com.example.IS216_Dlegent.payload.request.InsertRoomTypeRequest;
import com.example.IS216_Dlegent.payload.request.UpdateUtilitiesRequest;
import com.example.IS216_Dlegent.payload.respsonse.TienIchResponse;
import com.example.IS216_Dlegent.repository.HinhPhongRepo;
import com.example.IS216_Dlegent.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/resort/room-type")
public class LoaiPhongAPI {

    private final TienIchService tienIchService;
    @Autowired
    private final LoaiPhongService loaiPhongService;
    @Autowired
    private final HinhPhongRepo hinhPhongRepo;

    public LoaiPhongAPI(LoaiPhongService loaiPhongService, HinhPhongRepo hinhPhongRepo, TienIchService tienIchService) {
        this.loaiPhongService = loaiPhongService;
        this.hinhPhongRepo = hinhPhongRepo;
        this.tienIchService = tienIchService;
    }
    @PostMapping
    public ResponseEntity<?> insertRoomType(@RequestBody InsertRoomTypeRequest roomTypeRequestRequest, HttpServletRequest request) {
        LoaiPhong loaiPhong = loaiPhongService.saveLoaiPhong(
            roomTypeRequestRequest.getKhuNghiDuongId(),
            roomTypeRequestRequest.getTenLoaiPhong(),
            roomTypeRequestRequest.getDienTich(),
            roomTypeRequestRequest.getLoaiPhongTheoSoLuong(),
            roomTypeRequestRequest.getLoaiPhongTheoTieuChuan(),
            roomTypeRequestRequest.getSoGiuong(),
            roomTypeRequestRequest.getSoNguoi(),
            roomTypeRequestRequest.getGia()
        );
        return ResponseEntity.ok(loaiPhong);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LoaiPhong> updateLoaiPhong(@PathVariable Long id, @RequestBody InsertRoomTypeRequest request) {
        try {
            LoaiPhong updatedLoaiPhong = loaiPhongService.updateLoaiPhong(
                request.getId(),
                request.getKhuNghiDuongId(),
                request.getTenLoaiPhong(),
                request.getDienTich(),
                request.getLoaiPhongTheoSoLuong(),
                request.getLoaiPhongTheoTieuChuan(),
                request.getSoGiuong(),
                request.getSoNguoi(),
                request.getGia()
            );
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("Test Loai Phong: {}", updatedLoaiPhong);
            return ResponseEntity.ok(updatedLoaiPhong);
        }
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteRoomTypes(@RequestBody DeleteRoomTypeRequest request) {
        List<Long> ids = request.getIds();

        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("Danh sách ID không hợp lệ");
        }

        loaiPhongService.deleteAllById(ids);

        return ResponseEntity.ok("Đã xóa thành công các loại phòng");
    }

    @GetMapping("/images/{roomTypeId}")
    public ResponseEntity<List<HinhPhong>> getRoomTypeImagesByRoomTypeId(@PathVariable Long roomTypeId) {
        List<HinhPhong> images = hinhPhongRepo.findByRoomTypeIDAndIsDeleted(roomTypeId, 0L);
        return ResponseEntity.ok(images);
    }
    
    @PutMapping("/images/{imageId}")
    public ResponseEntity<List<HinhPhong>> DeleteRoomTypeImageByImageID(@PathVariable Long imageId) {
        hinhPhongRepo.softDeleteById(imageId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/utilities")
    public ResponseEntity<String> UpdateUtilities(@RequestBody UpdateUtilitiesRequest request) {
        //tienIchService.updateTienIchPhong(request.getIdLoaiPhong(), request.getIdTienIch(), request.getStatus());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/utilities")
    public List<TienIchResponse> UpdateUtilities(@PathVariable Long id) {
        
        return tienIchService.getTienIchByLoaiPhongId(id)
                         .stream()
                         .map(TienIchResponse::fromEntity)
                         .toList();
    }
    @PostMapping("/{id}/utilities")
    @ResponseBody
    public ResponseEntity<Void> UpdateUtilities(@PathVariable Long id, @RequestBody List<Long> tienIchIds) {
        tienIchService.updateTienIchPhong(id, tienIchIds);
        return ResponseEntity.ok().build();
    }
}