package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.utilities.ExceptionsUtil;

public class GroupOptions {
  GroupsDao groupsDao;

  public GroupOptions(GroupsDao groupsDao) {
    super();
    this.groupsDao = groupsDao;
  }

  public List<String> findBySize(int maxSize) throws UiException {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Group> group = new ArrayList<>();
    List<String> groupNames = new ArrayList<>();
    try {
      group = groupsDao.findBySize(maxSize);
      group.forEach(g -> groupNames.add(g.getGroupName()));
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    if (group.isEmpty()) {
      throw new UiException();
    }
    return groupNames;
  }

}
