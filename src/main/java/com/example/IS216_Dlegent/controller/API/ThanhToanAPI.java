package com.example.IS216_Dlegent.controller.API;

import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.IS216_Dlegent.model.HoaDon;
import com.example.IS216_Dlegent.payload.respsonse.HoaDonResponse;
import com.example.IS216_Dlegent.service.DatPhongService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/payment")
public class ThanhToanAPI {

    @Autowired
    private DatPhongService datPhongService;
    
    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/{datPhongId}")
    public ResponseEntity<?> capNhatTrangThaiVaTaoHoaDon(@PathVariable Long datPhongId) {
        String trangThai = "Đã thanh toán";
        String hinhThucThanhToan = "zalopay";
        List<HoaDon> hoaDons = datPhongService.capNhatTrangThaiVaTaoHoaDon(datPhongId, trangThai, hinhThucThanhToan);
        List<HoaDonResponse> dtos = hoaDons.stream()
                                .map(HoaDonResponse::new)
                                .collect(Collectors.toList());

        try {
            // Tạo nội dung email với thông tin chi tiết
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<html><body style='font-family: Arial, sans-serif;'>");
            emailContent.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>");
            emailContent.append("<h2 style='color: #2c3e50;'>Xác nhận đặt phòng thành công</h2>");
            emailContent.append("<p>Kính gửi <strong>").append(dtos.get(0).getTenKhachHang()).append("</strong>,</p>");
            emailContent.append("<p>Cảm ơn bạn đã đặt phòng tại DLEGENT. Dưới đây là thông tin chi tiết về đặt phòng của bạn:</p>");
            
            for (int i = 0; i < dtos.size(); i++) {
                HoaDonResponse hoaDonResponse = dtos.get(i);
                HoaDon hoaDon = hoaDons.get(i);
                String tenResort = hoaDon.getChiTietDatPhong().getGoiDatPhong().getLoaiPhong().getKhuNghiDuong().getTen();
                
                emailContent.append("<div style='background-color: #f8f9fa; padding: 15px; margin: 10px 0; border-radius: 5px;'>");
                emailContent.append("<p><strong>Mã hóa đơn:</strong> ").append(hoaDonResponse.getId()).append("</p>");
                emailContent.append("<p><strong>Mã số thuế: 5958219421</strong> ").append("</p>");
                emailContent.append("<p><strong>Resort:</strong> ").append(tenResort).append("</p>");
                emailContent.append("<p><strong>Tổng tiền:</strong> ").append(hoaDonResponse.getTongGiaTien()).append(" VND</p>");
                emailContent.append("<p><strong>Thời gian thanh toán:</strong> ").append(hoaDonResponse.getThoiGianThanhToan().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("</p>");
                emailContent.append("<p><strong>Hình thức thanh toán:</strong> ").append(hoaDonResponse.getHinhThucThanhToan()).append("</p>");
                emailContent.append("</div>");
            }
            
            emailContent.append("<p style='margin-top: 20px;'>Chúc bạn có một kỳ nghỉ tuyệt vời!</p>");
            emailContent.append("<p>Trân trọng,<br>DLEGENT Team</p>");
            emailContent.append("</div></body></html>");

            // Tạo và gửi email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom("nthanhthangclone001@gmail.com");
            helper.setTo("zero2272005@gmail.com");
            helper.setSubject("DLEGENT - Xác nhận đặt phòng thành công");
            helper.setText(emailContent.toString(), true); // true = gửi HTML
            
            mailSender.send(message);
            
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Lỗi khi gửi email: " + e.getMessage());
        }

        return ResponseEntity.ok(dtos);
    }
}
