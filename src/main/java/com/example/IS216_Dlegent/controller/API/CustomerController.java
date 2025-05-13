package com.example.IS216_Dlegent.controller.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IS216_Dlegent.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PutMapping("/{username}/activate")
    public String activateCustomer(@PathVariable String username) {
        boolean success = customerService.activateCustomer(username);
        return success ? "Customer activated successfully" : "Customer not found";
    }
}
