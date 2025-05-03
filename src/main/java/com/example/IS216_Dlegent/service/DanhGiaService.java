package com.example.IS216_Dlegent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.DanhGia;
import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.payload.dto.DanhGiaDTO;
import com.example.IS216_Dlegent.payload.request.InsertDanhGiaRequest;
import com.example.IS216_Dlegent.repository.DanhGiaRepository;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.repository.KhuNghiDuongRepo;
import com.example.IS216_Dlegent.repository.UserRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DanhGiaService {

    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Autowired
    private KhuNghiDuongRepo khuNghiDuongRepo;

    @Autowired
    private KhachHangRepository khachHangRepository;

    public List<DanhGiaDTO> getTop10DanhGiaFormattedByDoiTac(Long doiTacId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return danhGiaRepository
                .findTop10ByDoiTacIdOrderByThoiGianTaoDesc(doiTacId, PageRequest.of(0, 10))
                .stream()
                .map(dg -> {
                    DanhGiaDTO dto = new DanhGiaDTO();
                    dto.setTenKhachHang(dg.getKhachHang().getTaiKhoan().getUsername());
                    dto.setTenResort(dg.getKhuNghiDuong().getTen());
                    dto.setDiem(dg.getDiem());
                    dto.setNoiDung(dg.getNoiDung());
                    dto.setThoiGianTaoFormatted(dg.getThoiGianTao().format(formatter));
                    return dto;
                }).toList();
    }

    public List<DanhGiaDTO> getDanhGiaDTOs(Long resortId){
        List<DanhGia> danhGias = danhGiaRepository.findByKhuNghiDuong_Id(resortId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return danhGias.stream()
            .map(dg -> new DanhGiaDTO(
                dg.getKhachHang().getTaiKhoan().getUsername(),
                dg.getKhuNghiDuong().getTen(),
                dg.getDiem(),
                dg.getNoiDung(),
                dg.getThoiGianTao().format(formatter)))
            .collect(Collectors.toList());
    }

    public Page<DanhGiaDTO> getDanhGiaDTOsByResortId(Long resortId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<DanhGia> danhGiaPage = danhGiaRepository.findByKhuNghiDuong_Id(resortId, pageable);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return danhGiaPage.map(dg -> new DanhGiaDTO(
                dg.getKhachHang().getTaiKhoan().getUsername(),
                dg.getKhuNghiDuong().getTen(),
                dg.getDiem(),
                dg.getNoiDung(),
                dg.getThoiGianTao().format(formatter)));
    }

    public DanhGia insertDanhGia(InsertDanhGiaRequest danhGia) {
        KhuNghiDuong khuNghiDuong = khuNghiDuongRepo.findById(danhGia.getResortId())
                .orElseThrow(() -> new RuntimeException("Resort không tồn tại"));

        KhachHang khachHang = khachHangRepository.findById(danhGia.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

        DanhGia dg = new DanhGia();
        dg.setKhuNghiDuong(khuNghiDuong);
        dg.setKhachHang(khachHang);
        dg.setDiem(danhGia.getDiem());
        dg.setNoiDung(danhGia.getNoiDung());
        dg.setThoiGianTao(LocalDateTime.now());

        return danhGiaRepository.save(dg);
    }
}