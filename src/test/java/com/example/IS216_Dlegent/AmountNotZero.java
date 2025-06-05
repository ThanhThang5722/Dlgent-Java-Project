package com.example.IS216_Dlegent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.Phong;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.service.PhongService;
import com.example.IS216_Dlegent.service.ZalopayService;

@SpringBootTest
class AmountNotZero {

	@Test
	void contextLoads() {
	}

	@Mock
	private DatPhongRepository datPhongRepository;

	@Mock
	private ChiTietDatPhongRepository chiTietDatPhongRepository;

	@Mock
	private PhongService phongService;

	@InjectMocks
	private ZalopayService zalopayService;

	@Test
	void createOrder_amountZero() {
		Map<String, Object> orderRequest = new HashMap<>();
		orderRequest.put("amount", 0);
		Long idDatPhong = 1L;

		DatPhong mockDatPhong = new DatPhong();
		mockDatPhong.setId(idDatPhong);

		Mockito.when(datPhongRepository.findById(idDatPhong)).thenReturn(Optional.of(mockDatPhong));

		List<ChiTietDatPhong> mockChiTietList = new ArrayList<>();
		ChiTietDatPhong chiTiet = new ChiTietDatPhong();

		GoiDatPhong goiDatPhong = new GoiDatPhong();
		LoaiPhong loaiPhong = new LoaiPhong();
		loaiPhong.setId(1L);
		loaiPhong.setTenLoaiPhong("Phòng đôi");
		goiDatPhong.setLoaiPhong(loaiPhong);

		chiTiet.setGoiDatPhong(goiDatPhong);
		chiTiet.setNgayBatDau(LocalDateTime.now());
		chiTiet.setNgayKetThuc(LocalDateTime.now().plusDays(1));

		// Mock phongService để trả về danh sách phòng trống
		List<Phong> mockPhongs = new ArrayList<>();
		Phong phong = new Phong();
		phong.setId(1L);
		phong.setTinhTrang("Available");
		phong.setLoaiPhong(loaiPhong);
		mockPhongs.add(phong);
		when(phongService.getPhongKhongBanTrongKhoangThoiGian(any(LocalDateTime.class), any(LocalDateTime.class)))
				.thenReturn(mockPhongs);

		mockChiTietList.add(chiTiet);

		Mockito.when(chiTietDatPhongRepository.findByDatPhong_Id(idDatPhong))
				.thenReturn(mockChiTietList);

		String result = zalopayService.createOrder(orderRequest, idDatPhong);

		assertTrue(result.contains("error"));
    	assertTrue(result.contains("Amount can't be zero"));
	}
	
	@Test
	void createOrder_amountNotZero() {
		Map<String, Object> orderRequest = new HashMap<>();
		orderRequest.put("amount", 0);
		Long idDatPhong = 1L;

		DatPhong mockDatPhong = new DatPhong();
		mockDatPhong.setId(idDatPhong);

		Mockito.when(datPhongRepository.findById(idDatPhong)).thenReturn(Optional.of(mockDatPhong));

		List<ChiTietDatPhong> mockChiTietList = new ArrayList<>();
		ChiTietDatPhong chiTiet = new ChiTietDatPhong();

		GoiDatPhong goiDatPhong = new GoiDatPhong();
		LoaiPhong loaiPhong = new LoaiPhong();
		loaiPhong.setId(1L);
		loaiPhong.setTenLoaiPhong("Phòng đôi");
		goiDatPhong.setLoaiPhong(loaiPhong);

		chiTiet.setGoiDatPhong(goiDatPhong);
		chiTiet.setNgayBatDau(LocalDateTime.now());
		chiTiet.setNgayKetThuc(LocalDateTime.now().plusDays(1));

		// Mock phongService để trả về danh sách phòng trống
		List<Phong> mockPhongs = new ArrayList<>();
		Phong phong = new Phong();
		phong.setId(1L);
		phong.setTinhTrang("Available");
		phong.setLoaiPhong(loaiPhong);
		mockPhongs.add(phong);
		when(phongService.getPhongKhongBanTrongKhoangThoiGian(any(LocalDateTime.class), any(LocalDateTime.class)))
				.thenReturn(mockPhongs);

		mockChiTietList.add(chiTiet);

		Mockito.when(chiTietDatPhongRepository.findByDatPhong_Id(idDatPhong))
				.thenReturn(mockChiTietList);

		String result = zalopayService.createOrder(orderRequest, idDatPhong);

		assertTrue(result.contains("order_url"));
	}
}
