package com.test;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

/**
 * reader implemented by poi
 *
 * @author walter
 * @since 1.0
 */
public class PoiReader {
    public static void main(String[] args) throws URISyntaxException, IOException, InvalidFormatException {
        File file = openFile("people.xlsx");
        Sheet sheet;
        try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            sheet = workbook.sheetIterator().next();
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String str = formatRow(row);
                System.out.println(str);
            }
            long total = Runtime.getRuntime().totalMemory() / (1024 * 1024);
            long max = Runtime.getRuntime().maxMemory() / (1024 * 1024);
            System.out.println("total:" + total + " max:" + max);
        }
    }

    private static String formatRow(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        StringBuilder builder = new StringBuilder();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            builder.append(formatCell(cell));
        }
        return builder.toString();
    }

    private static String formatCell(Cell cell) {
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue() + "";
            default:
                return "";
        }
    }

    private static File openFile(String srcFile) throws URISyntaxException {
        URL resource = MiniReader.class.getClassLoader().getResource("people.xlsx");
        return new File(resource.toURI());
    }
}
