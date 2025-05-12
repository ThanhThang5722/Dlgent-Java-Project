package com.example.IS216_Dlegent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DOI_DIEM")
public class DoiDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_MAGIAM", referencedColumnName = "ID")
    private GiamGia giamGia;

    @Column(name = "GIA_TRI")
    private Integer giaTri;

    public DoiDiem(){}

    public DoiDiem(Long id, GiamGia giamGia, Integer giaTri) {
        this.id = id;
        this.giamGia = giamGia;
        this.giaTri = giaTri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiamGia getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(GiamGia giamGia) {
        this.giamGia = giamGia;
    }

    public Integer getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(Integer giaTri) {
        this.giaTri = giaTri;
    }

}
