package com.kalachev.task7.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {

  static String dbUrl;
  static String dbUsername;
  static String dbPassword;

  private ConnectionManager() {
    super();
  }

  public static Connection openDbConnection() {
    Properties properties = new Properties();
    URL url = ClassLoader.getSystemResource("DbProperties");
    if (url != null) {
      try (InputStream is = url.openStream()) {
        properties.load(is);
        dbUrl = (String) properties.get("URL");
        dbUsername = (String) properties.get("NAME");
        dbPassword = (String) properties.get("PASSWORD");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Connection connection = null;
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
      connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    } catch (Exception e) {
      System.out.println("Error while opening Connection");
    }
    return connection;
  }

}
