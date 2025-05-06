package com.example.IS216_Dlegent.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.IS216_Dlegent.model.KhuNghiDuong;
import com.example.IS216_Dlegent.model.LoaiPhong;
import com.example.IS216_Dlegent.payload.SSR.RoomTypeDetailsDTO;
import com.example.IS216_Dlegent.repository.KhuNghiDuongRepo;
import com.example.IS216_Dlegent.repository.LoaiPhongRepo;
import com.example.IS216_Dlegent.repository.jdbc.JdbcRoomType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class LoaiPhongService {
    private static final Logger logger = LoggerFactory.getLogger(LoaiPhongService.class);

    private final LoaiPhongRepo loaiPhongRepository;
    private final KhuNghiDuongRepo khuNghiDuongRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private final KhuNghiDuongService khuNghiDuongService;

    public LoaiPhongService(LoaiPhongRepo loaiPhongRepository, KhuNghiDuongService khuNghiDuongService,
            KhuNghiDuongRepo khuNghiDuongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
        this.khuNghiDuongService = khuNghiDuongService;
        this.khuNghiDuongRepository = khuNghiDuongRepository;
    }

    public LoaiPhong getLoaiPhongById(Long id) {
        logger.info("Starting getLoaiPhongById with id: {}", id);
        try {
            // Sử dụng JPQL để eager load KhuNghiDuong
            String jpql = "SELECT lp FROM LoaiPhong lp JOIN FETCH lp.khuNghiDuong WHERE lp.id = :id";
            Query query = entityManager.createQuery(jpql);
            query.setParameter("id", id);

            LoaiPhong loaiPhong = null;
            try {
                loaiPhong = (LoaiPhong) query.getSingleResult();
                logger.info("LoaiPhong found with JPQL: {}", loaiPhong);
                return loaiPhong;
            } catch (Exception e) {
                logger.warn("Error when using JPQL to fetch LoaiPhong: {}", e.getMessage());
                // Nếu có lỗi với JPQL, sử dụng cách thông thường
                logger.info("Falling back to regular findById");
            }

            // Nếu không có kết quả từ JPQL, sử dụng cách thông thường
            loaiPhong = loaiPhongRepository.findById(id).orElse(null);

            if (loaiPhong == null) {
                logger.warn("LoaiPhong not found with id: {}", id);
                return null;
            }

            // Nếu KhuNghiDuong là null, cần tải KhuNghiDuong theo ID từ cột FK
            if (loaiPhong.getKhuNghiDuong() == null) {
                logger.warn("KhuNghiDuong is null for LoaiPhong with id: {}, attempting to manually load", id);

                // Lấy ID của KhuNghiDuong từ database trực tiếp
                String nativeQuery = "SELECT ID_KHU_NGHI_DUONG FROM LOAI_PHONG WHERE ID = ?";
                Query nativeQ = entityManager.createNativeQuery(nativeQuery);
                nativeQ.setParameter(1, id);

                try {
                    Object kndId = nativeQ.getSingleResult();
                    if (kndId != null) {
                        Long khuNghiDuongId = ((Number) kndId).longValue();
                        logger.info("Found KhuNghiDuong ID: {} for LoaiPhong ID: {}", khuNghiDuongId, id);

                        // Lấy KhuNghiDuong từ ID
                        KhuNghiDuong khuNghiDuong = khuNghiDuongRepository.findById(khuNghiDuongId).orElse(null);
                        if (khuNghiDuong != null) {
                            loaiPhong.setKhuNghiDuong(khuNghiDuong);
                            logger.info("Successfully loaded KhuNghiDuong: {}", khuNghiDuong);
                        } else {
                            logger.warn("Could not find KhuNghiDuong with ID: {}", khuNghiDuongId);
                        }
                    } else {
                        logger.warn("KhuNghiDuong ID is null in database for LoaiPhong ID: {}", id);
                    }
                } catch (Exception e) {
                    logger.error("Error when loading KhuNghiDuong ID: {}", e.getMessage());
                }
            } else {
                logger.info("LoaiPhong has KhuNghiDuong with id: {}", loaiPhong.getKhuNghiDuong().getId());
            }

            return loaiPhong;
        } catch (Exception e) {
            logger.error("Error in getLoaiPhongById for id: {}", id, e);
            return null;
        }
    }

    public List<LoaiPhong> getRoomTypesByPartnerId(Long doiTacId) {
        List<KhuNghiDuong> khuNghiDuongList = khuNghiDuongService.getKhuNghiDuongsByDoiTacId(doiTacId);
        List<Long> khuNghiDuongIds = khuNghiDuongList.stream().map(KhuNghiDuong::getId).toList();
        return loaiPhongRepository.findByKhuNghiDuong_IdIn(khuNghiDuongIds);
    }

    @Transactional
    public LoaiPhong saveLoaiPhong(Long idKhuNghiDuong, String tenLoaiPhong, Double dienTich,
            String loaiPhongTheoSoLuong,
            String loaiPhongTheoTieuChuan, Integer soGiuong, Integer soNguoi, BigDecimal gia) {

        KhuNghiDuong khuNghiDuong = khuNghiDuongRepository.findById(idKhuNghiDuong)
                .orElseThrow(() -> new IllegalArgumentException("Khu Nghỉ Dưỡng không tồn tại"));

        LoaiPhong loaiPhong = new LoaiPhong();
        loaiPhong.setKhuNghiDuong(khuNghiDuong);
        loaiPhong.setTenLoaiPhong(tenLoaiPhong);
        loaiPhong.setDienTich(dienTich);
        loaiPhong.setLoaiPhongTheoSoLuong(loaiPhongTheoSoLuong);
        loaiPhong.setLoaiPhongTheoTieuChuan(loaiPhongTheoTieuChuan);
        loaiPhong.setSoGiuong(soGiuong);
        loaiPhong.setSoNguoi(soNguoi);
        loaiPhong.setGia(gia);

        return loaiPhongRepository.save(loaiPhong);
    }

    @Transactional
    public LoaiPhong updateLoaiPhong(Long id, Long idKhuNghiDuong, String tenLoaiPhong, Double dienTich,
            String loaiPhongTheoSoLuong,
            String loaiPhongTheoTieuChuan, Integer soGiuong, Integer soNguoi, BigDecimal gia) throws Exception {
        LoaiPhong loaiPhong = loaiPhongRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loại phòng không tồn tại"));
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Test Loai Phong: {}", loaiPhong);

        KhuNghiDuong khuNghiDuong = khuNghiDuongRepository.findById(idKhuNghiDuong)
                .orElseThrow(() -> new IllegalArgumentException("Khu Nghỉ Dưỡng không tồn tại"));

        logger.info("Test Loai khuNghiDuong: {}", khuNghiDuong);

        loaiPhong.setKhuNghiDuong(khuNghiDuong);
        loaiPhong.setTenLoaiPhong(tenLoaiPhong);
        loaiPhong.setDienTich(dienTich);
        loaiPhong.setLoaiPhongTheoSoLuong(loaiPhongTheoSoLuong);
        loaiPhong.setLoaiPhongTheoTieuChuan(loaiPhongTheoTieuChuan);
        loaiPhong.setSoGiuong(soGiuong);
        loaiPhong.setSoNguoi(soNguoi);
        loaiPhong.setGia(gia);

        return loaiPhongRepository.save(loaiPhong);
    }

    @Transactional
    public void deleteAllById(List<Long> ids) {
        loaiPhongRepository.deleteAllById(ids);
    }

    /**
     * Lấy danh sách loại phòng theo khu nghỉ dưỡng
     *
     * @param khuNghiDuongId ID của khu nghỉ dưỡng
     * @return Danh sách các loại phòng thuộc khu nghỉ dưỡng
     */
    public List<LoaiPhong> getLoaiPhongByKhuNghiDuongId(Long khuNghiDuongId) {
        return loaiPhongRepository.findByKhuNghiDuong_Id(khuNghiDuongId);
    }

    /**
     * Lấy danh sách loại phòng theo khu nghỉ dưỡng và số người
     *
     * @param khuNghiDuongId ID của khu nghỉ dưỡng
     * @param soNguoi        Số người tối thiểu của loại phòng
     * @return Danh sách các loại phòng phù hợp
     */
    public List<LoaiPhong> getLoaiPhongByResortIdAndSoNguoi(Long khuNghiDuongId, int soNguoi) {
        try {
            // Sử dụng JPQL để lấy các loại phòng phù hợp
            String jpql = "SELECT lp FROM LoaiPhong lp WHERE lp.khuNghiDuong.id = :resortId AND lp.soNguoi >= :soNguoi";
            Query query = entityManager.createQuery(jpql, LoaiPhong.class);
            query.setParameter("resortId", khuNghiDuongId);
            query.setParameter("soNguoi", soNguoi);

            return query.getResultList();
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách loại phòng theo resort và số người: {}", e.getMessage());
            return List.of(); // Trả về danh sách rỗng nếu có lỗi
        }
    }

    // Call PROCEDURE
    @Autowired
    private JdbcRoomType jdbcRoomType;

    public List<RoomTypeDetailsDTO> getRoomByResort(Long resortId, LocalDateTime checkIn, LocalDateTime checkOut,
            int soNguoi) {
        if (checkIn == null) {
            checkIn = LocalDateTime.now();
        }

        if (checkOut == null) {
            checkOut = checkIn.plusDays(1); // mac dinh checkout sau 1 ngay
        }

        return jdbcRoomType.getRoomByResort(resortId, checkIn, checkOut, soNguoi);
    }
}