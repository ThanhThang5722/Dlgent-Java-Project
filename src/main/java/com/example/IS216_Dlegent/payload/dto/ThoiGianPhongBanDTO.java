package com.example.IS216_Dlegent.payload.dto;

import java.time.LocalDateTime;

import com.example.IS216_Dlegent.model.ThoiGianPhongBan;

public class ThoiGianPhongBanDTO {
    private Long id;
    private Integer maSoPhong;
    private String tenLoaiPhong;
    private Long khachHangId;
    private String tenTaiKhoan;
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;

    public ThoiGianPhongBanDTO() {}

    public ThoiGianPhongBanDTO(Long id, Integer maSoPhong, String tenLoaiPhong, Long khachHangId, String tenTaiKhoan, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
        this.id = id;
        this.maSoPhong = maSoPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.khachHangId = khachHangId;
        this.tenTaiKhoan = tenTaiKhoan;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaSoPhong() {
        return maSoPhong;
    }

    public void setMaSoPhong(Integer maSoPhong) {
        this.maSoPhong = maSoPhong;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    public ThoiGianPhongBanDTO convertToDTO(ThoiGianPhongBan entity) {
        return new ThoiGianPhongBanDTO(
            entity.getId(),
            entity.getPhong().getMaSo(),
            entity.getPhong().getLoaiPhong().getTenLoaiPhong(),
            entity.getKhachHang().getId(),
            entity.getKhachHang().getTaiKhoan().getUsername(),
            entity.getNgayBatDau(),
            entity.getNgayKetThuc()
        );
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }
    
}