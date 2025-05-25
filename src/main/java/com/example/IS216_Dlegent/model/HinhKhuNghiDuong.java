package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "HINH_KHU_NGHI_DUONG")
public class HinhKhuNghiDuong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KHU_NGHI_DUONG", nullable = false)
    private KhuNghiDuong idKhuNghiDuong;

    @Nationalized
    @Column(name = "URL", nullable = false)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public KhuNghiDuong getIdKhuNghiDuong() {
        return idKhuNghiDuong;
    }

    public void setIdKhuNghiDuong(KhuNghiDuong idKhuNghiDuong) {
        this.idKhuNghiDuong = idKhuNghiDuong;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
