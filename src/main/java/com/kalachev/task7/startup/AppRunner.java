package com.kalachev.task7.startup;

import com.kalachev.task7.dao.initialization.DatabaseInitializer;
import com.kalachev.task7.dao.initialization.UserInitializer;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.ui.ConsoleMenu;

public class AppRunner {

  public static void main(String[] args) throws DaoException {
    DatabaseInitializer db = new DatabaseInitializer();
    UserInitializer uc = new UserInitializer();
    db.createDatabase();
    uc.createUser();

    ConsoleMenu app = new ConsoleMenu();
    app.runSchoolApp();

  }

}
