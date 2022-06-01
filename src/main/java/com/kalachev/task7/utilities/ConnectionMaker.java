package com.kalachev.task7.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.kalachev.task7.exceptions.DaoException;

public class ConnectionMaker {

  static String dbUrl;
  static String dbUsername;
  static String dbPassword;

  private ConnectionMaker() {
    super();
  }

  public static Connection getDbConnection() throws DaoException {
    Properties properties = new Properties();
    URL url = ClassLoader.getSystemResource("DbProperties");
    if (url != null) {
      try (InputStream is = url.openStream()) {
        properties.load(is);
        dbUrl = (String) properties.get("URL_ORIGINAL");
        dbUsername = (String) properties.get("USERNAME_ORIGINAL");
        dbPassword = (String) properties.get("PASSWORD_ORIGINAL");
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
      throw new DaoException();
    }
  }

  public static Connection getDbConnectionForNewUser() throws DaoException {
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
      throw new DaoException();
    }
  }

}
