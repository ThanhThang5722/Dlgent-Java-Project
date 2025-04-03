package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "THANH_PHO")
public class ThanhPho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TEN_THANH_PHO", nullable = false)
    private String tenThanhPho;

    @OneToMany(mappedBy = "thanhPho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Quan> quans = new ArrayList<>();
}
