package com.example.IS216_Dlegent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.IS216_Dlegent.controller.API.AdminAPI;
import com.example.IS216_Dlegent.model.DoiTac;
import com.example.IS216_Dlegent.model.LichSuRutTien;
import com.example.IS216_Dlegent.repository.DoiTacRepository;
import com.example.IS216_Dlegent.repository.LichSuRutTienRepository;

@SpringBootTest
class DuyetRutTienTest {
    @Mock
    private LichSuRutTienRepository lichSuRutTienRepository;

    @Mock
    private DoiTacRepository doiTacRepository;

    @InjectMocks
    private AdminAPI adminAPI;

    @Test
    void testDuyetRutTien(){
        Long id = 1L;
        LichSuRutTien yeuCau = new LichSuRutTien();
        yeuCau.setId(id);
        yeuCau.setTrangThaiRutTien("Chờ phê duyệt");
        yeuCau.setSoTien(BigDecimal.valueOf(50000));

        DoiTac doiTac = new DoiTac();
        doiTac.setSoDu(BigDecimal.valueOf(100000));

        Mockito.when(lichSuRutTienRepository.findById(id))
                .thenReturn(Optional.of(yeuCau));

        Mockito.when(doiTacRepository.findById(yeuCau.getDoiTacId()))
                .thenReturn(Optional.of(doiTac));

        ResponseEntity<?> response = adminAPI.duyetRutTien(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(50000), doiTac.getSoDu());
    }

    @Test
    void testDuyetRutTienAm(){
        Long id = 1L;
        LichSuRutTien yeuCau = new LichSuRutTien();
        yeuCau.setId(id);
        yeuCau.setTrangThaiRutTien("Chờ phê duyệt");
        yeuCau.setSoTien(BigDecimal.valueOf(99999999));

        DoiTac doiTac = new DoiTac();
        doiTac.setSoDu(BigDecimal.valueOf(100000));

        Mockito.when(lichSuRutTienRepository.findById(id))
                .thenReturn(Optional.of(yeuCau));

        Mockito.when(doiTacRepository.findById(yeuCau.getDoiTacId()))
                .thenReturn(Optional.of(doiTac));

        ResponseEntity<?> response = adminAPI.duyetRutTien(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    
}

