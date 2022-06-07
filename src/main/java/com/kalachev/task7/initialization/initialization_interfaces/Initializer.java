package com.kalachev.task7.initialization.initialization_interfaces;

import com.kalachev.task7.exceptions.DaoException;

public interface Initializer {

  void initializeTables() throws DaoException;

}