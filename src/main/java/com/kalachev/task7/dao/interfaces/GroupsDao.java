package com.kalachev.task7.dao.interfaces;

import java.util.List;

import com.kalachev.task7.dao.entities.Group;
import com.kalachev.task7.exceptions.DaoException;

public interface GroupsDao {
  List<Group> findBySize(int maxSize) throws DaoException;
}