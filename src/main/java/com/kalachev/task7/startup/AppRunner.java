package com.kalachev.task7.startup;

import com.kalachev.task7.dao.initialization.DatabaseCreator;
import com.kalachev.task7.dao.initialization.UserCreator;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.ui.ConsoleMenu;

public class AppRunner {

  public static void main(String[] args) throws DaoException {

    DatabaseCreator db = new DatabaseCreator();
    UserCreator uc = new UserCreator();
    db.createDatabase();
    uc.createUser();

    ConsoleMenu app = new ConsoleMenu();
    app.runSchoolApp();

  }

}
