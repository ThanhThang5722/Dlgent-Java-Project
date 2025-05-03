package com.example.IS216_Dlegent.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.DanhGia;
import com.example.IS216_Dlegent.model.GoiDatPhong;
import com.example.IS216_Dlegent.model.HinhPhong;
import com.example.IS216_Dlegent.model.TienIch;
import com.example.IS216_Dlegent.model.UtilitiesOfRoomType;
import com.example.IS216_Dlegent.payload.dto.RoomTypeDTO;
import com.example.IS216_Dlegent.payload.dto.GoiDatPhongDTO;
import com.example.IS216_Dlegent.repository.HinhPhongRepo;
import com.example.IS216_Dlegent.repository.DanhGiaRepository;
import com.example.IS216_Dlegent.repository.GoiDaiPhongRepository;
import com.example.IS216_Dlegent.repository.UtilitiesOfRoomTypeRepository;

@Repository
public class JdbcRoomType {
        private final JdbcTemplate jdbcTemplate;

        @Autowired
        private HinhPhongRepo hinhPhongRepo;
        @Autowired
        private UtilitiesOfRoomTypeRepository utilitiesOfRoomTypeRepository;
        @Autowired
        private GoiDaiPhongRepository goiDatPhongRepo;
        @Autowired
        private DanhGiaRepository danhGiaRepo;

        public JdbcRoomType(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        public List<RoomTypeDTO> getRoomByResort(Long resortId, LocalDateTime ngayNhan, LocalDateTime ngayTra,
                        int soNguoi) {
                SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                                .withProcedureName("TimLoaiPhong")
                                .declareParameters(
                                                new SqlParameter("p_resortId", Types.INTEGER),
                                                new SqlParameter("p_ngay_nhan", Types.TIMESTAMP),
                                                new SqlParameter("p_ngay_tra", Types.TIMESTAMP),
                                                new SqlParameter("p_so_nguoi", Types.INTEGER),
                                                new SqlOutParameter("p_output", Types.REF_CURSOR))
                                .returningResultSet("p_output", new RoomTypeRowMapper());

                MapSqlParameterSource params = new MapSqlParameterSource()
                                .addValue("p_resortId", resortId)
                                .addValue("p_ngay_nhan", Timestamp.valueOf(ngayNhan))
                                .addValue("p_ngay_tra", Timestamp.valueOf(ngayTra))
                                .addValue("p_so_nguoi", soNguoi);

                Map<String, Object> result = call.execute(params);

                @SuppressWarnings("unchecked")
                List<RoomTypeDTO> roomTypes = (List<RoomTypeDTO>) result.get("p_output");

                for (RoomTypeDTO roomtype : roomTypes) {
                        List<HinhPhong> hinhPhongs = hinhPhongRepo.findByRoomTypeID(roomtype.getId());

                        List<GoiDatPhongDTO> goiDatPhongs = (goiDatPhongRepo.findByLoaiPhong_Id(roomtype.getId())
                                        .stream()
                                        .map(GoiDatPhongDTO::new)
                                        .collect(Collectors.toList()));

                        List<UtilitiesOfRoomType> tienIchs = utilitiesOfRoomTypeRepository
                                        .findByLoaiPhong_Id(roomtype.getId());

                        roomtype.setImages(hinhPhongs);
                        roomtype.setGoiDatPhongDTOs(goiDatPhongs);
                        roomtype.setUtilities(tienIchs.stream()
                                        .map(tienIch -> new TienIch(
                                                        tienIch.getUtility().getId(),
                                                        tienIch.getUtility().getUtilityType(),
                                                        tienIch.getUtility().getIconUrl()))
                                        .collect(Collectors.toList()));
                }

                return roomTypes;
        }

        private static class RoomTypeRowMapper implements RowMapper<RoomTypeDTO> {
                @Override
                public RoomTypeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new RoomTypeDTO(
                                        rs.getLong("ID"),
                                        rs.getString("TEN_LOAI_PHONG"),
                                        rs.getDouble("DIEN_TICH"),
                                        rs.getString("LOAI_PHONG_THEO_SO_LUONG"),
                                        rs.getString("LOAI_PHONG_THEO_TIEU_CHUAN"),
                                        rs.getInt("SO_GIUONG"),
                                        rs.getInt("SO_NGUOI"),
                                        rs.getDouble("GIA"));
                }
        }
}
