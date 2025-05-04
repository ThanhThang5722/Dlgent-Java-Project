package com.example.IS216_Dlegent.payload.dto;

public class ChiTietDatPhongDTO {
    private Long id;
    private String tenLoaiPhong;
    // private GiamGia?
    private int soLuongPhong;
    private int soLuongDichVuYeuCau;
    private int tongGiaTien;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String tinhTrang;

    public ChiTietDatPhongDTO() {
    }

    public ChiTietDatPhongDTO(Long id, String tenLoaiPhong, int soLuongPhong, int soLuongDichVuYeuCau,
            int tongGiaTien,
            String ngayBatDau, String ngayKetThuc, String tinhTrang) {
        this.id = id;
        this.tenLoaiPhong = tenLoaiPhong;
        this.soLuongPhong = soLuongPhong;
        this.soLuongDichVuYeuCau = soLuongDichVuYeuCau;
        this.tongGiaTien = tongGiaTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tinhTrang = tinhTrang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public int getSoLuongPhong() {
        return soLuongPhong;
    }

    public void setSoLuongPhong(int soLuongPhong) {
        this.soLuongPhong = soLuongPhong;
    }

    public int getSoLuongDichVuYeuCau() {
        return soLuongDichVuYeuCau;
    }

    public void setSoLuongDichVuYeuCau(int soLuongDichVuYeuCau) {
        this.soLuongDichVuYeuCau = soLuongDichVuYeuCau;
    }

    public int getTongGiaTien() {
        return tongGiaTien;
    }

    public void setTongGiaTien(int tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

}
