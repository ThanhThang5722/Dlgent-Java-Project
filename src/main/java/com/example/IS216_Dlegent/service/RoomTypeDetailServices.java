package com.example.IS216_Dlegent.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.TienIch;
import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;

@Service
public class RoomTypeDetailServices {
    @Autowired
    LoaiPhongService loaiPhongService;

    @Autowired
    HinhPhongService hinhPhongService;

    @Autowired
    TienIchService tienIchService;

    @Autowired
    GoiDatPhongService goiDatPhongService;

    public List<RoomTypeDetailsDTO> getRoomTypeDetailsByPartnerId(Long doiTacId) {
        List<RoomTypeDetailsDTO> results = new ArrayList<>();
        List<LoaiPhong> dsLoaiPhongs = loaiPhongService.getRoomTypesByPartnerId(doiTacId);

        for(LoaiPhong lp : dsLoaiPhongs) {
            RoomTypeDetailsDTO roomTypeDetailsDTO = new RoomTypeDetailsDTO(lp);

            List<HinhPhong> hinhAnh = hinhPhongService.getHinhPhongByRoomTypeID(lp.getId());
            roomTypeDetailsDTO.setHinhAnh(hinhAnh);

            List<TienIch> tienIchs = tienIchService.getTienIchByLoaiPhongId(lp.getId());
            roomTypeDetailsDTO.setTienIch(tienIchs);

            List<GoiDatPhong> dsGoiDatPhongs = goiDatPhongService.getListGoiDatPhongByRoomTypeId(lp.getId());
            roomTypeDetailsDTO.setDsGoiDatPhongs(dsGoiDatPhongs);

            results.add(roomTypeDetailsDTO);
        }
        return results;
    }
}
