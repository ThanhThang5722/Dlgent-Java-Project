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
import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import com.example.IS216_Dlegent.repository.DanhGiaRepository;
import com.example.IS216_Dlegent.service.BookingListService;
import com.example.IS216_Dlegent.service.ChiTietDatPhongService;
import com.example.IS216_Dlegent.service.DanhGiaService;
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

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private ChiTietDatPhongService chiTietDatPhongService;

    @Autowired
    BookingListService bookingListService;

    @GetMapping("/user/profile")
    public String profilePage(Model model) {
        return "CustomerView/Profile";
    }

    @GetMapping("/user/booking-history/{id}")
    public String myBookingPage(@PathVariable Long id, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("bookingHistory", bookingListService.getBookingHistory(id));

        return "CustomerView/BookingHistory";
    }

    @GetMapping("/user/purchase")
    public String purchasePage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        return "CustomerView/Purchase";
    }

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

        List<RoomTypeDetailsDTO> roomtypes = loaiPhongService.getRoomByResort(id, checkInDateTime, checkOutDateTime,
                soNguoi);

        List<DanhGiaDTO> danhGias = danhGiaService.getDanhGiaDTOs(id);

        model.addAttribute("resort", khuNghiDuong);
        model.addAttribute("result", roomtypes);
        model.addAttribute("resortId", id);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("soNguoi", soNguoi);
        model.addAttribute("danhGias", danhGias);

        return "CustomerView/ResortDetail";
    }

    @GetMapping("/gio-hang")
    public String gioHangPage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        List<ChiTietDatPhongDTO> cartItems = chiTietDatPhongService.getChiTietDatPhongByDatPhongId();

        // tổng tiền
        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getTongGiaTien())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "CustomerView/GioHang";
    }
}