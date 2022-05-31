package com.kalachev.task7.dao.daoInterfaces;

import java.util.List;

import com.kalachev.task7.entities.Group;
import com.kalachev.task7.exceptions.DaoException;

public interface GroupsDao {
  List<Group> findGroupsBySize(int maxSize) throws DaoException;
}