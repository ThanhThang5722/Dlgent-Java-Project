package com.example.IS216_Dlegent.controller.SSR;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LichSuRutTienAdminDTO {
    private Long id;
    private Long doiTacId;
    private String tenDoiTac;
    private BigDecimal soTien;
    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianRutTien;
    private String trangThai;
    private BigDecimal soDuHienTai;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDoiTacId() {
        return doiTacId;
    }
    public void setDoiTacId(Long doiTacId) {
        this.doiTacId = doiTacId;
    }
    public String getTenDoiTac() {
        return tenDoiTac;
    }
    public void setTenDoiTac(String tenDoiTac) {
        this.tenDoiTac = tenDoiTac;
    }
    public BigDecimal getSoTien() {
        return soTien;
    }
    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }
    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }
    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }
    public LocalDateTime getThoiGianRutTien() {
        return thoiGianRutTien;
    }
    public void setThoiGianRutTien(LocalDateTime thoiGianRutTien) {
        this.thoiGianRutTien = thoiGianRutTien;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public BigDecimal getSoDuHienTai() {
        return soDuHienTai;
    }
    public void setSoDuHienTai(BigDecimal soDuHienTai) {
        this.soDuHienTai = soDuHienTai;
    }
    public LichSuRutTienAdminDTO(Long id, Long doiTacId, String tenDoiTac, BigDecimal soTien, LocalDateTime thoiGianTao,
            LocalDateTime thoiGianRutTien, String trangThai, BigDecimal soDuHienTai) {
        this.id = id;
        this.doiTacId = doiTacId;
        this.tenDoiTac = tenDoiTac;
        this.soTien = soTien;
        this.thoiGianTao = thoiGianTao;
        this.thoiGianRutTien = thoiGianRutTien;
        this.trangThai = trangThai;
        this.soDuHienTai = soDuHienTai;
    }
    public LichSuRutTienAdminDTO() {}

    
}