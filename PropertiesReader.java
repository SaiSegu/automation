package com.java.automation.Readers;

import com.java.automation.Generic_Functions.ExecutionHelper;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class PropertiesReader {
    private static Properties properties = new Properties();
    final static Logger log4jLogger = Logger.getLogger(PropertiesReader.class);
    public static void init() {
        if (System.getProperty("env") == null) {
            System.setProperty("env", "US-Celluler.env.txt");
        }
        InputStream in = null;
        try {
            in = new FileInputStream(ExecutionHelper.getResourcesPath() + "/Environments/" + System.getProperty("env"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            in = null;
        }
        log4jLogger.info("Read all properties from file");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void flush() {
        properties = null;
    }

    private static class LazyHolder {
        private static final PropertiesReader INSTANCE = new PropertiesReader();
    }

    public static PropertiesReader getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return properties.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}