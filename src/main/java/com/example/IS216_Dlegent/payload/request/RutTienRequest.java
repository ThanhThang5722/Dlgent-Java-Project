package com.example.IS216_Dlegent.payload.request;

import java.math.BigDecimal;

public class RutTienRequest {
    private Long doiTacId;
    private BigDecimal soTien;
    public RutTienRequest() {}
    public RutTienRequest(Long doiTacId, BigDecimal soTien) {
        this.doiTacId = doiTacId;
        this.soTien = soTien;
    }
    public Long getDoiTacId() {
        return doiTacId;
    }
    public void setDoiTacId(Long doiTacId) {
        this.doiTacId = doiTacId;
    }
    public BigDecimal getSoTien() {
        return soTien;
    }
    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }
    
}