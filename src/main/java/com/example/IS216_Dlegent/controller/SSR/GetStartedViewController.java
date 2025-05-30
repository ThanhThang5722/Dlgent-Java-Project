package com.example.IS216_Dlegent.controller.SSR;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("")
public class GetStartedViewController {

    @GetMapping("/getstarted")
    public String getGetStartedView(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/GetStarted";
    }

    @GetMapping("/customer-signin")
    public String getCustomerSiginView(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/signin";
    }

    @GetMapping("/customer-signup")
    public String getCustomerSignup(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/signup";
    }

    @GetMapping("/partner-signin")
    public String getPartnerSignin(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);

        return "/PartnerSignin";
    }

    @GetMapping("/partner-signup")
    public String getPartnerSignup(Model model) {
        String bootstrapUrl = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css";
        model.addAttribute("bootstrapUrl", bootstrapUrl);
        return "/PartnerSignup";
    }

    @GetMapping("/verify-email-success")
    public String getMethodName(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "/EmailVerifiedSucces";
    }

}
