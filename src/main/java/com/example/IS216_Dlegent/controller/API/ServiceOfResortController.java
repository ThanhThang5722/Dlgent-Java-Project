package com.example.IS216_Dlegent.controller.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.Services;
import com.example.IS216_Dlegent.model.ServicesOfResort;
import com.example.IS216_Dlegent.payload.respsonse.ServiceWithStatusDTO;
import com.example.IS216_Dlegent.repository.KhuNghiDuongRepo;
import com.example.IS216_Dlegent.repository.ServicesOfResortRepository;
import com.example.IS216_Dlegent.repository.ServicesRepository;

@RestController
@RequestMapping("/api/services-of-resort")
public class ServiceOfResortController {

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ServicesOfResortRepository servicesOfResortRepository;

    @Autowired
    private KhuNghiDuongRepo khuNghiDuongRepository;

    // 1. Lấy danh sách dịch vụ (có và chưa có)
    @GetMapping("/{resortId}")
    public ResponseEntity<List<ServiceWithStatusDTO>> getServicesOfResort(@PathVariable Long resortId) {
        List<Services> allServices = servicesRepository.findAll();
        List<ServicesOfResort> currentServices = servicesOfResortRepository.findByKhuNghiDuong_IdAndIsDeletedFalse(resortId);

        Map<Long, ServicesOfResort> existingMap = currentServices.stream()
                .collect(Collectors.toMap(s -> s.getDichVu().getId(), s -> s));

        List<ServiceWithStatusDTO> result = new ArrayList<>();
        for (Services s : allServices) {
            ServicesOfResort linked = existingMap.get(s.getId());
            ServiceWithStatusDTO dto = new ServiceWithStatusDTO();
            dto.setServiceId(s.getId());
            dto.setServiceName(s.getServiceName());
            if (linked != null) {
                dto.setPrice(linked.getGia());
                dto.setDaCo(true);
            } else {
                dto.setPrice(0.0);
                dto.setDaCo(false);
            }
            result.add(dto);
        }

        return ResponseEntity.ok(result);
    }

    // 2. Cập nhật danh sách dịch vụ
    @PutMapping("/{resortId}")
    public ResponseEntity<Void> updateServicesOfResort(@PathVariable Long resortId, @RequestBody List<ServiceWithStatusDTO> dtos) {
        KhuNghiDuong resort = khuNghiDuongRepository.findById(resortId)
                .orElseThrow(() -> new RuntimeException("Resort not found"));

        for (ServiceWithStatusDTO dto : dtos) {
            Optional<ServicesOfResort> optional = servicesOfResortRepository
                .findByKhuNghiDuong_IdAndDichVu_Id(resortId, dto.getServiceId());

            if (dto.getDaCo()) {
                Services service = servicesRepository.findById(dto.getServiceId())
                        .orElseThrow(() -> new RuntimeException("Service not found"));
                if (optional.isPresent()) {
                    ServicesOfResort existing = optional.get();
                    existing.setGia(dto.getPrice());
                    existing.setIsDeleted(false);
                    servicesOfResortRepository.save(existing);
                } else {
                    ServicesOfResort newLink = new ServicesOfResort();
                    newLink.setDichVu(service);
                    newLink.setKhuNghiDuong(resort);
                    newLink.setGia(dto.getPrice());
                    newLink.setIsDeleted(false);
                    servicesOfResortRepository.save(newLink);
                }
            } else {
                // Nếu bỏ chọn → soft delete
                optional.ifPresent(existing -> {
                    existing.setIsDeleted(true);
                    servicesOfResortRepository.save(existing);
                });
            }
        }

        return ResponseEntity.ok().build();
    }
}