package com.example.IS216_Dlegent.controller.SSR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;

@Controller
public class PartnerViewController {
    @Autowired
    private KhuNghiDuongService khuNghiDuongService;

    @GetMapping("/partner/{doiTacId}")
    public String getPartnerView(@PathVariable Long doiTacId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        model.addAttribute("doiTacId", doiTacId);
        List<KhuNghiDuong> khuNghiDuongs = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        model.addAttribute("khuNghiDuongs", khuNghiDuongs);


        return "/PartnerView/PartnerDashBoard";
    }
}
