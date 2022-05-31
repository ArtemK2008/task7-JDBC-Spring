package com.kalachev.task7.initialization.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionMaker;
import com.kalachev.task7.utilities.JdbcCloser;

public class GroupsFiller {

  private static final String INSERT_GROUPS = "INSERT INTO Groups (group_name) VALUES (?)";

  public void populateGroups(List<String> groups) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionMaker.getDbConnectionForNewUser();
      statement = connection.prepareStatement(INSERT_GROUPS);
      connection.setAutoCommit(false);
      for (String group : groups) {
        statement.setString(1, group);
        statement.addBatch();
      }
      statement.executeBatch();
      connection.commit();
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(statement, connection);
    }
  }
}
