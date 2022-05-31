package com.kalachev.task7.utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.kalachev.task7.exceptions.DaoException;

public class JdbcCloser {

  private JdbcCloser() {
    super();
  }

  public static void closeAll(Statement statement, Connection connection)
      throws DaoException {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
  }

  public static void closeAll(ResultSet rs, Statement statement,
      Connection connection) throws DaoException {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
        throw new DaoException();
      }
    }
  }

}
