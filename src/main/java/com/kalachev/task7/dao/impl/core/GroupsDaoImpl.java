package com.kalachev.task7.dao.impl.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.task7.dao.GroupsDao;
import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.JdbcUtil;

@Component
public class GroupsDaoImpl implements GroupsDao {

  private static final String FIND_GROUP_BY_SIZE = "SELECT g.group_id,g.group_name FROM Students"
      + " as s " + "INNER JOIN Groups as g " + "ON s.group_id = g.group_id "
      + "GROUP BY g.group_id,g.group_name " + "HAVING COUNT (s.group_id) >=(?)";

  @Override
  public List<Group> findBySize(int size) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    List<Group> groups = new LinkedList<>();
    try {
      connection = ConnectionManager.openDbConnection();
      statement = connection.prepareStatement(FIND_GROUP_BY_SIZE);
      statement.setInt(1, size);
      rs = statement.executeQuery();
      while (rs.next()) {
        Group group = new Group();
        group.setId(rs.getInt("group_id"));
        group.setGroupName(rs.getString("group_name"));
        groups.add(group);
      }
    } catch (SQLException e) {
      System.out
          .println("Error while getting groups with size less then " + size);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return groups;
  }

}
