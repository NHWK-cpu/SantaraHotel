package com.mycompany.santarahoteladmin;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Class untuk mengekspor data dari TableView JavaFX ke file Excel (XLSX)
 */

public class ExcelExporter {

    /**
     * Mengekspor data dari TableView ke file Excel
     * @param tableView TableView yang akan diekspor datanya
     * @param fileName Nama file output Excel (.xlsx)
     */
    public static <T> void exportTableViewToExcel(TableView<T> tableView, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // Membuat workbook Excel baru
        Sheet sheet = workbook.createSheet("Data"); // Membuat sheet baru dalam workbook

        ObservableList<T> items = tableView.getItems(); // Mendapatkan data dan kolom dari TableView
        List<?> columns = tableView.getColumns();

        // Membuat header (baris pertama)
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            String header = tableView.getColumns().get(i).getText();
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header);
        }

        // Membuat baris-baris data
        for (int i = 0; i < items.size(); i++) {
            Row row = sheet.createRow(i + 1); // Membuat baris baru di Excel (dimulai dari baris 1 karena baris 0 sudah ada header)
            T item = items.get(i); // Mengambil item data dari TableView

            // Mengisi data untuk setiap kolom
            for (int j = 0; j < columns.size(); j++) {
                String propertyName = tableView.getColumns().get(j).getId(); // Mengambil nama property dari ID kolom (asumsi format: "getPropertyName")
                if (propertyName == null) continue;

                try {
                    // Menggunakan reflection untuk memanggil getter dari object
                    Method getter = item.getClass().getMethod("get" + capitalize(propertyName).substring(3));
                    
                    Object value = getter.invoke(item);
                    
                    // Membuat cell dan mengisi nilainya
                    Cell cell = row.createCell(j);
                    cell.setCellValue(value != null ? value.toString() : "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Menulis workbook ke file
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    /**
     * Mengubah huruf pertama string menjadi kapital
     * @param str String yang akan diubah
     * @return String dengan huruf pertama kapital
     */
    private static String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
