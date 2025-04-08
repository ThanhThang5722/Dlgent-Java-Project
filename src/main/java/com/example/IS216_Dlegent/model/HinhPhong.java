package com.example.IS216_Dlegent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "HINH_PHONG")
public class HinhPhong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_LOAI_PHONG", nullable = false)
    private Long roomTypeID;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "IS_DELETED", nullable = false)
    private Long isDeleted;
    HinhPhong(){}
    public HinhPhong(Long roomTypeID, String url, Long isDeleted) {
        this.roomTypeID = roomTypeID;
        this.url = url;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(Long roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }
    @Override
    public String toString() {
        return "HinhPhong [id=" + id + ", roomTypeID=" + roomTypeID + ", url=" + url + ", isDeleted=" + isDeleted + "]";
    }
    

}
