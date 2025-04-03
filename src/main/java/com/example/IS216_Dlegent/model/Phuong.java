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
}