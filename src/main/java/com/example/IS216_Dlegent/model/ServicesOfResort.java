package com.example.IS216_Dlegent.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DICH_VU_KHU_NGHI_DUONG")
public class ServicesOfResort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_DICH_VU", nullable = false)
    private Services dichVu;

    @ManyToOne
    @JoinColumn(name = "ID_RESORT", nullable = false)
    private KhuNghiDuong khuNghiDuong;

    @Column(name = "GIA", nullable = false)
    private Double gia;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "dichVuKhuNghiDuong")
    private List<DichVuMacDinh> dichVuMacDinhs;

    public ServicesOfResort(Long id, Services dichVu, KhuNghiDuong khuNghiDuong, Double gia, Boolean isDeleted) {
        this.id = id;
        this.dichVu = dichVu;
        this.khuNghiDuong = khuNghiDuong;
        this.gia = gia;
        this.isDeleted = isDeleted;
    }

    public ServicesOfResort(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Services getDichVu() {
        return dichVu;
    }

    public void setDichVu(Services dichVu) {
        this.dichVu = dichVu;
    }

    public KhuNghiDuong getKhuNghiDuong() {
        return khuNghiDuong;
    }

    public void setKhuNghiDuong(KhuNghiDuong khuNghiDuong) {
        this.khuNghiDuong = khuNghiDuong;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
