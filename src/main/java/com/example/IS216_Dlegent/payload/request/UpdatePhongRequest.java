package com.example.IS216_Dlegent.payload.request;

public class UpdatePhongRequest {
    private Long phongId;
    private String tinhTrang;

    UpdatePhongRequest() {}
    UpdatePhongRequest(Long phongId, String tinhTrang) {
        this.phongId = phongId;
        this.tinhTrang = tinhTrang;
    }
    public Long getPhongId() {
        return phongId;
    }
    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }
    public String getTinhTrang() {
        return tinhTrang;
    }
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    
}
