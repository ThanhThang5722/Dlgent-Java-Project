package com.example.IS216_Dlegent.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "GIAMGIA")
public class GiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_LOAI_PHONG", referencedColumnName = "ID")
    private LoaiPhong loaiPhong;

    @Column(name = "LOAI_GIAMGIA", nullable = false, length = 255)
    private String loaiGiamGia;

    @Column(name = "GIA_TRI", nullable = false, precision = 12, scale = 2)
    private BigDecimal giaTri;

    @Column(name = "MUC_TOI_DA", precision = 12, scale = 2)
    private BigDecimal mucToiDa;

    @Column(name = "NGAY_BAT_DAU", nullable = false)
    private LocalDateTime ngayBatDau;

    @Column(name = "NGAY_KET_THUC", nullable = false)
    private LocalDateTime ngayKetThuc;

    @Column(name = "TINH_TRANG", nullable = false, length = 255)
    private String tinhTrang;

    @OneToOne(mappedBy = "giamGia", fetch = FetchType.EAGER)
    private DoiDiem doiDiem;

    public GiamGia() {
    }

    public GiamGia(Long id, LoaiPhong loaiPhong, String loaiGiamGia, BigDecimal giaTri, BigDecimal mucToiDa,
            LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, String tinhTrang, DoiDiem doiDiem) {
        this.id = id;
        this.loaiPhong = loaiPhong;
        this.loaiGiamGia = loaiGiamGia;
        this.giaTri = giaTri;
        this.mucToiDa = mucToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tinhTrang = tinhTrang;
        this.doiDiem = doiDiem;
    }

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

    public String getLoaiGiamGia() {
        return loaiGiamGia;
    }

    public void setLoaiGiamGia(String loaiGiamGia) {
        this.loaiGiamGia = loaiGiamGia;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public BigDecimal getMucToiDa() {
        return mucToiDa;
    }

    public void setMucToiDa(BigDecimal mucToiDa) {
        this.mucToiDa = mucToiDa;
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

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public DoiDiem getDoiDiem() {
        return doiDiem;
    }

    public void setDoiDiem(DoiDiem doiDiem) {
        this.doiDiem = doiDiem;
    }

}
