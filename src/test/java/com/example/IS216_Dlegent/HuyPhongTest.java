package com.example.IS216_Dlegent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.HoaDon;
import com.example.IS216_Dlegent.model.ThoiGianPhongBan;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.HoaDonRepositoryJPA;
import com.example.IS216_Dlegent.repository.ThoiGianPhongBanRepository;
import com.example.IS216_Dlegent.service.DatPhongService;

@SpringBootTest
class HuyPhongTest {

    @Mock
    private ChiTietDatPhongRepository chiTietRepo;
    
    @Mock
    private HoaDonRepositoryJPA hoaDonRepository;
    
    @Mock
    private ThoiGianPhongBanRepository thoiGianPhongBanRepository;
    
    @InjectMocks
    private DatPhongService datPhongService;
    
    private ChiTietDatPhong chiTietDatPhong;
    private HoaDon hoaDon;
    private DoiTac doiTac;
    private ThoiGianPhongBan thoiGianPhongBan;
    
    @BeforeEach
    void setUp() {
        // dữ liệu mẫu
        chiTietDatPhong = new ChiTietDatPhong();
        chiTietDatPhong.setId(1L);
        chiTietDatPhong.setTrangThai("Đã thanh toán");
        
        chiTietDatPhong.setNgayBatDau(LocalDateTime.now().plusDays(5));
        chiTietDatPhong.setNgayKetThuc(LocalDateTime.now().plusDays(7));
        
        doiTac = new DoiTac();
        doiTac.setId(1L);
        doiTac.setSoDu(new BigDecimal("1000000"));
        
        hoaDon = new HoaDon();
        hoaDon.setId(1L);
        hoaDon.setChiTietDatPhong(chiTietDatPhong);
        hoaDon.setDoiTac(doiTac);
        hoaDon.setTongGiaTien(new BigDecimal("500000"));
        
        thoiGianPhongBan = new ThoiGianPhongBan();
        thoiGianPhongBan.setId(1L);
        thoiGianPhongBan.setHoaDon(hoaDon);
    }
    
    @Test
    void testHuyPhong_ThanhCong() {

        when(chiTietRepo.findById(1L)).thenReturn(Optional.of(chiTietDatPhong));
        when(hoaDonRepository.findByChiTietDatPhong_Id(1L)).thenReturn(hoaDon);
        when(thoiGianPhongBanRepository.findByHoaDon_Id(1L)).thenReturn(thoiGianPhongBan);
        
        ResponseEntity<?> result = datPhongService.huyPhong(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Hủy đặt phòng thành công", result.getBody());
  
        verify(chiTietRepo).save(chiTietDatPhong);
        verify(thoiGianPhongBanRepository).delete(thoiGianPhongBan);
        
        // Kiểm tra số dư đối tác đã bị trừ
        assertEquals(new BigDecimal("500000"), doiTac.getSoDu());
        assertEquals("Đã hủy", chiTietDatPhong.getTrangThai());
    }
    
    @Test
    void testHuyPhong_QuaThoiGianHuy() {
   
        // Ngày bắt đầu là 2 ngày sau hiện tại (không đủ điều kiện hủy)
        chiTietDatPhong.setNgayBatDau(LocalDateTime.now().plusDays(2));
        when(chiTietRepo.findById(1L)).thenReturn(Optional.of(chiTietDatPhong));
        
        ResponseEntity<?> result = datPhongService.huyPhong(1L);
        
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Đã quá thời gian hủy phòng", result.getBody());
        
        verify(chiTietRepo, never()).save(any(ChiTietDatPhong.class));
    }
    
    @Test
    void testHuyPhong_NgayDaQua() {
 
        // Ngày bắt đầu là 1 ngày trước hiện tại
        chiTietDatPhong.setNgayBatDau(LocalDateTime.now().minusDays(1));
        when(chiTietRepo.findById(1L)).thenReturn(Optional.of(chiTietDatPhong));
        
        ResponseEntity<?> result = datPhongService.huyPhong(1L);
        
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Hủy đặt phòng thất bại", result.getBody());
        
        verify(chiTietRepo, never()).save(any(ChiTietDatPhong.class));
    }
}
