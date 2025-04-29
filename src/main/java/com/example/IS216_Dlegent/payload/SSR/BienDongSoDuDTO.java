package com.example.IS216_Dlegent.payload.SSR;

import java.math.BigDecimal;

public class BienDongSoDuDTO {
    private String ngay;
    private BigDecimal thayDoiSoDu;
    public BienDongSoDuDTO(){}
    public BienDongSoDuDTO(String ngay, BigDecimal thayDoiSoDu) {
        this.ngay = ngay;
        this.thayDoiSoDu = thayDoiSoDu;
    }

    public String getNgay() {
        return ngay;
    }

    public BigDecimal getThayDoiSoDu() {
        return thayDoiSoDu;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public void setThayDoiSoDu(BigDecimal thayDoiSoDu) {
        this.thayDoiSoDu = thayDoiSoDu;
    }
    
}
