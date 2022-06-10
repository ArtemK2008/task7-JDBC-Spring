package com.kalachev.task7.ui;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.ui.commands.Command;
import com.kalachev.task7.ui.dispatcher.CommandDispatcher;

public class ConsoleMenuForSpring {
  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  Initializer initializerImpl;
  Map<String, Command> commands;
  CommandDispatcher commandDispatcher;

  public ConsoleMenuForSpring(Scanner scanner, Initializer initializerImpl,
      CommandDispatcher commandDispatcher) {
    super();
    this.scanner = scanner;
    this.initializerImpl = initializerImpl;
    this.commandDispatcher = commandDispatcher;
  }

  private static String[] options = {
      "1: Find all groups with less or equals student count",
      "2: Find all students related to course with given name",
      "3: Add new student", "4: Delete student by ID",
      "5: Add a student to the course (from a list)",
      "6: Remove the student from one of his or her courses", "7: Exit" };

  public void runSchoolApp() {
    initializerImpl.initializeTables();
    initializeCommands();
    cleanConsole();
    String option = "1";
    while (!"7".equals(option)) {
      printMenu(options);
      try {
        option = scanner.next();
        validateOption(option);
        handleOptions(option);
      } catch (InputMismatchException e) {
        System.out.println(
            "Please enter an integer value between 1 and " + options.length);
      } catch (Exception ex) {
        System.out.println("An unexpected error happened. Please try again");
      } finally {
        scanner.nextLine();
        pressEnterMessage();
        cleanConsole();
      }
    }
  }

  private void initializeCommands() {
    commands = new HashMap<>();
    commands.put("1", commandDispatcher.getGroupSizeCommand());
    commands.put("2", commandDispatcher.getFindStudentsByCourseCommand());
    commands.put("3", commandDispatcher.getAddStudentCommand());
    commands.put("4", commandDispatcher.getDeleteByIdCommand());
    commands.put("5", commandDispatcher.getAddToCourseCommand());
    commands.put("6", commandDispatcher.getRemoveFromCourseCommand());
    commands.put("7", commandDispatcher.getExitCommand());
  }

  private void handleOptions(String option) {
    commands.get(option).execute();
  }

  private void pressEnterMessage() {
    System.out.println("press \"ENTER\" to continue...");
    scanner.nextLine();
  }

  private void validateOption(String option) {
    if (Integer.parseInt(option) < 1 || Integer.parseInt(option) > 7) {
      throw new InputMismatchException();
    }
  }

  private static void printMenu(String[] options) {
    for (String option : options) {
      System.out.println(option);
    }
    System.out.println("Choose your option : ");
  }

  public void cleanConsole() {
    for (int i = 0; i < 100; i++) {
      System.out.println();
    }
  }
}
