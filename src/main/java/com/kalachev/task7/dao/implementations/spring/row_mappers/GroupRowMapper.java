package com.kalachev.task7.dao.implementations.spring.row_mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kalachev.task7.dao.entities.Group;

public class GroupRowMapper implements RowMapper<Group> {

  @Override
  public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
    Group group = new Group();
    group.setId(rs.getInt("group_id"));
    group.setGroupName(rs.getString("group_name"));
    return group;
  }
}