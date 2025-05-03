package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.KhachHang;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long>{

}
