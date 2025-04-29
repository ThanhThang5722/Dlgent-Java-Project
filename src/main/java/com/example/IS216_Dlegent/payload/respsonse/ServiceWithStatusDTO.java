package com.example.IS216_Dlegent.payload.respsonse;

public class ServiceWithStatusDTO {
    private Long serviceId;
    private String serviceName;
    private Double price;
    private Boolean daCo;
    public ServiceWithStatusDTO(){}
    public ServiceWithStatusDTO(Long serviceId, String serviceName, Double price, Boolean daCo) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.daCo = daCo;
    }
    public Long getServiceId() {
        return serviceId;
    }
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getDaCo() {
        return daCo;
    }
    public void setDaCo(Boolean daCo) {
        this.daCo = daCo;
    }
    
}
