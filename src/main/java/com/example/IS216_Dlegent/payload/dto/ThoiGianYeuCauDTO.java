package com.example.IS216_Dlegent.payload.dto;

import java.time.LocalDateTime;

public class ThoiGianYeuCauDTO {
    private LocalDateTime ngayBatDau;
    private LocalDateTime ngayKetThuc;

    public ThoiGianYeuCauDTO(){}
    public ThoiGianYeuCauDTO(LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }
    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }
    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    
}
