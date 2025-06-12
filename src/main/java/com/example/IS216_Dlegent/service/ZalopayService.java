package com.example.IS216_Dlegent.service;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.config.ZalopayConfig;
import com.example.IS216_Dlegent.model.ChiTietDatPhong;
import com.example.IS216_Dlegent.model.DatPhong;
import com.example.IS216_Dlegent.model.Phong;
import com.example.IS216_Dlegent.repository.ChiTietDatPhongRepository;
import com.example.IS216_Dlegent.repository.DatPhongRepository;
import com.example.IS216_Dlegent.utils.HMACUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZalopayService {
    @Autowired
    DatPhongRepository datPhongRepository;

    @Autowired
    ChiTietDatPhongRepository chiTietDatPhongRepository;

    @Autowired
    PhongService phongService;

    Logger logger = LoggerFactory.getLogger(ZalopayService.class);

    private static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    public String createOrder(Map<String, Object> orderRequest, Long idDatphong) {
        Map<Boolean, String> check = kiemTraPhongTrong(orderRequest, idDatphong);
        
        if (!check.containsKey(true)) {
            return "{\"error\": \"" + check.get(false) + "\"}";
        }

        Random rand = new Random();
        int randomId = rand.nextInt(1000000);

        Object amount = orderRequest.get("amount");
        if (Integer.parseInt(amount.toString()) == 0) {
            return "{\"error\": \"Amount can't be zero\"}";
        }

        String ngrokPrefix = "https://a086-101-99-36-202.ngrok-free.app";
        String callback_url = ngrokPrefix + "/api/payment/" + idDatphong.toString();

        Map<String, Object> order = new HashMap<>();
        order.put("app_id", ZalopayConfig.config.get("app_id"));
        order.put("app_trans_id", getCurrentTimeString("yyMMdd") + "_" + randomId);
        order.put("app_time", System.currentTimeMillis());
        order.put("app_user", "user123");
        order.put("amount", amount);
        order.put("description", "SN Mobile - Payment for the order #" + randomId);
        order.put("bank_code", "");
        order.put("item", "[{}]");
        order.put("embed_data", "{}");
        order.put("callback_url", callback_url);

        System.out.println(order.get("callback_url"));
        String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|"
                + order.get("amount") + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|"
                + order.get("item");

        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZalopayConfig.config.get("key1"), data);
        order.put("mac", mac);

        System.out.println("Generated MAC: " + mac);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(ZalopayConfig.config.get("endpoint"));

            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> entry : order.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder resultJsonStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    resultJsonStr.append(line);
                }

                System.out.println("Zalopay Response: " + resultJsonStr.toString());

                return resultJsonStr.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to create order: " + e.getMessage() + "\"}";
        }
    }

    public String getOrderStatus(String appTransId) {
        String data = ZalopayConfig.config.get("app_id") + "|" + appTransId + "|" + ZalopayConfig.config.get("key1");
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZalopayConfig.config.get("key1"), data);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(ZalopayConfig.config.get("orderstatus"));

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("app_id", ZalopayConfig.config.get("app_id")));
            params.add(new BasicNameValuePair("app_trans_id", appTransId));
            params.add(new BasicNameValuePair("mac", mac));

            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder resultJsonStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    resultJsonStr.append(line);
                }

                return resultJsonStr.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to get order status: " + e.getMessage() + "\"}";
        }
    }

    public Map<Boolean, String> kiemTraPhongTrong(Map<String, Object> orderRequest, Long idDatphong) {
        // kiem tra neu con phong trong
        try {
            // Lấy thông tin đặt phòng theo ID
            DatPhong datPhong = datPhongRepository.findById(idDatphong).get();

            // Lấy danh sách chi tiết đặt phòng
            List<ChiTietDatPhong> chiTietList = chiTietDatPhongRepository.findByDatPhong_Id(datPhong.getId());

            // Kiểm tra từng phòng trong chi tiết đặt phòng
            for (ChiTietDatPhong chiTiet : chiTietList) {
                List<Phong> phongs = phongService.getPhongKhongBanTrongKhoangThoiGian(chiTiet.getNgayBatDau(),
                        chiTiet.getNgayKetThuc());
                phongs = phongs.stream()
                        .filter(p -> p.getTinhTrang().equals("Available")
                                && p.getLoaiPhong().getId().equals(chiTiet.getGoiDatPhong().getLoaiPhong().getId()))
                        .collect(Collectors.toList());
                if (phongs.isEmpty()) {
                    return Map.of(false, "Phòng " + chiTiet.getGoiDatPhong().getLoaiPhong().getTenLoaiPhong()
                            + " không còn");
                }
            }

            // Kiểm tra số lượng phòng còn trống có đủ với số lượng chi tiết đặt phòng của
            // cùng loại
            Map<Long, Integer> loaiPhongCount = new HashMap<>();
            Map<Long, Integer> availableRoomCount = new HashMap<>();

            // Đếm số lượng chi tiết đặt phòng cho mỗi loại phòng
            for (ChiTietDatPhong chiTiet : chiTietList) {
                Long loaiPhongId = chiTiet.getGoiDatPhong().getLoaiPhong().getId();
                loaiPhongCount.put(loaiPhongId, loaiPhongCount.getOrDefault(loaiPhongId, 0) + 1);
            }

            // Đếm số lượng phòng còn trống cho mỗi loại phòng
            for (ChiTietDatPhong chiTiet : chiTietList) {
                Long loaiPhongId = chiTiet.getGoiDatPhong().getLoaiPhong().getId();
                if (!availableRoomCount.containsKey(loaiPhongId)) {
                    List<Phong> phongs = phongService.getPhongKhongBanTrongKhoangThoiGian(chiTiet.getNgayBatDau(),
                            chiTiet.getNgayKetThuc());
                    phongs = phongs.stream()
                            .filter(p -> p.getTinhTrang().equals("Available")
                                    && p.getLoaiPhong().getId().equals(loaiPhongId))
                            .collect(Collectors.toList());
                    availableRoomCount.put(loaiPhongId, phongs.size());
                }
            }

            // Kiểm tra từng loại phòng
            for (Map.Entry<Long, Integer> entry : loaiPhongCount.entrySet()) {
                Long loaiPhongId = entry.getKey();
                Integer requiredCount = entry.getValue();
                Integer availableCount = availableRoomCount.get(loaiPhongId);

                if (availableCount < requiredCount) {
                    // Lấy tên loại phòng
                    String tenLoaiPhong = chiTietList.stream()
                            .filter(ct -> ct.getGoiDatPhong().getLoaiPhong().getId().equals(loaiPhongId))
                            .findFirst()
                            .get()
                            .getGoiDatPhong().getLoaiPhong().getTenLoaiPhong();

                    return Map.of(false, "Chỉ còn " + availableCount + " phòng " + tenLoaiPhong
                            + " trống, không đủ số lượng " + requiredCount + " phòng yêu cầu");
                }
            }
        } catch (Exception e) {
            return Map.of(false, "Lỗi khi kiểm tra tình trạng phòng: " + e.getMessage());
        }
        return Map.of(true, "Có đủ phòng");
    }
}
