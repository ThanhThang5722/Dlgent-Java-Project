package com.example.IS216_Dlegent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long>{
    public Optional<KhachHang> findByTaiKhoan(Account taikhoan);
}
