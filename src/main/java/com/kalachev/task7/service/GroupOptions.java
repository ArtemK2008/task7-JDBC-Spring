package com.kalachev.task7.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.task7.dao.GroupsDao;
import com.kalachev.task7.dao.entities.Group;

@Component
public class GroupOptions {
  @Autowired
  GroupsDao groupsDao;

  public GroupOptions(GroupsDao groupsDao) {
    super();
    this.groupsDao = groupsDao;
  }

  public List<String> findBySize(int maxSize) {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Group> group = groupsDao.findBySize(maxSize);
    return group.stream().map(Group::getGroupName).collect(Collectors.toList());
  }

}
