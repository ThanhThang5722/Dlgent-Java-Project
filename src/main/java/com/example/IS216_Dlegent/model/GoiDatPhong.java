package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "GOI_DAT_PHONG")
public class GoiDatPhong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_LOAI_PHONG", nullable = false)
    private LoaiPhong loaiPhong;

    @Column(name = "TONG_GIATIEN", nullable = false)
    private BigDecimal tongGiaTien;

    @OneToMany(mappedBy = "goiDatPhong")
    private List<DichVuMacDinh> dichVuMacDinhs;

    

    public GoiDatPhong(Long id, LoaiPhong loaiPhong, BigDecimal tongGiaTien, List<DichVuMacDinh> dichVuMacDinhs) {
        this.id = id;
        this.loaiPhong = loaiPhong;
        this.tongGiaTien = tongGiaTien;
        this.dichVuMacDinhs = dichVuMacDinhs;
    }

    public GoiDatPhong(LoaiPhong loaiPhong, BigDecimal tongGiaTien, List<DichVuMacDinh> dichVuMacDinhs) {
        this.loaiPhong = loaiPhong;
        this.tongGiaTien = tongGiaTien;
        this.dichVuMacDinhs = dichVuMacDinhs;
    }
    public GoiDatPhong() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public BigDecimal getTongGiaTien() {
        return tongGiaTien;
    }

    public void setTongGiaTien(BigDecimal tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
    }

    public List<DichVuMacDinh> getDichVuMacDinhs() {
        return dichVuMacDinhs;
    }

    public void setDichVuMacDinhs(List<DichVuMacDinh> dichVuMacDinhs) {
        this.dichVuMacDinhs = dichVuMacDinhs;
    }

    // Getters, setters, constructors
    
}

