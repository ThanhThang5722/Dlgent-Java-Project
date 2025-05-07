package com.example.IS216_Dlegent.controller.SSR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.IS216_Dlegent.model.LichSuRutTien;
import com.example.IS216_Dlegent.repository.LichSuRutTienRepository;
import com.example.IS216_Dlegent.service.LichSuRutTienService;
import com.example.IS216_Dlegent.service.KhachHangService;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @Autowired
    private LichSuRutTienService lichSuRutTienService;

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("")
    public String getAdminDashboard() {
        return "/AdminView/AdminDashboard";
    }

    @GetMapping("/customerAccount")
    public String getCustomerAccountManagementView(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/AdminView/CustomerAccount/CustomerAccount";
    }

    @GetMapping("/partnerAccount")
    public String getPartnerAccountManagementView(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/AdminView/PartnerAccount/PartnerAccount";
    }

    @GetMapping("/partnerApproval")
    public String getPartnerApprovalView(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/AdminView/PartnerAccount/PartnerApproval";
    }

    @GetMapping("/discount")
    public String getDiscountMangamentView(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/withdraw")
    public String WithdrawManagementView(@RequestParam Long adminId, Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        List<LichSuRutTienAdminDTO> danhSachRutTien = lichSuRutTienService.getAllRutTienWithDoiTacInfo();
        model.addAttribute("rutTienList", danhSachRutTien);
        return "/AdminView/Withdraw/Withdraw";
    }

}
