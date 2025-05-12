package com.example.IS216_Dlegent.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.KhachHang;
import com.example.IS216_Dlegent.model.User;
import com.example.IS216_Dlegent.payload.dto.ThongTinCaNhanKhachHangDTO;
import com.example.IS216_Dlegent.repository.AccountRepo;
import com.example.IS216_Dlegent.repository.KhachHangRepository;
import com.example.IS216_Dlegent.repository.UserRepo;

@Service
public class ThongTinTaiKhoanService {
    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    UserRepo userRepo;

    public ThongTinCaNhanKhachHangDTO getThongTinCaNhanKhachHang(Long id){
        Optional<KhachHang> khachHangOtp = khachHangRepository.findById(id); //tra ve optional (co/khong) khachhang

        if (!khachHangOtp.isPresent()){
            return null;
        }

        KhachHang khachHang = khachHangOtp.get(); //da lay duoc thong tin kh

        // khachHang.getTaiKhoan().getUsername();

        Optional<User> nguoiDungOtp = userRepo.findByUserId(khachHang.getTaiKhoan().getUserId());

        if(!nguoiDungOtp.isPresent()){
            return null;
        }

        User nguoiDung = nguoiDungOtp.get();


        ThongTinCaNhanKhachHangDTO thongTinCaNhanKhachHangDTO = new ThongTinCaNhanKhachHangDTO(
            khachHang.getTaiKhoan().getUsername(),
            nguoiDung.getFullName(),
            nguoiDung.getPhoneNumber(),
            nguoiDung.getCccd(),
            khachHang.getDiaChi(),
            khachHang.getDiemTichLuy(),
            nguoiDung.getEmail()
        );

        return thongTinCaNhanKhachHangDTO;
    }

    public void setThongTinCaNhanKhachHang(Long id, ThongTinCaNhanKhachHangDTO dto){
        Optional<KhachHang> khachHangOtp = khachHangRepository.findById(id);

        KhachHang khachHang = khachHangOtp.get();

        Optional<User> nguoiDungOtp = userRepo.findByUserId(khachHang.getTaiKhoan().getUserId());

        User nguoiDung = nguoiDungOtp.get();

        if (dto.getDiaChi() != null) {
            khachHang.setDiaChi(dto.getDiaChi());
        }

        if (dto.getTenTaiKhoan() != null) {
            khachHang.getTaiKhoan().setUsername(dto.getTenTaiKhoan());
        }

        if (dto.getHoTen() != null) {
            nguoiDung.setFullName(dto.getHoTen());
        }

        if (dto.getEmail() != null) {
            nguoiDung.setEmail(dto.getEmail());
        }

        if (dto.getSoDienThoai() != null) {
            nguoiDung.setPhoneNumber(dto.getSoDienThoai());
        }

        if (dto.getCCCD() != null) {
            nguoiDung.setCccd(dto.getCCCD());
        }

        khachHangRepository.save(khachHang);
        userRepo.save(nguoiDung);
    }
}

