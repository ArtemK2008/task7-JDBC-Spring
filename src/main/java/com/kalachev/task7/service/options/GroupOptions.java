package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.classes.GroupsDaoImpl;
import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;

public class GroupOptions {
  GroupsDao groupsDao;

  public GroupOptions() {
    super();
    this.groupsDao = new GroupsDaoImpl();
  }

  public List<String> findGroupsBySize(int maxSize) throws UiException {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Group> group = new ArrayList<>();
    List<String> groupNames = new ArrayList<>();
    try {
      group = groupsDao.findGroupsBySize(maxSize);
      group.forEach(g -> groupNames.add(g.getGroupName()));
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    if (group.isEmpty()) {
      throw new UiException();
    }
    return groupNames;
  }

}
