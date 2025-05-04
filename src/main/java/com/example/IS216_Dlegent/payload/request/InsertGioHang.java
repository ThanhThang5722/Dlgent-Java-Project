package com.example.IS216_Dlegent.payload.request;

public class InsertGioHang {
    private Long khachHangId;
    private Long goiDatPhongId;
    // private Integer soLuongPhong;
    // private Integer soLuongDichVuYeuCau;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String tinhTrang="Pending";
    public InsertGioHang(Long khachHangId, Long goiDatPhongId, String ngayBatDau, String ngayKetThuc) {
        this.khachHangId = khachHangId;
        this.goiDatPhongId = goiDatPhongId;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
    public Long getKhachHangId() {
        return khachHangId;
    }
    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }
    public Long getGoiDatPhongId() {
        return goiDatPhongId;
    }
    public void setGoiDatPhongId(Long goiDatPhongId) {
        this.goiDatPhongId = goiDatPhongId;
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
