package com.kalachev.task7.dao.initialization_interfaces;

import com.kalachev.task7.exceptions.DaoException;

public interface Initializer {

  void initializeTables() throws DaoException;

}