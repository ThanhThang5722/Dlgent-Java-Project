package com.example.IS216_Dlegent.payload.respsonse;

import com.example.IS216_Dlegent.model.TienIch;

public class TienIchResponse {
    private Long id;
    private String loaiTienIch;

    public TienIchResponse(Long id, String loaiTienIch) {
        this.id = id;
        this.loaiTienIch = loaiTienIch;
    }
    public static TienIchResponse fromEntity(TienIch entity) {
        return new TienIchResponse(entity.getId(), entity.getLoaiTienIch());
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLoaiTienIch() {
        return loaiTienIch;
    }
    public void setLoaiTienIch(String loaiTienIch) {
        this.loaiTienIch = loaiTienIch;
    }
    
}
