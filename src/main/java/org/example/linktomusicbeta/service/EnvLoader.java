package org.example.linktomusicbeta.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvLoader {
    private static Properties properties = new Properties();

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(".env"); // 루트 폴더에 있는 .env 읽기
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
