package com.kalachev.task7.dao.initialization;

import com.kalachev.task7.exceptions.DaoException;

public interface Initializer {

  void initializeTables() throws DaoException;

}