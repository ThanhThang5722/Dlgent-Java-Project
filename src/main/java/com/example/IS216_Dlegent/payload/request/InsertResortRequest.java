package com.example.IS216_Dlegent.payload.request;

public class InsertResortRequest {
    private String resortName;
    private String address;
    private String city;
    private String district;
    private String province;
    public InsertResortRequest() {}
    public InsertResortRequest(String resortName, String address, String city, String district, String province) {
        this.resortName = resortName;
        this.address = address;
        this.city = city;
        this.district = district;
        this.province = province;
    }
    public String getResortName() {
        return resortName;
    }
    public void setResortName(String resortName) {
        this.resortName = resortName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    
}
