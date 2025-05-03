package com.example.IS216_Dlegent.payload.respsonse;

public class ServicesOfResortResponse {
    private Long id;
    private Long resortId;
    private String resortName;
    private Long dichVuId;
    private String tenDichVu;
    private Double gia;
    private Boolean isDeleted;
    public ServicesOfResortResponse(Long id, Long resortId, String resortName, Long dichVuId, String tenDichVu,
            Double gia, Boolean isDeleted) {
        this.id = id;
        this.resortId = resortId;
        this.resortName = resortName;
        this.dichVuId = dichVuId;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
        this.isDeleted = isDeleted;
    }
    public ServicesOfResortResponse(){}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getResortId() {
        return resortId;
    }
    public void setResortId(Long resortId) {
        this.resortId = resortId;
    }
    public String getResortName() {
        return resortName;
    }
    public void setResortName(String resortName) {
        this.resortName = resortName;
    }
    public Long getDichVuId() {
        return dichVuId;
    }
    public void setDichVuId(Long dichVuId) {
        this.dichVuId = dichVuId;
    }
    public String getTenDichVu() {
        return tenDichVu;
    }
    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }
    public Double getGia() {
        return gia;
    }
    public void setGia(Double gia) {
        this.gia = gia;
    }
    public Boolean getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
}
