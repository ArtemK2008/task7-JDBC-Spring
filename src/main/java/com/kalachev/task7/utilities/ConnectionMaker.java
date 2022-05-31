package com.kalachev.task7.utilities;

import java.sql.Connection;
import java.sql.DriverManager;

import com.kalachev.task7.exceptions.DaoException;

public class ConnectionMaker {

  private static final String URL = "jdbc:postgresql://localhost/";
  private static final String USERNAME = "postgres";
  private static final String PASSWORD = "2487";

  private static final String URL_CREATED = "jdbc:postgresql://localhost/comkalachevtasksqljdbc";
  private static final String USERNAME_CREATED = "kalachevartemsql";
  private static final String PASSWORD_CREATED = "1234";

  private ConnectionMaker() {
    super();
  }

  public static Connection getDbConnection() throws DaoException {
    Connection connection = null;
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    }
  }

  public static Connection getDbConnectionForNewUser() throws DaoException {
    Connection connection = null;
    try {
      DriverManager.registerDriver(new org.postgresql.Driver());
      connection = DriverManager.getConnection(URL_CREATED, USERNAME_CREATED,
          PASSWORD_CREATED);
      return connection;
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    }
  }

}
