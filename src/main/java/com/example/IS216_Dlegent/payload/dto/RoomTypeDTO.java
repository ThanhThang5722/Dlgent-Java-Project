package com.example.IS216_Dlegent.payload.dto;

import java.util.List;

import com.example.IS216_Dlegent.model.DanhGia;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.TienIch;

public class RoomTypeDTO {
    private Long id;
    private String roomtypeName;
    private Double area;
    private String quantityRoomType;
    private String standardRoomType;
    private Integer bedNum;
    private Integer peopleNum;
    private Double price;
    private List<HinhPhong> images;
    private List<TienIch> utilities;
    private List<GoiDatPhongDTO> goiDatPhongs;

    public RoomTypeDTO(Long id, String roomtypeName, Double area, String quantityRoomType, String standardRoomType,
            Integer bedNum, Integer peopleNum, Double price) {
        this.id = id;
        this.roomtypeName = roomtypeName;
        this.area = area;
        this.quantityRoomType = quantityRoomType;
        this.standardRoomType = standardRoomType;
        this.bedNum = bedNum;
        this.peopleNum = peopleNum;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomtypeName() {
        return roomtypeName;
    }

    public void setRoomtypeName(String roomtypeName) {
        this.roomtypeName = roomtypeName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getQuantityRoomType() {
        return quantityRoomType;
    }

    public void setQuantityRoomType(String quantityRoomType) {
        this.quantityRoomType = quantityRoomType;
    }

    public String getStandardRoomType() {
        return standardRoomType;
    }

    public void setStandardRoomType(String standardRoomType) {
        this.standardRoomType = standardRoomType;
    }

    public Integer getBedNum() {
        return bedNum;
    }

    public void setBedNum(Integer bedNum) {
        this.bedNum = bedNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<HinhPhong> getImages() {
        return images;
    }

    public void setImages(List<HinhPhong> images) {
        this.images = images;
    }

    public List<TienIch> getUtilities() {
        return utilities;
    }

    public void setUtilities(List<TienIch> utilities) {
        this.utilities = utilities;
    }

    public List<GoiDatPhongDTO> getGoiDatPhongDTOs() {
        return goiDatPhongs;
    }

    public void setGoiDatPhongDTOs(List<GoiDatPhongDTO> goiDatPhongs) {
        this.goiDatPhongs = goiDatPhongs;
    }
}
