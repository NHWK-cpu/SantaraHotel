package com.mycompany.santarahoteladmin;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class ExcelExporter {

    public static <T> void exportTableViewToExcel(TableView<T> tableView, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        ObservableList<T> items = tableView.getItems();
        List<?> columns = tableView.getColumns();

        // Header
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            String header = tableView.getColumns().get(i).getText();
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(header);
        }

        // Data rows
        for (int i = 0; i < items.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T item = items.get(i);

            for (int j = 0; j < columns.size(); j++) {
                String propertyName = tableView.getColumns().get(j).getId(); // assuming you set .setId("fieldName") on each column
                if (propertyName == null) continue;

                try {
                    Method getter = item.getClass().getMethod("get" + capitalize(propertyName).substring(3));
                    Object value = getter.invoke(item);
                    Cell cell = row.createCell(j);
                    cell.setCellValue(value != null ? value.toString() : "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
