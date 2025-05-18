CREATE OR REPLACE PROCEDURE TimResortVaPhong (
    p_tu_khoa   IN  VARCHAR2,
    p_ngay_nhan IN  TIMESTAMP,
    p_ngay_tra  IN  TIMESTAMP,
    p_so_nguoi  IN  NUMBER,
    p_output    OUT SYS_REFCURSOR
)
IS
BEGIN
    OPEN p_output FOR
        SELECT
            knd.ID AS id,
            knd.ten AS ten_resort,
            knd.dia_chi || ', ' || p_ph.ten_phuong || ', ' ||
            p_quan.ten_quan || ', ' || p_tp.ten_thanh_pho           AS dia_chi,
            MIN(gdp.tong_giatien)                                   AS gia_thap_nhat,
            knd.img_360_url,
            knd.danh_gia,
            COUNT(DISTINCT dg.ID) AS so_luong_danh_gia,
          ( CASE WHEN REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(p_ph.ten_phuong))) THEN 1 ELSE 0 END +
            CASE WHEN REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(p_quan.ten_quan))) THEN 1 ELSE 0 END +
            CASE WHEN REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(p_tp.ten_thanh_pho))) THEN 1 ELSE 0 END +
            CASE WHEN REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(knd.ten)))        THEN 1 ELSE 0 END +
            CASE WHEN REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(knd.dia_chi)))    THEN 1 ELSE 0 END
          )                                                         AS relevance_score

        FROM   khu_nghi_duong          knd
        JOIN   phuong                  p_ph   ON knd.phuong_id       = p_ph.id
        JOIN   quan                    p_quan ON p_ph.id_quan        = p_quan.id
        JOIN   thanh_pho               p_tp   ON p_quan.id_thanh_pho = p_tp.id
        JOIN   phong                   p      ON p.id_khu_nghi_duong = knd.id
        JOIN   loai_phong              lp     ON lp.id               = p.id_loai_phong
        LEFT   JOIN goi_dat_phong      gdp    ON gdp.id_loai_phong   = lp.id
        LEFT JOIN   danh_gia                dg     ON dg.ID_KHU_NGHI_DUONG= knd.id
        WHERE  (   REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(p_ph.ten_phuong)))
                OR REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(p_quan.ten_quan)))
                OR REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(p_tp.ten_thanh_pho)))
                OR REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(knd.ten)))
                OR REGEXP_LIKE(TRIM(LOWER(p_tu_khoa)), TRIM(LOWER(knd.dia_chi))) )
          AND  p.TRANG_THAI = 'Available'
          AND  lp.so_nguoi  >= p_so_nguoi
          AND  NOT EXISTS (
                 SELECT 1
                 FROM   thoigian_phong_ban tpb
                 WHERE  tpb.id_phong = p.id
                   AND  tpb.ngay_bat_dau < p_ngay_tra
                   AND  tpb.ngay_ket_thuc > p_ngay_nhan )
        GROUP BY
              knd.id,
              knd.ten, knd.dia_chi,
              p_ph.ten_phuong, p_quan.ten_quan, p_tp.ten_thanh_pho,
              knd.img_360_url, knd.danh_gia
        ORDER BY relevance_score DESC;
END;
/

--Procedure tim goi dat phong
CREATE OR REPLACE PROCEDURE TimLoaiPhong (
    p_resortId IN NUMBER,
    p_ngay_nhan IN  TIMESTAMP,
    p_ngay_tra  IN  TIMESTAMP,
    p_so_nguoi  IN  NUMBER,
    p_output    OUT SYS_REFCURSOR
)
IS
BEGIN
  OPEN p_output FOR 
    SELECT lp.ID,
           lp.ID_KHU_NGHI_DUONG,
           lp.TEN_LOAI_PHONG,
           lp.DIEN_TICH,
           lp.LOAI_PHONG_THEO_SO_LUONG,
           lp.LOAI_PHONG_THEO_TIEU_CHUAN,
           lp.SO_GIUONG,
           lp.SO_NGUOI,
           lp.GIA
    FROM LOAI_PHONG lp 
    JOIN PHONG p ON p.ID_LOAI_PHONG = lp.ID
    WHERE lp.ID_KHU_NGHI_DUONG = p_resortId
    AND p.TRANG_THAI = 'Available'
    AND NOT EXISTS (
           SELECT 1
           FROM   thoigian_phong_ban tpb
           WHERE  tpb.id_phong = p.id
             AND  tpb.ngay_bat_dau < p_ngay_tra
             AND  tpb.ngay_ket_thuc > p_ngay_nhan);
END; 
      
--FUCNTION t�nh d�nh gi� trung b�nh resort
CREATE OR REPLACE FUNCTION tinhDanhGiaTBResort(
  p_resortId IN NUMBER
) RETURN NUMBER
IS 
  v_avg_rating NUMBER;
BEGIN 

  SELECT NVL(AVG(diem), 0)
  INTO v_avg_rating
  FROM DANH_GIA
  WHERE ID_KHU_NGHI_DUONG = p_resortId;
  
  RETURN v_avg_rating;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN 0;
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('L?i khi t�nh d�nh gi�: ' || SQLERRM);
    RETURN 0;
END;
/

