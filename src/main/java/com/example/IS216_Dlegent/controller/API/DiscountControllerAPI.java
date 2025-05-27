package com.example.IS216_Dlegent.controller.API;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.Discount;
import com.example.IS216_Dlegent.payload.request.DiscountCreateRequest;
import com.example.IS216_Dlegent.service.DiscountService;

@RestController
@RequestMapping("/api/discount")
public class DiscountControllerAPI{

    @Autowired
    private DiscountService discountService;


    @PostMapping
    public ResponseEntity<?> createDiscount(@RequestBody DiscountCreateRequest discount){
        String dateTimeStrBD = discount.getNgayBatDau();
        DateTimeFormatter formatterBD = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTimeBD = LocalDateTime.parse(dateTimeStrBD, formatterBD);


        String dateTimeStrKT = discount.getNgayKetThuc();
        DateTimeFormatter formatterKT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTimeKT = LocalDateTime.parse(dateTimeStrKT, formatterKT);
        System.out.println("Hereeeeee");
        Discount disc  = discountService.saveDiscount(
            discount.getIdLoaiPhong(),
            discount.getLoaiGiamGia(),
            discount.getGiaTri(),
            discount.getMucToiDa(),
            localDateTimeBD,
            localDateTimeKT,
            discount.getTrangThai()
        );
        
        return ResponseEntity.ok(discount);
    }

    @PutMapping("")
    public ResponseEntity<?> updateDiscount(@RequestBody com.example.IS216_Dlegent.payload.SSR.Discount discount){
        discountService.updateDiscount(discount);
        return ResponseEntity.ok(discount);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id){
        discountService.deleteDiscount(id);
        return ResponseEntity.ok(id);
    }

}