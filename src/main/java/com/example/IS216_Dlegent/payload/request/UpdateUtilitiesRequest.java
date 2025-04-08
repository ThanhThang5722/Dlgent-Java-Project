package com.example.IS216_Dlegent.payload.request;

public class UpdateUtilitiesRequest {
    private Long idTienIch;
    private Long idLoaiPhong;
    private String status;

    UpdateUtilitiesRequest(){}
    
    public UpdateUtilitiesRequest(Long idTienIch, Long idLoaiPhong, String status) {
        this.idTienIch = idTienIch;
        this.idLoaiPhong = idLoaiPhong;
        this.status = status;
    }

    public Long getIdTienIch() {
        return idTienIch;
    }
    public void setIdTienIch(Long idTienIch) {
        this.idTienIch = idTienIch;
    }
    public Long getIdLoaiPhong() {
        return idLoaiPhong;
    }
    public void setIdLoaiPhong(Long idLoaiPhong) {
        this.idLoaiPhong = idLoaiPhong;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
