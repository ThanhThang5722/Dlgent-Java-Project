package com.example.IS216_Dlegent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIEN_ICH")
public class Utilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOAI_TIEN_ICH")
    private String utilityType;

    @Column(name = "ICON_URL")
    private String iconUrl;

    public Utilities() {
    }

    public Utilities(Long id, String utilityType, String iconUrl) {
        this.id = id;
        this.utilityType = utilityType;
        this.iconUrl = iconUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
