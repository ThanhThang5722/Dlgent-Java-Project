package com.example.IS216_Dlegent.payload.request;

public class RoomRequest {
    private Long khuNghiDuongId;
    private Long loaiPhongId;
    private Integer maSo;
    private String tinhTrang;
    public RoomRequest() {
    }
    public RoomRequest(Long khuNghiDuongId, Long loaiPhongId, Integer maSo, String tinhTrang) {
        this.khuNghiDuongId = khuNghiDuongId;
        this.loaiPhongId = loaiPhongId;
        this.maSo = maSo;
        this.tinhTrang = tinhTrang;
    }
    public Long getKhuNghiDuongId() {
        return khuNghiDuongId;
    }
    public void setKhuNghiDuongId(Long khuNghiDuongId) {
        this.khuNghiDuongId = khuNghiDuongId;
    }
    public Long getLoaiPhongId() {
        return loaiPhongId;
    }
    public void setLoaiPhongId(Long loaiPhongId) {
        this.loaiPhongId = loaiPhongId;
    }
    public Integer getMaSo() {
        return maSo;
    }
    public void setMaSo(Integer maSo) {
        this.maSo = maSo;
    }
    public String getTinhTrang() {
        return tinhTrang;
    }
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
}
