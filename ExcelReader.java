package com.java.automation.Readers;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.java.automation.Generic_Functions.ExecutionHelper;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelReader {
    final static Logger log4jLogger = Logger.getLogger(ExcelReader.class);
    private static Workbook readExcel(String excelFileName) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(ExecutionHelper.getInputFilesPath() + excelFileName));
            return new XSSFWorkbook(in);
        } catch (Exception ee) {
            ee.printStackTrace();
            return null;
        }
    }

    public static Sheet readExcelFileAndGetSheet(String excelFilePath, String sheetName) {
        Workbook workbook = readExcel(excelFilePath);
        int noOfSheets = workbook.getNumberOfSheets();
        int i = 0;
        while (!workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName) && i < noOfSheets) {
            log4jLogger.warn("Sheet name is " + workbook.getSheetAt(i).getSheetName());
            i++;
        }
        return workbook.getSheetAt(i);
    }

    public static Recordset readExcelAsDB(String excelFilePath, String query) {
        Fillo fillo=new Fillo();
        Connection connection= null;
        try {
            connection = fillo.getConnection(ExecutionHelper.getInputFilesPath() + excelFilePath);
            return connection.executeQuery(query);
        } catch (FilloException e) {
            log4jLogger.info("No Test Data found for current test case");
            return null;
        }
    }

}
