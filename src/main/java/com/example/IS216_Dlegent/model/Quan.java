package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "QUAN")
public class Quan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TEN_QUAN", nullable = false)
    private String tenQuan;

    @ManyToOne
    @JoinColumn(name = "ID_THANH_PHO", nullable = false)
    private ThanhPho thanhPho;

    @OneToMany(mappedBy = "quan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phuong> phuongs = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenQuan() {
        return tenQuan;
    }

    public void setTenQuan(String tenQuan) {
        this.tenQuan = tenQuan;
    }

    public ThanhPho getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(ThanhPho thanhPho) {
        this.thanhPho = thanhPho;
    }

    public List<Phuong> getPhuongs() {
        return phuongs;
    }

    public void setPhuongs(List<Phuong> phuongs) {
        this.phuongs = phuongs;
    }
}
