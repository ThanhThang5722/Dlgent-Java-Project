package com.example.IS216_Dlegent.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;

public class RoomTypeRepositoryImpl implements RoomTypeRepository {
    private Connection connection;

    public RoomTypeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RoomTypeDetailsDTO findById(Long id) {
        String sql = "SELECT * FROM RoomType WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Xử lý kết quả trả về từ cơ sở dữ liệu và ánh xạ vào DTO
                RoomTypeDetailsDTO roomType = new RoomTypeDetailsDTO.Builder()
                                                .setId(rs.getLong("id"))
                                                .setTenLoaiPhong(rs.getString("tenLoaiPhong")).build();
                // ... ánh xạ các trường khác
                return roomType;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RoomTypeDetailsDTO> findAll() {
        List<RoomTypeDetailsDTO> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM RoomType";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                RoomTypeDetailsDTO roomType = new RoomTypeDetailsDTO.Builder()
                                                    .setId(rs.getLong("id"))
                                                    .setTenLoaiPhong(rs.getString("tenLoaiPhong"))
                                                    .build();
                // ... ánh xạ các trường khác
                roomTypes.add(roomType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    @Override
    public List<RoomTypeDetailsDTO> findAllByPartnerID(Long partnerId) {
        List<RoomTypeDetailsDTO> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM RoomType";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                RoomTypeDetailsDTO roomType = new RoomTypeDetailsDTO.Builder()
                                                    .setId(rs.getLong("id"))
                                                    .setTenLoaiPhong(rs.getString("tenLoaiPhong"))
                                                    .build();
                // ... ánh xạ các trường khác
                roomTypes.add(roomType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    @Override
    public void save(RoomTypeDetailsDTO roomTypeDetailsDTO) {
        String sql = "INSERT INTO RoomType (tenLoaiPhong, dienTich, ...) VALUES (?, ?, ...)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, roomTypeDetailsDTO.getTenLoaiPhong());
            stmt.setDouble(2, roomTypeDetailsDTO.getDienTich());
            // ... ánh xạ các trường khác
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM RoomType WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}