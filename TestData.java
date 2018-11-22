package com.java.automation.Readers;

import com.codoid.products.fillo.Recordset;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestData {
    private String query = "Select * from TEST_DATA";
    private String testDataFile = "AdvancedTestData.xlsx";
    public HashMap<String, String> testDataCollection = new HashMap<>();
    final static Logger logger = Logger.getLogger(TestData.class);

    public void init(String testCaseName) {
        try {
            Recordset testData = ExcelReader.readExcelAsDB(testDataFile, query + " WHERE TC_NAME='" + testCaseName + "'");
            if (testData != null)
                loadTestData(testData);
            else {
                logger.info("Test Data is not available for " + testCaseName);
                throw new RuntimeException();
            }

        } catch (Exception ee) {
            ee.printStackTrace();
            throw new RuntimeException("No Test Data found for test case" + testCaseName);
        }
    }

    private void loadTestData(Recordset testData) {
        try {
            List<String> allData = testData.getFieldNames();
            testData.next();
            for (int i = 1; i < allData.size(); i++) {
                String currentColData = testData.getField(allData.get(i));
                if (currentColData != null && !currentColData.isEmpty()) {
                    testDataCollection.put(currentColData.split("<>")[0], currentColData.split("<>")[1]);
                }
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            logger.info("Exception while reading test data.");

        }
    }

    public String get(String dataKey) {
        return testDataCollection.get(dataKey);
    }

    public List<String> getList(String key) {
        String strData = get(key);

        String[] lst = strData.split(";");
        logger.info(strData);
        return Arrays.asList(lst);
    }
}
