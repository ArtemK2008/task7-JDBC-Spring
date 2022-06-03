package com.kalachev.task7.startup;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.ui.ConsoleMenu;

public class AppRunner {

  public static void main(String[] args) throws DaoException {

    ConsoleMenu app = new ConsoleMenu();
    app.runSchoolApp();

  }

}
