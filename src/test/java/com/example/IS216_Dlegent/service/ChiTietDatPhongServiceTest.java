package com.example.IS216_Dlegent.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.payload.dto.ChiTietDatPhongDTO;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;

@ExtendWith(MockitoExtension.class)
public class ChiTietDatPhongServiceTest {

    @Mock
    private ChiTietDatPhongRepository chiTietDatPhongRepository;

    @InjectMocks
    private ChiTietDatPhongService chiTietDatPhongService;

    private ChiTietDatPhong mockChiTietDatPhong;
    private GoiDatPhong mockGoiDatPhong;
    private LoaiPhong mockLoaiPhong;

    @BeforeEach
    void setUp() {
        // Setup mock objects
        mockLoaiPhong = new LoaiPhong();
        mockLoaiPhong.setId(1L);
        mockLoaiPhong.setTenLoaiPhong("Deluxe Room");

        mockGoiDatPhong = new GoiDatPhong();
        mockGoiDatPhong.setId(1L);
        mockGoiDatPhong.setTongGiaTien(new BigDecimal("1000000")); // 1,000,000 VND per day
        mockGoiDatPhong.setLoaiPhong(mockLoaiPhong);

        mockChiTietDatPhong = new ChiTietDatPhong();
        mockChiTietDatPhong.setId(1L);
        mockChiTietDatPhong.setGoiDatPhong(mockGoiDatPhong);
        mockChiTietDatPhong.setSoLuongPhong(1);
        mockChiTietDatPhong.setTongGiaTien(new BigDecimal("2000000")); // 2 days * 1,000,000
        mockChiTietDatPhong.setNgayBatDau(LocalDateTime.of(2024, 1, 1, 14, 0));
        mockChiTietDatPhong.setNgayKetThuc(LocalDateTime.of(2024, 1, 3, 12, 0)); // 2 days
        mockChiTietDatPhong.setTrangThai("Pending");
        mockChiTietDatPhong.setSoLuongDichVuYeuCau(0);
    }

    @Test
    void testUpdateCartQuantity_Success() {
        // Given
        Long cartItemId = 1L;
        Integer newQuantity = 3;
        
        when(chiTietDatPhongRepository.findById(cartItemId)).thenReturn(Optional.of(mockChiTietDatPhong));
        when(chiTietDatPhongRepository.save(any(ChiTietDatPhong.class))).thenReturn(mockChiTietDatPhong);

        // When
        ResponseEntity<?> response = chiTietDatPhongService.updateCartQuantity(cartItemId, newQuantity);

        // Then
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ChiTietDatPhongDTO);
        
        ChiTietDatPhongDTO dto = (ChiTietDatPhongDTO) response.getBody();
        assertEquals(newQuantity.intValue(), dto.getSoLuongPhong());
        
        // Verify price calculation: 1,000,000 * 3 rooms * 2 days = 6,000,000
        assertEquals(6000000, dto.getTongGiaTien());
        
        // Verify database save was called
        verify(chiTietDatPhongRepository, times(1)).save(any(ChiTietDatPhong.class));
        verify(chiTietDatPhongRepository, times(2)).findById(cartItemId); // Once for update, once for verification
    }

    @Test
    void testUpdateCartQuantity_InvalidQuantity() {
        // Given
        Long cartItemId = 1L;
        Integer invalidQuantity = 0;
        
        when(chiTietDatPhongRepository.findById(cartItemId)).thenReturn(Optional.of(mockChiTietDatPhong));

        // When
        ResponseEntity<?> response = chiTietDatPhongService.updateCartQuantity(cartItemId, invalidQuantity);

        // Then
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals("Số lượng phòng phải lớn hơn 0", response.getBody());
        
        // Verify database save was NOT called
        verify(chiTietDatPhongRepository, never()).save(any(ChiTietDatPhong.class));
    }

    @Test
    void testUpdateCartQuantity_ItemNotFound() {
        // Given
        Long nonExistentId = 999L;
        Integer quantity = 2;
        
        when(chiTietDatPhongRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<?> response = chiTietDatPhongService.updateCartQuantity(nonExistentId, quantity);

        // Then
        assertTrue(response.getStatusCode().is4xxClientError());
        assertTrue(response.getBody().toString().contains("Cập nhật số lượng thất bại"));
        
        // Verify database save was NOT called
        verify(chiTietDatPhongRepository, never()).save(any(ChiTietDatPhong.class));
    }

    @Test
    void testPriceCalculation_SingleDay() {
        // Given - Setup for 1 day stay
        mockChiTietDatPhong.setNgayBatDau(LocalDateTime.of(2024, 1, 1, 14, 0));
        mockChiTietDatPhong.setNgayKetThuc(LocalDateTime.of(2024, 1, 2, 12, 0)); // 1 day
        
        Long cartItemId = 1L;
        Integer quantity = 2;
        
        when(chiTietDatPhongRepository.findById(cartItemId)).thenReturn(Optional.of(mockChiTietDatPhong));
        when(chiTietDatPhongRepository.save(any(ChiTietDatPhong.class))).thenReturn(mockChiTietDatPhong);

        // When
        ResponseEntity<?> response = chiTietDatPhongService.updateCartQuantity(cartItemId, quantity);

        // Then
        ChiTietDatPhongDTO dto = (ChiTietDatPhongDTO) response.getBody();
        
        // Verify price calculation: 1,000,000 * 2 rooms * 1 day = 2,000,000
        assertEquals(2000000, dto.getTongGiaTien());
    }
}
