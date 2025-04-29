package com.example.IS216_Dlegent.payload.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.IS216_Dlegent.model.DichVuMacDinh;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class GoiDatPhongDTO {
    private Long id;
    private Long loaiPhongId;
    private String tenLoaiPhong;
    private BigDecimal tongGiaTien;
    private List<DichVuMacDinhDTO> dichVuMacDinhs = new ArrayList<>();
    
    public GoiDatPhongDTO() {
    }
    
    public GoiDatPhongDTO(GoiDatPhong goiDatPhong) {
        this.id = goiDatPhong.getId();
        if (goiDatPhong.getLoaiPhong() != null) {
            this.loaiPhongId = goiDatPhong.getLoaiPhong().getId();
            this.tenLoaiPhong = goiDatPhong.getLoaiPhong().getTenLoaiPhong();
        }
        
        // Tạo danh sách dịch vụ mặc định và tính lại tổng giá tiền
        BigDecimal tongGia = BigDecimal.ZERO;
        if (goiDatPhong.getDichVuMacDinhs() != null) {
            for (DichVuMacDinh dvmd : goiDatPhong.getDichVuMacDinhs()) {
                DichVuMacDinhDTO dichVuDTO = new DichVuMacDinhDTO(dvmd);
                this.dichVuMacDinhs.add(dichVuDTO);
                // Cộng dồn giá sau giảm giá của từng dịch vụ
                tongGia = tongGia.add(dichVuDTO.getGiaSauGiam());
            }
        }
        
        // Cập nhật tổng giá tiền
        this.tongGiaTien = tongGia;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoaiPhongId() {
        return loaiPhongId;
    }

    public void setLoaiPhongId(Long loaiPhongId) {
        this.loaiPhongId = loaiPhongId;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public BigDecimal getTongGiaTien() {
        return tongGiaTien;
    }

    public void setTongGiaTien(BigDecimal tongGiaTien) {
        this.tongGiaTien = tongGiaTien;
    }

    public List<DichVuMacDinhDTO> getDichVuMacDinhs() {
        return dichVuMacDinhs;
    }

    public void setDichVuMacDinhs(List<DichVuMacDinhDTO> dichVuMacDinhs) {
        this.dichVuMacDinhs = dichVuMacDinhs;
    }
    
    // Nested DTO class for DichVuMacDinh
    @JsonIgnoreProperties({"goiDatPhong"})
    public static class DichVuMacDinhDTO {
        private Long id;
        private Long dichVuId;
        private String tenDichVu;
        private Double giaDichVu;
        private BigDecimal giamGia;
        private BigDecimal giaSauGiam;
        
        public DichVuMacDinhDTO() {
        }
        
        public DichVuMacDinhDTO(DichVuMacDinh dichVuMacDinh) {
            this.id = dichVuMacDinh.getId();
            if (dichVuMacDinh.getDichVuKhuNghiDuong() != null) {
                this.dichVuId = dichVuMacDinh.getDichVuKhuNghiDuong().getId();
                if (dichVuMacDinh.getDichVuKhuNghiDuong().getDichVu() != null) {
                    this.tenDichVu = dichVuMacDinh.getDichVuKhuNghiDuong().getDichVu().getServiceName();
                }
                this.giaDichVu = dichVuMacDinh.getDichVuKhuNghiDuong().getGia();
            }
            this.giamGia = dichVuMacDinh.getGIAMGIA();
            
            // Tính giá sau giảm giá
            this.giaSauGiam = calculateGiaSauGiam();
        }
        
        // Tính giá sau khi giảm
        private BigDecimal calculateGiaSauGiam() {
            if (this.giaDichVu == null) {
                return BigDecimal.ZERO;
            }
            
            BigDecimal giaGoc = BigDecimal.valueOf(this.giaDichVu);
            if (this.giamGia == null || this.giamGia.compareTo(BigDecimal.ZERO) <= 0) {
                return giaGoc;
            }
            
            // Tính số tiền được giảm (giá gốc * tỷ lệ giảm giá / 100)
            BigDecimal soTienGiam = giaGoc.multiply(this.giamGia).divide(new BigDecimal(100));
            
            // Giá sau giảm = giá gốc - số tiền giảm
            return giaGoc.subtract(soTienGiam);
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getDichVuId() {
            return dichVuId;
        }

        public void setDichVuId(Long dichVuId) {
            this.dichVuId = dichVuId;
        }

        public String getTenDichVu() {
            return tenDichVu;
        }

        public void setTenDichVu(String tenDichVu) {
            this.tenDichVu = tenDichVu;
        }

        public Double getGiaDichVu() {
            return giaDichVu;
        }

        public void setGiaDichVu(Double giaDichVu) {
            this.giaDichVu = giaDichVu;
        }

        public BigDecimal getGiamGia() {
            return giamGia;
        }

        public void setGiamGia(BigDecimal giamGia) {
            this.giamGia = giamGia;
        }
        
        public BigDecimal getGiaSauGiam() {
            return giaSauGiam;
        }
        
        public void setGiaSauGiam(BigDecimal giaSauGiam) {
            this.giaSauGiam = giaSauGiam;
        }
    }
} 