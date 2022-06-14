package com.kalachev.task7.ui.commands.impl;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.task7.ui.commands.Command;

@Component
public class ExitCommand implements Command {
  @Autowired
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
