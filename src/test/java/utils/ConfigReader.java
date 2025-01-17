package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE = "configuration.properties";

    static {
        try {
            String path = System.getProperty("user.dir") + "/" + CONFIG_FILE;
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("configuration.properties dosyası bulunamadı! " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException(key + " configuration.properties dosyasında bulunamadı!");
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }

    public static int getInt(String key) {
        String value = getProperty(key);
        return Integer.parseInt(value);
    }

    public static long getLong(String key) {
        String value = getProperty(key);
        return Long.parseLong(value);
    }
}