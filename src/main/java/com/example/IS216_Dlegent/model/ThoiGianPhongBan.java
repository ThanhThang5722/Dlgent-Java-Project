package com.example.IS216_Dlegent.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "THOIGIAN_PHONG_BAN")
public class ThoiGianPhongBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PHONG", nullable = false)
    private Phong phong;

    @Column(name = "NGAY_BAT_DAU", nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(name = "NGAY_KET_THUC", nullable = false)
    private LocalDateTime ngayKetThuc;

    @ManyToOne
    @JoinColumn(name = "ID_KHACH_HANG", nullable = false)
    private KhachHang khachHang;

    public ThoiGianPhongBan() {
    }

    public ThoiGianPhongBan(Long id, Phong phong, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc,
            KhachHang khachHang) {
        this.id = id;
        this.phong = phong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.khachHang = khachHang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }
    
}

