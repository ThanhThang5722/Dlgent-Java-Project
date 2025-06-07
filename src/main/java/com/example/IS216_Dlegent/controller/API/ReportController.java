package com.example.IS216_Dlegent.controller.API;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.payload.dto.HoaDonDTO;
import com.example.IS216_Dlegent.repository.HoaDonRepository;
import com.example.IS216_Dlegent.service.DanhGiaService;
import com.example.IS216_Dlegent.service.DoiTacService;
import com.example.IS216_Dlegent.service.ExcelExportService;
import com.example.IS216_Dlegent.service.HoaDonService;

@RestController
@RequestMapping("/api/resort/report")
public class ReportController {
    
    @Autowired
    private DanhGiaService danhGiaService;
    
    @Autowired
    private HoaDonRepository hoaDonRepository;
    
    @Autowired
    private DoiTacService doiTacService;
    
    @Autowired
    private HoaDonService hoaDonService;
    
    @Autowired
    private ExcelExportService excelExportService;
    
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getReportSummary(@RequestParam Long doiTacId) {
        Map<String, Object> response = new HashMap<>();
        
        // Lượt đặt mới trong ngày
        int hoaDonTrongNgay = hoaDonRepository.demHoaDonTrongNgay(doiTacId);
        response.put("todaysBookings", hoaDonTrongNgay);
        
        // Doanh thu tháng này
        BigDecimal tongDonThang = Optional.ofNullable(hoaDonRepository.tongHoaDonThangHienTai(doiTacId))
                .orElse(BigDecimal.ZERO);
        response.put("monthRevenue", tongDonThang);
        
        // Tổng số lượt đặt
        int tongSoLuotDat = hoaDonRepository.tongSoLuotDat(doiTacId);
        response.put("totalBookings", tongSoLuotDat);
        
        // Số dư hiện tại
        BigDecimal soDu = Optional.ofNullable(doiTacService.getSoDu(doiTacId)).orElse(BigDecimal.ZERO);
        response.put("currentBalance", soDu);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/monthly-revenue")
    public ResponseEntity<Map<String, Object>> getMonthlyRevenue() {
        Map<String, Object> response = new HashMap<>();
        
        BigDecimal[] doanhThu = hoaDonService.layDoanhThu12ThangHienTai();
        String[] monthLabels = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        
        response.put("labels", monthLabels);
        response.put("data", doanhThu);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/balance-changes")
    public ResponseEntity<Map<String, Object>> bienDongSoDu(@RequestParam Long doiTacId) {
        Map<String, Object> response = new HashMap<>();
        
        List<Object[]> bienDong = hoaDonService.getBalanceChanges(doiTacId);
        
        List<String> labels = new ArrayList<>();
        List<BigDecimal> data = new ArrayList<>();
        
        for (Object[] row : bienDong) {
            Timestamp timestamp = (Timestamp) row[0];
            LocalDate ngay = timestamp.toLocalDateTime().toLocalDate();
            BigDecimal soDu = (BigDecimal) row[1];
            
            labels.add(ngay.format(DateTimeFormatter.ofPattern("dd/MM")));
            data.add(soDu);
        }
        
        response.put("labels", labels);
        response.put("data", data);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/reviews")
    public ResponseEntity<List<DanhGiaDTO>> getReviews(@RequestParam Long doiTacId) {
        List<DanhGiaDTO> topDanhGia = danhGiaService.getTop10DanhGiaFormattedByDoiTac(doiTacId);
        return ResponseEntity.ok(topDanhGia);
    }
    
    @GetMapping("/invoices")
    public ResponseEntity<List<HoaDonDTO>> getInvoicesByPartner(@RequestParam Long doiTacId) {
        List<HoaDonDTO> invoices = hoaDonService.getHoaDonByDoiTac(doiTacId);
        return ResponseEntity.ok(invoices);
    }
    
    @GetMapping("/invoices/export")
    public ResponseEntity<InputStreamResource> exportInvoicesToExcel(@RequestParam Long doiTacId) {
        try {
            // Lấy danh sách hóa đơn
            List<HoaDonDTO> invoices = hoaDonService.getHoaDonByDoiTac(doiTacId);
            
            if (invoices == null || invoices.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            // Tạo file Excel
            ByteArrayInputStream excelFile = excelExportService.exportHoaDonToExcel(invoices);
            
            // Thiết lập response headers
            HttpHeaders headers = new HttpHeaders();
            String filename = "hoa_don_" + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + ".xlsx";
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new InputStreamResource(excelFile));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/popular-resorts")
    public ResponseEntity<Map<String, Object>> getPopularResorts() {
        Map<String, Object> response = new HashMap<>();
        
        List<Object[]> results = hoaDonRepository.getPopularResorts();
        
        List<String> labels = new ArrayList<>();
        List<Double> percentages = new ArrayList<>();
        
        long total = results.stream()
                .mapToLong(r -> ((Number) r[1]).longValue())
                .sum();
        
        for (Object[] row : results) {
            String resortName = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            
            labels.add(resortName);
            double percent = total == 0 ? 0 : (count * 100.0) / total;
            percentages.add(Math.round(percent * 10.0) / 10.0); // làm tròn 1 chữ số sau dấu phẩy
        }
        
        response.put("labels", labels);
        response.put("data", percentages);
        
        return ResponseEntity.ok(response);
    }
} 