package com.example.IS216_Dlegent.controller.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.payload.respsonse.ServicesOfResortResponse;
import com.example.IS216_Dlegent.service.ServicesOfResortService;

@RestController
@RequestMapping("/api")
public class ServicesAPI {
    @Autowired
    private ServicesOfResortService service;

    @GetMapping("/services-of-resort")
    public ServicesOfResortResponse getServiceOfResort(
            @RequestParam("resortId") Long resortId,
            @RequestParam("serviceId") Long serviceId) {
        return service.getByResortIdAndDichVuId(resortId, serviceId);
    }

}
