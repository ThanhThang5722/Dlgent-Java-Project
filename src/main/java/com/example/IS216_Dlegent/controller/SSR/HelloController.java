package com.example.IS216_Dlegent.controller.SSR;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
    @GetMapping("")
    public String home() {
        return "index";
    }
    
}
