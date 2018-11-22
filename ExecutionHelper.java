package com.java.automation.Generic_Functions;


import com.java.automation.Readers.PropertiesReader;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionHelper {
    final static Logger logger = Logger.getLogger(ExecutionHelper.class);

    public static String currentExecDir() {
        return System.getProperty("user.dir");
    }

    public static String getInputFilesPath() {
        return currentExecDir() + PropertiesReader.getProperty("INPUT_FILES_PATH");
    }

    public static String getResourcesPath() {
        return currentExecDir() + "/src/test/resources";
    }
    public static String getDateTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date).toString().replace(" ", "").replace(":", "").replace("/", "");
    }

}
