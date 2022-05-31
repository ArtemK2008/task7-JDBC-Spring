package com.kalachev.task7.initialization;

import java.sql.Connection;
import java.sql.Statement;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionMaker;
import com.kalachev.task7.utilities.JdbcCloser;

public class DatabaseCreator {

  public void createDatabase() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionMaker.getDbConnection();
      statement = connection.createStatement();
      String dropIfExist = "DROP DATABASE IF EXISTS comkalachevtasksqljdbc;";
      statement.execute(dropIfExist);
      String createDb = "CREATE DATABASE comkalachevtasksqljdbc";
      statement.execute(createDb);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(statement, connection);
    }
  }

}
