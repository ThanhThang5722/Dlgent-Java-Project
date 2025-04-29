package com.example.IS216_Dlegent.controller.SSR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.TienIch;
import com.example.IS216_Dlegent.service.HinhPhongService;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import com.example.IS216_Dlegent.service.LoaiPhongService;
import com.example.IS216_Dlegent.service.TienIchService;

@Controller
public class PartnerViewController {
    @Autowired
    private KhuNghiDuongService khuNghiDuongService;
    
    @Autowired
    private LoaiPhongService loaiPhongService;

    @Autowired
    private HinhPhongService hinhPhongService;

    @Autowired
    private TienIchService tienIchService;

    @GetMapping("/partner/{doiTacId}")
    public String getPartnerView(@PathVariable Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);
        List<KhuNghiDuong> khuNghiDuongs = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        model.addAttribute("khuNghiDuongs", khuNghiDuongs);

        return "/PartnerView/PartnerDashBoard";
    }

    @GetMapping("/partner/room-type-list/{doiTacId}")
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
}
