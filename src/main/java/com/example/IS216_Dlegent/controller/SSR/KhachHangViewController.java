package com.example.IS216_Dlegent.controller.SSR;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;
import com.example.IS216_Dlegent.payload.dto.BookedRoomDTO;
import com.example.IS216_Dlegent.payload.dto.BookingListDTO;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.payload.dto.ThongTinCaNhanKhachHangDTO;
import com.example.IS216_Dlegent.payload.respsonse.ResortSearchResponse;
import com.example.IS216_Dlegent.repository.DanhGiaRepository;
import com.example.IS216_Dlegent.service.BookingListService;
import com.example.IS216_Dlegent.service.ChiTietDatPhongService;
import com.example.IS216_Dlegent.service.DanhGiaService;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.KhoMaGiamGia;
import com.example.IS216_Dlegent.payload.dto.KhachHangDTO;
import com.example.IS216_Dlegent.payload.dto.MaGiamGiaDTO;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.service.DichVuMacDinhService;
import com.example.IS216_Dlegent.service.DiemService;
import com.example.IS216_Dlegent.service.GoiDatPhongService;
import com.example.IS216_Dlegent.service.KhoMaGiamGiaService;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import com.example.IS216_Dlegent.service.LoaiPhongService;
import com.example.IS216_Dlegent.service.ThongTinTaiKhoanService;
import com.example.IS216_Dlegent.utils.CookieUtils;

import jakarta.servlet.http.HttpServletRequest;

import com.example.IS216_Dlegent.service.MaGiamGiaService;
import com.example.IS216_Dlegent.service.AccountService;

@Controller
public class KhachHangViewController {

    @Autowired
    private HttpServletRequest request;

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
    private BookingListService bookingListService;

    @Autowired
    private DiemService diemService;

    @Autowired
    private MaGiamGiaService maGiamGiaService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private KhoMaGiamGiaService khoMaGiamGiaService;

    @Autowired
    private AccountService accountService;



    @GetMapping("/user/point-redemption")
    public String pointRedemptionPage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        Long userId = CookieUtils.getUserIdFromCookie(request);

        // Lấy điểm tích lũy của khách hàng
        Integer diemTichLuy = diemService.getDiemByUserId(userId);
        model.addAttribute("diemTichLuy", diemTichLuy);

        // Lấy danh sách mã giảm giá có thể quy đổi
        List<MaGiamGiaDTO> danhSachMaGiam = maGiamGiaService.getDanhSach();
        model.addAttribute("danhSachMaGiam", danhSachMaGiam);

        return "CustomerView/PointRedemption";
    }

    @GetMapping("/user/discount-codes")
    public String discountCodesPage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        Long userId = CookieUtils.getUserIdFromCookie(request);

        // Lấy danh sách mã giảm giá của khách hàng
        List<MaGiamGiaDTO> maGiamGias = khoMaGiamGiaService.getMaGiamGiaByKhachHangId(userId);
        model.addAttribute("maGiamGias", maGiamGias);

        return "CustomerView/DiscountCodes";
    }

    @GetMapping("/user/booking-history")
    public String myBookingPage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        Long userId = CookieUtils.getUserIdFromCookie(request);

        BookingListDTO bookingList = bookingListService.getBookingHistory(userId);
        System.out.println("BookingList: " + bookingList);

        List<BookedRoomDTO> upcomingRoom = bookingList.getUpcomingRoom();
        List<BookedRoomDTO> completedRoom = bookingList.getCompletedRoom();
        List<BookedRoomDTO> cancelledRoom = bookingList.getCancelledRoom();

        model.addAttribute("cancelledBookings", cancelledRoom);
        model.addAttribute("upcomingBookings", upcomingRoom);
        model.addAttribute("completedBookings", completedRoom);

        return "CustomerView/BookingHistory";
    }

    @GetMapping("/user/booking-history/booking-detail")
    public String bookingDetailPage(@RequestParam Long id, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        // Gọi API để lấy thông tin chi tiết đặt phòng
        var response = bookingListService.getBookingDetail(id);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            model.addAttribute("booking", response.getBody());
        }

        return "CustomerView/BookingDetail";
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

        KhuNghiDuong khuNghiDuong = khuNghiDuongService.findById(id);
        if (khuNghiDuong == null) {
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

        Long khachHangId = CookieUtils.getUserIdFromCookie(request);

        List<ChiTietDatPhongDTO> cartItems = chiTietDatPhongService.getChiTietDatPhongByDatPhongId(khachHangId);

        // tổng tiền
        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getTongGiaTien())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "CustomerView/GioHang";
    }

    @GetMapping("/user/change-password")
    public String changePasswordPage(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        Long userId = CookieUtils.getUserIdFromCookie(request);

        Optional<KhachHang> khachHangOpt = khachHangRepository.findById(userId);
        if (khachHangOpt.isPresent()) {
            KhachHang khachHang = khachHangOpt.get();
            model.addAttribute("userId", khachHang.getTaiKhoan().getAccountId());
        }

        return "CustomerView/ChangePassword";
    }

    @Autowired
    ThongTinTaiKhoanService thongTinTaiKhoanService;

    @GetMapping("/user/profile")
    public String userProfile(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        Long khachHangId = CookieUtils.getUserIdFromCookie(request);

        ThongTinCaNhanKhachHangDTO thongTinCaNhanKhachHangDTO = thongTinTaiKhoanService.getThongTinCaNhanKhachHang(khachHangId);

        model.addAttribute("thongTinCaNhanDto", thongTinCaNhanKhachHangDTO);

        return "Profile/CustomerProfile";
    }

    @PutMapping("/user/profile/{id}")
    public ResponseEntity<?> editProfile(@RequestBody ThongTinCaNhanKhachHangDTO thongTinCaNhanKhachHangDTO,
            @PathVariable Long id) {

        thongTinTaiKhoanService.setThongTinCaNhanKhachHang(id, thongTinCaNhanKhachHangDTO);
        return ResponseEntity.ok().build();
    }

    

}