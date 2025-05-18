--TimResort
DECLARE
    v_cursor          SYS_REFCURSOR;

    v_id              NUMBER;
    v_ten_resort      NVARCHAR2(255);
    v_dia_chi         NVARCHAR2(500);
    v_gia_thap_nhat   NUMBER;
    v_dich_vu_mac_dinh NVARCHAR2(1000);   
    v_relevance_score NUMBER;
    v_img_url         NVARCHAR2(500);
    v_danh_gia        NUMBER;
    v_so_luong_danh_gia NUMBER;
BEGIN
    TimResortVaPhong(
        p_tu_khoa   => 'quận 1',
        p_ngay_nhan => TO_TIMESTAMP('2025-01-01 14:00:00'
                                    ,'YYYY-MM-DD HH24:MI:SS'),
        p_ngay_tra  => TO_TIMESTAMP('2025-01-03 12:00:00'
                                    ,'YYYY-MM-DD HH24:MI:SS'),
        p_so_nguoi  => 2,
        p_output    => v_cursor
    );

    DBMS_OUTPUT.PUT_LINE('@@@@@@@@ K?T QU? @@@@@@@@');

    LOOP
        FETCH v_cursor INTO v_id,
                           v_ten_resort,
                           v_dia_chi,
                           v_gia_thap_nhat,
                           --v_dich_vu_mac_dinh,
                           v_img_url,
                           v_danh_gia,
                           V_SO_LUONG_DANH_GIA,
                           v_relevance_score;
        EXIT WHEN v_cursor%NOTFOUND;

        /* --- hi?n th? --- */
        DBMS_OUTPUT.PUT_LINE('Tên Resort : '||v_ten_resort);
        DBMS_OUTPUT.PUT_LINE('Ð?a ch?    : '||v_dia_chi);
        DBMS_OUTPUT.PUT_LINE('Giá th?p nh?t: '||TO_CHAR(v_gia_thap_nhat,'999G999G999')
                            ||' VND');
        DBMS_OUTPUT.PUT_LINE('D?ch v? m?c d?nh: '||
                             NVL(v_dich_vu_mac_dinh,'(Không có)'));
        DBMS_OUTPUT.PUT_LINE('?nh        : '||v_img_url);
        DBMS_OUTPUT.PUT_LINE('Ðánh giá   : '||NVL(TO_CHAR(v_danh_gia,'990D0'),'-'));
        DBMS_OUTPUT.PUT_LINE('Relevance  : '||v_relevance_score);
        DBMS_OUTPUT.PUT_LINE('Relevance  : '||V_SO_LUONG_DANH_GIA);
        DBMS_OUTPUT.PUT_LINE('---------------------------------------');
    END LOOP;

    CLOSE v_cursor;
END;
/

--TimLoaiPhong
DECLARE
    v_output SYS_REFCURSOR;
    v_id LOAI_PHONG.ID%TYPE;
    v_ten LOAI_PHONG.TEN_LOAI_PHONG%TYPE;
    v_dien_tich LOAI_PHONG.DIEN_TICH%TYPE;
    v_so_luong LOAI_PHONG.LOAI_PHONG_THEO_SO_LUONG%TYPE;
    v_tieu_chuan LOAI_PHONG.LOAI_PHONG_THEO_TIEU_CHUAN%TYPE;
    v_so_giuong LOAI_PHONG.SO_GIUONG%TYPE;
    v_so_nguoi LOAI_PHONG.SO_NGUOI%TYPE;
    v_gia LOAI_PHONG.GIA%TYPE;
BEGIN
    TimLoaiPhong(
        p_resortId   => 16,
        p_ngay_nhan  => TO_TIMESTAMP('2025-06-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        p_ngay_tra   => TO_TIMESTAMP('2025-06-02 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        p_so_nguoi   => 1,
        p_output     => v_output
    );

    LOOP
        FETCH v_output INTO 
            v_id, v_ten, v_dien_tich, v_so_luong, v_tieu_chuan, v_so_giuong, v_so_nguoi, v_gia;
        EXIT WHEN v_output%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_id || ', Tên: ' || v_ten || ', Di?n tích: ' || v_dien_tich || ', Giá: ' || v_gia);
    END LOOP;

    CLOSE v_output;
END;
