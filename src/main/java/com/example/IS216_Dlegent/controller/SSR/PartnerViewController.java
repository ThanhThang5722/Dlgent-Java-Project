package com.example.IS216_Dlegent.controller.SSR;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.IS216_Dlegent.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LichSuRutTien;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.Services;
import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.model.TienIch;
import com.example.IS216_Dlegent.payload.SSR.BienDongSoDuDTO;
import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;
import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.HoaDonRepository;
import com.example.IS216_Dlegent.repository.LichSuRutTienRepository;
import com.example.IS216_Dlegent.service.DanhGiaService;
import com.example.IS216_Dlegent.service.DoiTacService;
import com.example.IS216_Dlegent.service.HinhPhongService;
import com.example.IS216_Dlegent.service.HoaDonService;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import com.example.IS216_Dlegent.service.LoaiPhongService;
import com.example.IS216_Dlegent.service.RoomTypeDetailServices;
import com.example.IS216_Dlegent.service.ServicesService;
import com.example.IS216_Dlegent.service.TienIchService;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/partner")
public class PartnerViewController {
    Logger logger = LoggerFactory.getLogger(PartnerViewController.class);

    @Autowired
    private KhuNghiDuongService khuNghiDuongService;
    
    @Autowired
    private LoaiPhongService loaiPhongService;

    @Autowired
    private HinhPhongService hinhPhongService;

    @Autowired
    private TienIchService tienIchService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private RoomTypeDetailServices roomTypeDetailServices;

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private DoiTacService doiTacService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private LichSuRutTienRepository lichSuRutTienRepository;

    @GetMapping("/{doiTacId}")
    public String getPartnerView(@PathVariable Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);
        List<KhuNghiDuong> khuNghiDuongs = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        model.addAttribute("khuNghiDuongs", khuNghiDuongs);
        List<Services> allServices = servicesService.findAll(); // hoặc servicesService.findAll()
        model.addAttribute("servicesList", allServices);

