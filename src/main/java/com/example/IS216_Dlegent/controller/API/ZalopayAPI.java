package com.example.IS216_Dlegent.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.IS216_Dlegent.service.ZalopayService;

import java.util.Map;

@RestController
@RequestMapping("/api/zalopay")
public class ZalopayAPI {

    private static final Logger logger = LoggerFactory.getLogger(ZalopayAPI.class);

    @Autowired
    private ZalopayService zalopayService;

    @PostMapping("/{idDatPhong}")
    public ResponseEntity<String> createPayment(@RequestBody Map<String, Object> orderRequest,
            @PathVariable Long idDatPhong) {
        try {
            String response = zalopayService.createOrder(orderRequest, idDatPhong);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating payment: " + e.getMessage());
        }
    }

    @GetMapping("/order-status/{appTransId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String appTransId) {
        String response = zalopayService.getOrderStatus(appTransId);
        return ResponseEntity.ok(response);
    }

}
