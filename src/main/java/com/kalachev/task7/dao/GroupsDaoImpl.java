package com.kalachev.task7.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.daoInterfaces.GroupsDao;
import com.kalachev.task7.entities.Group;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionMaker;
import com.kalachev.task7.utilities.JdbcCloser;

public class GroupsDaoImpl implements GroupsDao {

  private static final String FIND_GROUP_BY_SIZE = "SELECT g.group_id,g.group_name FROM Students"
      + " as s " + "INNER JOIN Groups as g " + "ON s.group_id = g.group_id "
      + "GROUP BY g.group_id,g.group_name " + "HAVING COUNT (s.group_id) >=(?)";

  @Override
  public List<Group> findGroupsBySize(int maxSize) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    List<Group> groups = new ArrayList<>();
    try {
      connection = ConnectionMaker.getDbConnectionForNewUser();
      statement = connection.prepareStatement(FIND_GROUP_BY_SIZE);
      statement.setInt(1, maxSize);
      rs = statement.executeQuery();
      while (rs.next()) {
        Group group = new Group();
        group.setId(rs.getInt("group_id"));
        group.setGroupName(rs.getString("group_name"));
        groups.add(group);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(rs, statement, connection);
    }
    return groups;
  }

}