        return "/PartnerView/PartnerDashBoard";
    }

    @GetMapping("/room-type-list/{doiTacId}")
    public String getRoomTypeView(@PathVariable Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);

        // Lấy danh sách Resort
        List<KhuNghiDuong> listKhuNghiDuong = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        model.addAttribute("listKhuNghiDuong", listKhuNghiDuong);
        List<LoaiPhong> listLoaiPhong = loaiPhongService.getRoomTypesByPartnerId(doiTacId);
        model.addAttribute("loaiPhongs", listLoaiPhong);
        List<String> danhSachTieuChuan = new ArrayList<>(Arrays.asList(
                "Hạng sang",
                "Bình dân",
                "Thương gia"));
        model.addAttribute("danhSachTieuChuan", danhSachTieuChuan);
        List<String> danhSachSoLuong = new ArrayList<>(Arrays.asList(
                "Phòng đơn",
                "Phòng đôi",
                "Phòng gia đình",
                "Phòng tập thể",
                "Phòng cao cấp",
                "Phòng Suite"));
        model.addAttribute("danhSachSoLuong", danhSachSoLuong);
        Logger logger = LoggerFactory.getLogger(getClass());

        Map<Long, List<HinhPhong>> anhTheoLoaiPhong = new HashMap<>();
        for (LoaiPhong loaiPhong : listLoaiPhong) {
            Long roomTypeID = loaiPhong.getId();
            List<HinhPhong> anhCuaLoaiPhong = hinhPhongService.getHinhPhongByRoomTypeID(roomTypeID);
            anhTheoLoaiPhong.put(roomTypeID, anhCuaLoaiPhong);
        }
        model.addAttribute("anhTheoLoaiPhong", anhTheoLoaiPhong);

        List<TienIch> danhSachTienIch = tienIchService.getAllTienIch();
        model.addAttribute("danhSachTienIch", danhSachTienIch);
        Map<Long, List<TienIch>> tienIchTheoLoaiPhong = new HashMap<>();
        for (LoaiPhong loaiPhong : listLoaiPhong) {
            Long roomTypeID = loaiPhong.getId();
            List<TienIch> tienIchcuaLoaiPhong = new ArrayList<>(tienIchService.getTienIchByLoaiPhongId(roomTypeID));
            tienIchTheoLoaiPhong.put(roomTypeID, tienIchcuaLoaiPhong);
        }
        model.addAttribute("tienIchTheoLoaiPhong", tienIchTheoLoaiPhong);
        return "/PartnerView/RoomTypeManagement";
    }

    @GetMapping("/booking-offer")
    public String getBookingOfferVIew(@RequestParam Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);

        // Lấy danh sách khu nghỉ dưỡng để hiển thị trong dropdown
        List<KhuNghiDuong> listKhuNghiDuong = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        model.addAttribute("listKhuNghiDuong", listKhuNghiDuong);
        
        // Lấy danh sách tất cả loại phòng của đối tác
        List<LoaiPhong> listLoaiPhong = loaiPhongService.getRoomTypesByPartnerId(doiTacId);
        model.addAttribute("listLoaiPhong", listLoaiPhong);
        
        // Lấy danh sách dịch vụ
        //List<Services> danhSachDichVu = servicesService.getAll();
        //model.addAttribute("danhSachDichVu", danhSachDichVu);

        // Lấy thông tin chi tiết về loại phòng
        List<RoomTypeDetailsDTO> roomTypeDetails = roomTypeDetailServices.getRoomTypeDetailsByPartnerId(doiTacId);
        model.addAttribute("roomTypes", roomTypeDetails);
        
        return "/PartnerView/BookingOfferManagement/BookingOfferManagement";
    }
    @GetMapping("/room")
    public String getRoomView(@RequestParam Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);
        // Lấy danh sách khu nghỉ dưỡng để hiển thị trong dropdown
        List<KhuNghiDuong> listKhuNghiDuong = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        model.addAttribute("listKhuNghiDuong", listKhuNghiDuong);
        
        // Lấy danh sách tất cả loại phòng của đối tác
        List<LoaiPhong> listLoaiPhong = loaiPhongService.getRoomTypesByPartnerId(doiTacId);
        model.addAttribute("listLoaiPhong", listLoaiPhong);
        return "/PartnerView/RoomManagement/RoomManagement";
    }

    @GetMapping("/report")
    public String getReportDashboardView(@RequestParam Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        List<DanhGiaDTO> topDanhGia = danhGiaService.getTop10DanhGiaFormattedByDoiTac(doiTacId);
        model.addAttribute("topDanhGia", topDanhGia);
        model.addAttribute("doiTacId", doiTacId);
        int hoaDonTrongNgay = hoaDonRepository.demHoaDonTrongNgay(doiTacId);
        model.addAttribute("todaysBookings", hoaDonTrongNgay);
        BigDecimal tongDonThang = Optional.ofNullable(hoaDonRepository.tongHoaDonThangHienTai(doiTacId)).orElse(BigDecimal.ZERO);
        tongDonThang = tongDonThang != null ? tongDonThang : BigDecimal.ZERO;
        model.addAttribute("monthRevenue", tongDonThang.doubleValue());
        int tongSoLuotDat = hoaDonRepository.tongSoLuotDat(doiTacId);
        model.addAttribute("totalBookings", tongSoLuotDat);
        BigDecimal soDu = doiTacService.getSoDu(doiTacId);
        soDu = soDu != null ? soDu : BigDecimal.ZERO;
        model.addAttribute("currentBalance", soDu);

        BigDecimal[] doanhThu = hoaDonService.layDoanhThu12ThangHienTai();
        model.addAttribute("doanhThu", doanhThu);

        /*List<BienDongSoDuDTO> bienDongList = hoaDonService.layLichSuBienDongSoDu(doiTacId);

        List<String> labels = bienDongList.stream()
            .map(item -> item.getNgay().toString()) // hoặc format theo ý
            .collect(Collectors.toList());

        List<BigDecimal> balanceData = bienDongList.stream()
            .map(item -> item.getThayDoiSoDu())
            .collect(Collectors.toList());

        model.addAttribute("labels", labels);
        model.addAttribute("balanceData", balanceData);*/

        List<Object[]> bienDong = hoaDonService.getBalanceChanges(doiTacId);

        List<String> labels = new ArrayList<>();
        List<BigDecimal> balanceData = new ArrayList<>();

        for (Object[] row : bienDong) {
            Timestamp timestamp = (Timestamp) row[0];
            LocalDate ngay = timestamp.toLocalDateTime().toLocalDate();
            BigDecimal thayDoi = (BigDecimal) row[1];

            labels.add(ngay.format(DateTimeFormatter.ofPattern("dd/MM")));
            balanceData.add(thayDoi);
        }

        
        model.addAttribute("labels", labels);
        model.addAttribute("balanceData", balanceData);


        // Tỉ lệ Khu nghỉ dưỡng được đặt theo phần %
        List<Object[]> results = hoaDonRepository.getPopularResorts();

        List<String> labelsKhuNghiDuong = new ArrayList<>();
        List<Double> percentages = new ArrayList<>();

        long total = results.stream()
            .mapToLong(r -> ((Number) r[1]).longValue())
            .sum();

        for (Object[] row : results) {
            String resortName = (String) row[0];
            Long count = ((Number) row[1]).longValue(); 

            labelsKhuNghiDuong.add(resortName);
            double percent = total == 0 ? 0 : (count * 100.0) / total;
            percentages.add(Math.round(percent * 10.0) / 10.0); // làm tròn 1 chữ số sau dấu phẩy
        }

        model.addAttribute("resortLabels", labelsKhuNghiDuong);
        model.addAttribute("resortData", percentages);

        return "/PartnerView/Report/ReportDashboard";
    }
    
    @GetMapping("/withdraw")
    public String getMethodName(@RequestParam Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);
        List<LichSuRutTien> danhSachRutTien = lichSuRutTienRepository.findByDoiTacIdOrderByThoiGianTaoDesc(doiTacId);
        model.addAttribute("rutTienList", danhSachRutTien);
        return "/PartnerView/Withdraw/Withdraw";
    }
    

}
