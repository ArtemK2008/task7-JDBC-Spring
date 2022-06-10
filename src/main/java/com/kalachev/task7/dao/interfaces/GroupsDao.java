package com.kalachev.task7.dao.interfaces;

import java.util.List;

import com.kalachev.task7.dao.entities.Group;

public interface GroupsDao {
  List<Group> findBySize(int size);
}