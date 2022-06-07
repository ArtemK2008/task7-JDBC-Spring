package com.kalachev.task7.dao.initialization_interfaces;

import com.kalachev.task7.exceptions.DaoException;

public interface SchemaInitializer {

  void createSchema() throws DaoException;

}