package com.kalachev.task7.startup;

import java.util.Scanner;

import com.kalachev.task7.initialization.core.InitializerImpl;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.ui.ConsoleMenuForCore;

public class AppRunner {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Initializer initializer = new InitializerImpl();
    ConsoleMenuForCore app = new ConsoleMenuForCore(scanner, initializer);
    app.runSchoolApp();

  }
}
