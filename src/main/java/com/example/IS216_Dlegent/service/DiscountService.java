package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.Discount;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.repository.DiscountRepository;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;

@Service
public class DiscountService 
{
    @Autowired
    private LoaiPhongRepo loaiPhongRepository;
    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> getAll()
    {
        return discountRepository.findAll();
    }
    
    public Discount saveDiscount(Long idLoaiPhong, String loaiGiamGia, BigDecimal giaTri, BigDecimal mucToiDa, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, String tinhTrang)
    {
        LoaiPhong loaiPhong = loaiPhongRepository.findById(idLoaiPhong)
        .orElseThrow(() -> new IllegalArgumentException("Loại phòng không tồn tại"));

        Discount discount = new Discount();
        
        discount.setIdLoaiPhong(idLoaiPhong);
        discount.setLoaiGiamGia(loaiGiamGia);
        discount.setGiaTri(giaTri);
        discount.setMucToiDa(mucToiDa);
        discount.setNgayBatDau(ngayBatDau);
        discount.setNgayKetThuc(ngayKetThuc);
        discount.setTrangThai(tinhTrang);

        return discountRepository.save(discount);
    }
    public void updateDiscount(com.example.IS216_Dlegent.payload.SSR.Discount discount){
        Discount disc = discountRepository.findById(discount.getId())
        .orElseThrow(() -> new IllegalArgumentException("Mã giảm giá không tồn tại"));

        String dateTimeStrBD = discount.getNgayBatDau();
        DateTimeFormatter formatterBD = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTimeBD = LocalDateTime.parse(dateTimeStrBD, formatterBD);

        
        String dateTimeStrKT = discount.getNgayKetThuc();
        DateTimeFormatter formatterKT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTimeKT = LocalDateTime.parse(dateTimeStrKT, formatterKT);
        
        
        disc.setIdLoaiPhong(discount.getIdLoaiPhong());
        disc.setLoaiGiamGia(discount.getLoaiGiamGia());
        disc.setGiaTri(discount.getGiaTri());
        disc.setMucToiDa(discount.getMucToiDa());
        disc.setNgayBatDau(localDateTimeBD);
        disc.setNgayKetThuc(localDateTimeKT);
        disc.setTrangThai(discount.getTrangThai());

        discountRepository.save(disc);
    }
    public void deleteDiscount(Long id)
    {
        discountRepository.deleteById(id);
    }
}
