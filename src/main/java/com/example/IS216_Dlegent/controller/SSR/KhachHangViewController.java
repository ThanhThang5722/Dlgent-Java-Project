package com.example.IS216_Dlegent.controller.SSR;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.payload.dto.RoomTypeDTO;
import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import com.example.IS216_Dlegent.service.DichVuMacDinhService;
import com.example.IS216_Dlegent.service.GoiDatPhongService;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import com.example.IS216_Dlegent.service.LoaiPhongService;

@Controller
public class KhachHangViewController {

    @Autowired
    private KhuNghiDuongService khuNghiDuongService;

    @Autowired
    private LoaiPhongService loaiPhongService;

    @Autowired
    private GoiDatPhongService goiDatPhongService;

    @GetMapping("/tim-kiem-resort")
    public String timKiemResortPage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        return "CustomerView/SearchResort";
    }

    @GetMapping("/tim-kiem-resort/ket-qua")
    public String ketQuaTimKiemResort(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(defaultValue = "2") int soNguoi,
            Model model) {

        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        if (checkIn == null) {
            checkIn = LocalDate.now();
        }

        if (checkOut == null) {
            checkOut = checkIn.plusDays(2);
        }

        // Chuyển đổi LocalDate sang LocalDateTime (thời gian 00:00:00)
        LocalDateTime checkInDateTime = checkIn.atStartOfDay();
        LocalDateTime checkOutDateTime = checkOut.atStartOfDay();

        List<ResortSearchResponse> ketQuaTimKiem = khuNghiDuongService.searchResorts(searchTerm, checkInDateTime,
                checkOutDateTime, soNguoi);

        model.addAttribute("ketQuaTimKiem", ketQuaTimKiem);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("soNguoi", soNguoi);

        return "CustomerView/SearchResult";
    }

    @GetMapping("/resort-detail/{id}")
    public String resortDetail(@PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(defaultValue = "2") int soNguoi,
            Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        // Set default dates if not provided
        if (checkIn == null) {
            checkIn = LocalDate.now();
        }

        if (checkOut == null) {
            checkOut = checkIn.plusDays(2);
        }

        LocalDateTime checkInDateTime = checkIn.atStartOfDay();
        LocalDateTime checkOutDateTime = checkOut.atStartOfDay();

        // Lấy thông tin khu nghỉ dưỡng
        KhuNghiDuong khuNghiDuong = khuNghiDuongService.findById(id);
        if (khuNghiDuong == null) {
            // Xử lý khi không tìm thấy khu nghỉ dưỡng
            return "redirect:/tim-kiem-resort";
        }

        List<RoomTypeDTO> result = loaiPhongService.getRoomByResort(id, checkInDateTime, checkOutDateTime,
                soNguoi);

        model.addAttribute("resort", khuNghiDuong);
        model.addAttribute("result", result);
        model.addAttribute("resortId", id);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("soNguoi", soNguoi);

        return "CustomerView/ResortDetail";
    }
}