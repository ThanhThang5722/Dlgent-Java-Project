package com.example.IS216_Dlegent.payload.request;

import java.util.List;

public class InsertGoiDatPhongRequest {
    private Long loaiPhongId;
    private List<Long> dsDichVuId;
    private List<Integer> dsGiamGia;
    public InsertGoiDatPhongRequest(Long loaiPhongId, List<Long> dsDichVuId) {
        this.loaiPhongId = loaiPhongId;
        this.dsDichVuId = dsDichVuId;
    }
    public InsertGoiDatPhongRequest() {}
    public Long getLoaiPhongId() {
        return loaiPhongId;
    }
    public void setLoaiPhongId(Long loaiPhongId) {
        this.loaiPhongId = loaiPhongId;
    }
    public List<Long> getDsDichVuId() {
        return dsDichVuId;
    }
    public void setDsDichVuId(List<Long> dsDichVuId) {
        this.dsDichVuId = dsDichVuId;
    }
    public List<Integer> getDsGiamGia() {
        return dsGiamGia;
    }
    public void setDsGiamGia(List<Integer> dsGiamGia) {
        this.dsGiamGia = dsGiamGia;
    }
}
