package com.kalachev.task7;

import com.kalachev.task7.dao.DaoException;
import com.kalachev.task7.dao.DatabaseCreator;
import com.kalachev.task7.ui.ConsoleApp;

public class AppRunner {

  public static void main(String[] args) throws DaoException {

    DatabaseCreator db = new DatabaseCreator();
    db.createDatabase();
    db.createUser();

    ConsoleApp app = new ConsoleApp();
    app.runSchoolApp();

  }

}
