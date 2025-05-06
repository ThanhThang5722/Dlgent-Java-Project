package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LICH_SU_RUT_TIEN")
public class LichSuRutTien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_DOI_TAC", nullable = false)
    private Long doiTacId;

    @Column(name = "SOTIEN", nullable = false, precision = 12, scale = 2)
    private BigDecimal soTien;

    @Column(name = "THOIGIAN_TAO", nullable = false)
    private LocalDateTime thoiGianTao;

    @Column(name = "THOIGIAN_RUT_TIEN", nullable = true)
    private LocalDateTime thoiGianRutTien;

    @Column(name = "TRANGTHAI_RUTTIEN", nullable = false)
    private String trangThaiRutTien;

    public LichSuRutTien() {}
    public LichSuRutTien(Long id, Long doiTacId, BigDecimal soTien, LocalDateTime thoiGianTao,
            LocalDateTime thoiGianRutTien, String trangThaiRutTien) {
        this.id = id;
        this.doiTacId = doiTacId;
        this.soTien = soTien;
        this.thoiGianTao = thoiGianTao;
        this.thoiGianRutTien = thoiGianRutTien;
        this.trangThaiRutTien = trangThaiRutTien;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getTrangThaiRutTien() {
        return trangThaiRutTien;
    }
    public void setTrangThaiRutTien(String trangThaiRutTien) {
        this.trangThaiRutTien = trangThaiRutTien;
    }
    public Long getDoiTacId() {
        return doiTacId;
    }
    public void setDoiTacId(Long doiTacId) {
        this.doiTacId = doiTacId;
    }
    
    
}