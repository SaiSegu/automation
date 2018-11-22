package com.java.automation.Reporter;


import com.java.automation.Generic_Functions.ExecutionHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reporter {

    public static ExtentReports extent;
    final static Logger log4jLogger = Logger.getLogger(Reporter.class);

    public static String currentExecFolder = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss").format(Calendar.getInstance().getTime());

    private static String reportLocation = ExecutionHelper.currentExecDir() + "/target/" + currentExecFolder + "/testcase_" + ExecutionHelper.getDateTimeStamp() + ".html";

    public static String getReportLocation() {
        return reportLocation;
    }

    public static void startReporter(String name) {
        reportLocation = reportLocation.replace("testcase", name);
        log4jLogger.info("Reporter is started at Location:" + reportLocation);
        extent = new ExtentReports(reportLocation, true);
    }

    public static void endReport() {
        extent.flush();
        extent.close();
        log4jLogger.info("Execution is Completed:" + reportLocation);
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome \"" + reportLocation + "\""});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExtentTest startTestCase(String testName) {
        log4jLogger.info("Test Case execution started:" + testName);
        return extent.startTest(testName);
    }

    public static void logEvent(ExtentTest logger, LogStatus logStatus, String stepName, String details, String screenShotPath) {
        if (logStatus != LogStatus.PASS) {
            logger.log(logStatus, stepName + "\n:" + details, logger.addScreenCapture(new File(screenShotPath).getAbsolutePath()));
        } else {
            logger.log(logStatus, stepName, details);
        }
    }

    public static void logEventWithoutDescription(ExtentTest logger, LogStatus logStatus, String stepName, String screenShotPath) {
        logger.log(logStatus, stepName, logger.addScreenCapture(new File(screenShotPath).getAbsolutePath()));
    }

    public static void endTestCase(ExtentTest logger) {
        extent.endTest(logger);
    }

    public static void endParentTestCase(ExtentTest parentTest) {
        extent.endTest(parentTest);
    }

    public static void appendTestCase(ExtentTest parentTest, ExtentTest logger) {
        parentTest.appendChild(logger);
        logger = null;
    }

    public static ExtentTest startParentTest(String testDescription) {
        return extent.startTest(testDescription);

    }

    public static void logEvent(ExtentTest logger, LogStatus logStatus, String stepName, String details) {
        logger.log(logStatus, stepName, details);
    }
}