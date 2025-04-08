package com.example.IS216_Dlegent.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIEN_ICH")
public class TienIch {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOAI_TIEN_ICH")
    private String loaiTienIch;

    @Column(name = "ICON_URL")
    private String iconURL;

    public TienIch(Long id, String loaiTienIch, String iconURL) {
        this.id = id;
        this.loaiTienIch = loaiTienIch;
        this.iconURL = iconURL;
    }
    
    @ManyToMany(mappedBy = "tienIchSet")
    private Set<LoaiPhong> loaiPhongs;

    public TienIch() {}

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

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Set<LoaiPhong> getLoaiPhongs() {
        return loaiPhongs;
    }

    public void setLoaiPhongs(Set<LoaiPhong> loaiPhongs) {
        this.loaiPhongs = loaiPhongs;
    }

    
}
