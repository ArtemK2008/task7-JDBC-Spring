package com.kalachev.task7.service.options;

import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.dao.interfaces.GroupsDao;
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
    List<Group> group = groupsDao.findBySize(maxSize);
    List<String> groupNames = group.stream().map(Group::getGroupName)
        .collect(Collectors.toList());
    if (group.isEmpty()) {
      throw new GroupNotFoundException();
    }
    return groupNames;
  }

}
