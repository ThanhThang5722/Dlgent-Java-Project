package com.example.IS216_Dlegent.payload.dto;

import com.example.IS216_Dlegent.model.Phong;

public class PhongDTO {
    private Long id;
    private Long khuNghiDuongId;
    private Long loaiPhongId;
    private String tenLoaiPhong;
    private Integer maSo;
    private String tinhTrang;

    public PhongDTO() {}

    public PhongDTO(Long id, Long khuNghiDuongId, Long loaiPhongId, String tenLoaiPhong, Integer maSo, String tinhTrang) {
        this.id = id;
        this.khuNghiDuongId = khuNghiDuongId;
        this.loaiPhongId = loaiPhongId;
        this.tenLoaiPhong = tenLoaiPhong;
        this.maSo = maSo;
        this.tinhTrang = tinhTrang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public PhongDTO convertToDTO(Phong phong) {
        return new PhongDTO(
            phong.getId(),
            phong.getKhuNghiDuong().getId(),
            phong.getLoaiPhong().getId(),
            phong.getLoaiPhong().getTenLoaiPhong(),
            phong.getMaSo(),
            phong.getTinhTrang()
        );
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

}

