package com.example.IS216_Dlegent.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.IS216_Dlegent.payload.dto.HoaDonDTO;

@Service
public class ExcelExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public ByteArrayInputStream exportHoaDonToExcel(List<HoaDonDTO> hoaDons) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Hóa Đơn");
            
            // Create style for header cells
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã Hóa Đơn", "Khách Hàng", "Resort", "Thành Tiền", "Ngày Đặt"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Create data rows
            int rowIdx = 1;
            for (HoaDonDTO hoaDon : hoaDons) {
                Row row = sheet.createRow(rowIdx++);
                
                row.createCell(0).setCellValue(hoaDon.getMaHoaDon());
                row.createCell(1).setCellValue(hoaDon.getTenKhachHang());
                row.createCell(2).setCellValue(hoaDon.getTenResort());
                row.createCell(3).setCellValue(formatCurrency(hoaDon.getThanhTien()));
                row.createCell(4).setCellValue(hoaDon.getNgayDatFormatted());
            }
            
            // Resize columns to fit content
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
    
    private String formatCurrency(BigDecimal amount) {
        if (amount == null) return "";
        return CURRENCY_FORMATTER.format(amount);
    }
} 