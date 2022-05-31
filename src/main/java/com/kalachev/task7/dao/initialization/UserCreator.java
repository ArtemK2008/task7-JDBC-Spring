package com.kalachev.task7.dao.initialization;

import java.sql.Connection;
import java.sql.Statement;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionMaker;
import com.kalachev.task7.utilities.JdbcCloser;

public class UserCreator {

  public void createUser() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionMaker.getDbConnection();
      statement = connection.createStatement();
      String createUser = "DROP USER IF EXISTS kalachevartemsql;"
          + "CREATE USER kalachevartemsql WITH  PASSWORD '1234';"
          + " GRANT ALL PRIVILEGES ON DATABASE comkalachevtasksqljdbc TO kalachevartemsql;";
      statement.execute(createUser);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(statement, connection);
    }
  }

}
