package com.example.IS216_Dlegent.payload.SSR;

import java.math.BigDecimal;
import java.util.List;

import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.model.TienIch;

public class RoomTypeDetailsDTO {
    private Long id;
    private Long idKhuNghiDuong;
    private String tenLoaiPhong;
    private Double dienTich;
    private String loaiPhongTheoSoLuong;
    private String loaiPhongTheoTieuChuan;
    private Integer soGiuong;
    private Integer soNguoi;
    private BigDecimal gia;

    private List<HinhPhong> hinhAnh;
    private List<TienIch> tienIch;
    private List<GoiDatPhong> dsGoiDatPhongs;

    public RoomTypeDetailsDTO(){}

    public RoomTypeDetailsDTO(Long id, Long idKhuNghiDuong, String tenLoaiPhong, Double dienTich,
            String loaiPhongTheoSoLuong, String loaiPhongTheoTieuChuan, Integer soGiuong, Integer soNguoi,
            BigDecimal gia) {
        this.id = id;
        this.idKhuNghiDuong = idKhuNghiDuong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.dienTich = dienTich;
        this.loaiPhongTheoSoLuong = loaiPhongTheoSoLuong;
        this.loaiPhongTheoTieuChuan = loaiPhongTheoTieuChuan;
        this.soGiuong = soGiuong;
        this.soNguoi = soNguoi;
        this.gia = gia;
    }
    
    public RoomTypeDetailsDTO(LoaiPhong lp) {
        this.id = lp.getId();
        this.idKhuNghiDuong = lp.getKhuNghiDuong().getId();
        this.tenLoaiPhong = lp.getTenLoaiPhong();
        this.dienTich = lp.getDienTich();
        this.loaiPhongTheoSoLuong = lp.getLoaiPhongTheoSoLuong();
        this.loaiPhongTheoTieuChuan = lp.getLoaiPhongTheoTieuChuan();
        this.soGiuong = lp.getSoGiuong();
        this.soNguoi = lp.getSoNguoi();
        this.gia = lp.getGia();

        hinhAnh = null;
        tienIch = null;
        dsGoiDatPhongs = null;
    }
    public void setHinhAnh(List<HinhPhong> hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    public void setTienIch(List<TienIch> tienIch) {
        this.tienIch = tienIch;
    } 
    public void setDsGoiDatPhongs(List<GoiDatPhong> dsGoiDatPhongs) {
        this.dsGoiDatPhongs = dsGoiDatPhongs;
    }

    // Private constructor to enforce usage of Builder
    private RoomTypeDetailsDTO(Builder builder) {
        this.id = builder.id;
        this.tenLoaiPhong = builder.tenLoaiPhong;
        this.dienTich = builder.dienTich;
        this.loaiPhongTheoSoLuong = builder.loaiPhongTheoSoLuong;
        this.loaiPhongTheoTieuChuan = builder.loaiPhongTheoTieuChuan;
        this.soGiuong = builder.soGiuong;
        this.soNguoi = builder.soNguoi;
        this.gia = builder.gia;
        this.hinhAnh = builder.hinhAnh;
        this.tienIch = builder.tienIch;
        this.dsGoiDatPhongs = builder.dsGoiDatPhongs;
        this.idKhuNghiDuong = builder.idKhuNghiDuong;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getIdKhuNghiDuong() {
        return idKhuNghiDuong;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public String getLoaiPhongTheoSoLuong() {
        return loaiPhongTheoSoLuong;
    }

    public String getLoaiPhongTheoTieuChuan() {
        return loaiPhongTheoTieuChuan;
    }

    public Integer getSoGiuong() {
        return soGiuong;
    }

    public Integer getSoNguoi() {
        return soNguoi;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public List<HinhPhong> getHinhAnh() {
        return hinhAnh;
    }

    public List<TienIch> getTienIch() {
        return tienIch;
    }

    public List<GoiDatPhong> getDsGoiDatPhongs() {
        return dsGoiDatPhongs;
    }

    // Builder class
    public static class Builder {
        private Long id;
        private Long idKhuNghiDuong;
        private String tenLoaiPhong;
        private Double dienTich;
        private String loaiPhongTheoSoLuong;
        private String loaiPhongTheoTieuChuan;
        private Integer soGiuong;
        private Integer soNguoi;
        private BigDecimal gia;

        private List<HinhPhong> hinhAnh;
        private List<TienIch> tienIch;
        private List<GoiDatPhong> dsGoiDatPhongs;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setIdKhuNghiDuong(Long idKhuNghiDuong) {
            this.idKhuNghiDuong = idKhuNghiDuong;
            return this;
        }

        public Builder setTenLoaiPhong(String tenLoaiPhong) {
            this.tenLoaiPhong = tenLoaiPhong;
            return this;
        }

        public Builder setDienTich(Double dienTich) {
            this.dienTich = dienTich;
            return this;
        }

        public Builder setLoaiPhongTheoSoLuong(String loaiPhongTheoSoLuong) {
            this.loaiPhongTheoSoLuong = loaiPhongTheoSoLuong;
            return this;
        }

        public Builder setLoaiPhongTheoTieuChuan(String loaiPhongTheoTieuChuan) {
            this.loaiPhongTheoTieuChuan = loaiPhongTheoTieuChuan;
            return this;
        }

        public Builder setSoGiuong(Integer soGiuong) {
            this.soGiuong = soGiuong;
            return this;
        }

        public Builder setSoNguoi(Integer soNguoi) {
            this.soNguoi = soNguoi;
            return this;
        }

        public Builder setGia(BigDecimal gia) {
            this.gia = gia;
            return this;
        }

        public Builder setHinhAnh(List<HinhPhong> hinhAnh) {
            this.hinhAnh = hinhAnh;
            return this;
        }

        public Builder setTienIch(List<TienIch> tienIch) {
            this.tienIch = tienIch;
            return this;
        }

        public Builder setDsGoiDatPhongs(List<GoiDatPhong> dsGoiDatPhongs) {
            this.dsGoiDatPhongs = dsGoiDatPhongs;
            return this;
        }

        public RoomTypeDetailsDTO build() {
            return new RoomTypeDetailsDTO(this);
        }
    }
}
