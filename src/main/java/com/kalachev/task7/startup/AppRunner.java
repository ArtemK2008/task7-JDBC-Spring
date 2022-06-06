package com.kalachev.task7.startup;

import java.util.Scanner;

import com.kalachev.task7.dao.initialization.Initializer;
import com.kalachev.task7.dao.initialization.InitializerImpl;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.ui.ConsoleMenu;

public class AppRunner {

  public static void main(String[] args) throws DaoException {
    Scanner scanner = new Scanner(System.in);
    Initializer initializer = new InitializerImpl();
    ConsoleMenu app = new ConsoleMenu(scanner, initializer);
    app.runSchoolApp();

  }
}
