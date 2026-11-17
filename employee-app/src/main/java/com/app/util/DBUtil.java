package com.app.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final Properties props = new Properties();

    static {
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties not found on classpath");
            props.load(in);
            Class.forName(get("driver"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static String get(String key) {
        String v = props.getProperty(key);
        if (v == null) throw new IllegalStateException("Missing key in db.properties: " + key);
        return v.replaceAll("[\\s\\u00A0]+$", "").replaceAll("^[\\s\\u00A0]+", "");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(get("url"), get("user"), get("password"));
    }
}