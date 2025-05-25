package com.example.IS216_Dlegent.controller.SSR;

import com.example.IS216_Dlegent.payload.dto.Top6ResortDTO;
import com.example.IS216_Dlegent.service.KhuNghiDuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HelloController {
    @Autowired
    private KhuNghiDuongService khuNghiDuongService;

    @GetMapping("")
    public String home(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        List<Top6ResortDTO> resorts = khuNghiDuongService.getTop6ResortsByRating();
        model.addAttribute("top6Resorts", resorts);

        return "CustomerView/Homepage";
    }
}
