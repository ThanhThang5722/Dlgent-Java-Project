package com.example.IS216_Dlegent.payload.request;

import java.time.LocalDateTime;

public class InsertDanhGiaRequest {
    private Long resortId;
    private Long customerId;
    private Integer diem;
    private String noiDung;

    public InsertDanhGiaRequest() {
    }

    public InsertDanhGiaRequest(Long resortId, Long customerId, Integer diem, String noiDung) {
        this.resortId = resortId;
        this.customerId = customerId;
        this.diem = diem;
        this.noiDung = noiDung;
    }

    public Long getResortId() {
        return resortId;
    }

    public void setResortId(Long resortId) {
        this.resortId = resortId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getDiem() {
        return diem;
    }

    public void setDiem(Integer diem) {
        this.diem = diem;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

}
