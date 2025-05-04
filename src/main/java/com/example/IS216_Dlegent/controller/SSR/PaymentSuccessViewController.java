package com.example.IS216_Dlegent.controller.SSR;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment-success")
public class PaymentSuccessViewController {
    @PostMapping
    public String getCustomerAccountManagementView() {
        return "PaymentSuccess";
    }
}
