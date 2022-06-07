package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.GroupNotFoundException;

public class GroupOptions {
  GroupsDao groupsDao;

  public GroupOptions(GroupsDao groupsDao) {
    super();
    this.groupsDao = groupsDao;
  }

  public List<String> findBySize(int maxSize) throws GroupNotFoundException {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Group> group = new ArrayList<>();
    List<String> groupNames = new ArrayList<>();
    try {
      group = groupsDao.findBySize(maxSize);
      groupNames = group.stream().map(Group::getGroupName)
          .collect(Collectors.toList());
    } catch (DaoException e) {
      throw new GroupNotFoundException(
          "Can not find groups with size less then " + maxSize);
    }
    if (group.isEmpty()) {
      throw new GroupNotFoundException();
    }
    return groupNames;
  }

}
