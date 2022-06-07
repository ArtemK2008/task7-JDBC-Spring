package com.kalachev.task7.dao.initialization.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.JdbcUtil;

public class GroupsDataDbPopulator {

  private static final String INSERT_GROUPS = "INSERT INTO Groups (group_name) VALUES (?)";

  public void populateGroups(List<String> groups) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = ConnectionManager.openDbConnection();
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
      throw new DaoException("Error while populating table Groups");
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }
}
