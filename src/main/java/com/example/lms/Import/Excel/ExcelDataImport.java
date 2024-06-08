package com.example.lms.Import.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.example.lms.DataBase.DataHelper;
import com.example.lms.Datas.Book;
import com.example.lms.Session.SessionManager;
import org.apache.poi.ss.usermodel.*;

public class ExcelDataImport {
    public void excelImport(File excelFilePath) {
        try (FileInputStream excelFile = new FileInputStream(excelFilePath)) {
            Workbook workbook = WorkbookFactory.create(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            int rowCount = sheet.getLastRowNum();
            for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if(row != null){
                    String bookTitle = getCellStringValue(row.getCell(0));
                    String author = getCellStringValue(row.getCell(1));
                    String publisher = getCellStringValue(row.getCell(2));
                    String page = getCellStringValue(row.getCell(3));
                    String categories = getCellStringValue(row.getCell(4));

                    DataHelper.insertBook(new Book(capitalizeWords(bookTitle),capitalizeWords(author),publisher.toUpperCase(),page,capitalizeWords(categories),fetchUserId()));
                }
            }
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public static String capitalizeWords(String str){
        if(str == null || str.isEmpty()){
            return str;
        }

        String[] words = str.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();

        for(String word : words){
            if(word.length() > 0){
                stringBuilder.append(Character.toUpperCase(word.charAt(0)))
                             .append(word.substring(1).toLowerCase())
                             .append(" ");
            }
        }
        return stringBuilder.toString().trim();
    }

    private int fetchUserId() {
        return SessionManager.getInstance().getLoggedInUserId();
    }
}
