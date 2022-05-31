package com.kalachev.task7.commands;

import java.util.Scanner;

public class ExitCommand implements Command {
  Scanner scanner;

  public ExitCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("Have a good day!");
    scanner.close();
    System.exit(0);
  }

}
