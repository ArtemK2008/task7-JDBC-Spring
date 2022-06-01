package com.kalachev.task7.dao.initialization;

import java.sql.Connection;
import java.sql.Statement;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class UserInitializer {

  public void createUser() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.createStatement();
      String createUser = "DROP USER IF EXISTS kalachevartemsql;"
          + "CREATE USER kalachevartemsql WITH  PASSWORD '1234';"
          + " GRANT ALL PRIVILEGES ON DATABASE comkalachevtasksqljdbc TO kalachevartemsql;";
      statement.execute(createUser);
    } catch (Exception e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }

}
