package com.kalachev.task7.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.kalachev.task7.exceptions.DaoException;

public class ConnectionManager {

  static String dbUrl;
  static String dbUsername;
  static String dbPassword;

  private ConnectionManager() {
    super();
  }

  public static Connection openDbConnection() throws DaoException {
    Properties properties = new Properties();
    URL url = ClassLoader.getSystemResource("DbProperties");
    if (url != null) {
      try (InputStream is = url.openStream()) {
        properties.load(is);
        dbUrl = (String) properties.get("URL_CREATED");
        dbUsername = (String) properties.get("USERNAME_CREATED");
        dbPassword = (String) properties.get("PASSWORD_CREATED");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    Connection connection = null;
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
      connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    }
  }

}
