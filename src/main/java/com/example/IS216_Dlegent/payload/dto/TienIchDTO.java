package com.example.IS216_Dlegent.payload.dto;

public class TienIchDTO {
    private String loaiTienIch;
    private String iconUrl;

    public TienIchDTO(String loaiTienIch, String iconUrl) {
        this.loaiTienIch = loaiTienIch;
        this.iconUrl = iconUrl;
    }

    public String getLoaiTienIch() {
        return loaiTienIch;
    }

    public void setLoaiTienIch(String loaiTienIch) {
        this.loaiTienIch = loaiTienIch;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
