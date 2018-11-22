package com.java.automation.Generic_Functions;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.java.automation.Readers.ExcelReader;
import com.java.automation.WebElementUtils.ElementHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.HashMap;

public class ObjectRepoHandler {
    final static Logger logger = Logger.getLogger(ObjectRepoHandler.class);
    private static HashMap<String, By> oRCollection = new HashMap<>();
    private static String query = "Select OBJECT_NAME, IDENTIFIER, VALUE from OBJECT_REPOSITORY";
    private static String orFile = "OR.xlsx";

    public static void init() {
        try {
            Recordset orData = ExcelReader.readExcelAsDB(orFile, query);
            loadObjects(orData);
        } catch (FilloException e) {
            e.printStackTrace();
        }
    }

    public static By getElement(String elementName) {
        return oRCollection.get(elementName);
    }

    private static void loadObjects(Recordset orData) throws FilloException {
        while (orData.next()) {
            oRCollection.put(orData.getField("OBJECT_NAME").trim(),
                    ElementHelper.formIdentifier(orData.getField("IDENTIFIER"), orData.getField("VALUE")));
        }
        logger.info("Object repository loaded!!");
    }

    public static void flushOR() {
        logger.info("Flushing Object Repository");
        oRCollection = null;
    }

    public static void addObject(String OBJECT_NAME, By IDENTIFIER) {
        oRCollection.put(OBJECT_NAME, IDENTIFIER);
    }
}
