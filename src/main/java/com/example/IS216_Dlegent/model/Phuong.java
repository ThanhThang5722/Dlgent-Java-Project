package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "PHUONG")
public class Phuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TEN_PHUONG", nullable = false)
    private String tenPhuong;

    @ManyToOne
    @JoinColumn(name = "ID_QUAN", nullable = false)
    private Quan quan;

    @OneToMany(mappedBy = "phuong", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KhuNghiDuong> khuNghiDuongs = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenPhuong() {
        return tenPhuong;
    }

    public void setTenPhuong(String tenPhuong) {
        this.tenPhuong = tenPhuong;
    }

    public Quan getQuan() {
        return quan;
    }

    public void setQuan(Quan quan) {
        this.quan = quan;
    }

    public List<KhuNghiDuong> getKhuNghiDuongs() {
        return khuNghiDuongs;
    }

    public void setKhuNghiDuongs(List<KhuNghiDuong> khuNghiDuongs) {
        this.khuNghiDuongs = khuNghiDuongs;
    }
}