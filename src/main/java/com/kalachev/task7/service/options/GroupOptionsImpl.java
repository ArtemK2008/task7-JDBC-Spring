package com.kalachev.task7.service.options;

import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.service.options_interfaces.GroupOptions;

public class GroupOptionsImpl implements GroupOptions {
  GroupsDao groupsDao;

  public GroupOptionsImpl(GroupsDao groupsDao) {
    super();
    this.groupsDao = groupsDao;
  }

  @Override
  public List<String> findBySize(int maxSize) {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Group> group = groupsDao.findBySize(maxSize);
    return group.stream().map(Group::getGroupName).collect(Collectors.toList());
  }

}
