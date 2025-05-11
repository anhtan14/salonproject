package com.example.salonmanage.excel;

import com.example.salonmanage.DTO.BookingHistoryDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class BookingExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BookingHistoryDTO> list;

    public BookingExcelExporter(List<BookingHistoryDTO> list) {
        this.list = list;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Đơn hàng");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Ngày", style);
        createCell(row, 2, "Giảm giá", style);
        createCell(row, 3, "Trạng thái", style);
        createCell(row, 4, "Trạng thái thanh toán", style);
        createCell(row, 5, "Thời gian", style);
        createCell(row, 6, "Tổng tiền", style);
        createCell(row, 7, "Tên nhân viên", style);
        createCell(row, 8, "Số điện thoại người dùng", style);
        createCell(row, 9, "Chi nhánh", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (BookingHistoryDTO user : list) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getDate(), style);
            createCell(row, columnCount++, user.getDiscount(), style);
            if (user.getStatus() == 0) {
                createCell(row, columnCount++, "Đang chờ", style);
            } else if (user.getStatus() == 1) {
                createCell(row, columnCount++, "Hoàn thành", style);
            } else if (user.getStatus() == 2) {
                createCell(row, columnCount++, "Đã xác nhận", style);
            } else if (user.getStatus() == 3) {
                createCell(row, columnCount++, "Hủy", style);
            }else {
                createCell(row, columnCount++, "", style);
            }
            if (user.getPayment() == 0) {
                createCell(row, columnCount++, "Chưa thanh toán", style);
            } else if (user.getStatus() == 1) {
                createCell(row, columnCount++, "Đã thanh toán", style);
            }else {
                createCell(row, columnCount++, "", style);
            }
            if (user.getTime() != null) {
                createCell(row, columnCount++, user.getTime().getTimes(), style);
            }else{
                createCell(row, columnCount++, "", style);
            }
            createCell(row, columnCount++, user.getTotalPrice(), style);
            if (user.getNhanvienName() != null) {
                createCell(row, columnCount++, user.getNhanvienName(), style);
            }else{
                createCell(row, columnCount++, "", style);
            }
            if (user.getUser() != null) {
                createCell(row, columnCount++, user.getUser().getPhone(), style);
            }else{
                createCell(row, columnCount++, "", style);
            }
            createCell(row, columnCount++, user.getBranch().getAddress(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
