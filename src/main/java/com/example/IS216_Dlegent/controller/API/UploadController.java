package com.example.IS216_Dlegent.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.service.CloudinaryService;
import com.example.IS216_Dlegent.service.HinhPhongService;

@RestController
@RequestMapping("/api")
public class UploadController {

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private HinhPhongService hinhPhongService;

    @PostMapping("/partner/roomType-images/upload-image")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("roomTypeId") Long roomTypeId) {
        try {
            String imageUrl = cloudinaryService.uploadFile(file);

            HinhPhong hinhPhong = new HinhPhong(roomTypeId,imageUrl, 0L);
            HinhPhong saved = hinhPhongService.save(hinhPhong);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
}