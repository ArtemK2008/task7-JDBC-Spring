package com.kalachev.task7.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

  private JdbcUtil() {
    super();
  }

  public static void closeAll(Statement statement, Connection connection) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        System.out.println("Error while closing Statement");
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.out.println("Error while closing Connection");
      }
    }
  }

  public static void closeAll(ResultSet rs, Statement statement,
      Connection connection) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        System.out.println("Error while closing Result Set");
      }
    }
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        System.out.println("Error while closing Statement");
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.out.println("Error while closing Connection");
      }
    }
  }

}
