package com.example.IS216_Dlegent.model;

import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "DICH_VU")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEN_DICH_VU", nullable = false)
    private String serviceName;
    @Column(name = "MO_TA", nullable = false)
    private String moTa;

    public Services() {
    }

    public Services(Long id, String serviceName) {
        this.id = id;
        this.serviceName = serviceName;
    }

    public Services(Long id, String serviceName, String moTa) {
        this.id = id;
        this.serviceName = serviceName;
        this.moTa = moTa;
    }

    public Services(Long serviceId) {
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}
