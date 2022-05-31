package com.kalachev.task7.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.kalachev.task7.dao.initialization.Initializer;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.service.UserOptions;
import com.kalachev.task7.ui.commands.AddStudentCommand;
import com.kalachev.task7.ui.commands.AddToCourseCommand;
import com.kalachev.task7.ui.commands.Command;
import com.kalachev.task7.ui.commands.DeleteByIdCommand;
import com.kalachev.task7.ui.commands.ExitCommand;
import com.kalachev.task7.ui.commands.FindStudentByCourseCommand;
import com.kalachev.task7.ui.commands.GroupSizeCommand;
import com.kalachev.task7.ui.commands.RemoveFromCourseCommand;

public class ConsoleApp {
  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner = new Scanner(System.in);
  UserOptions userOptions = new UserOptions();
  Command command;

  private static String[] options = {
      "1: Find all groups with less or equals student count",
      "2: Find all students related to course with given name",
      "3: Add new student", "4: Delete student by ID",
      "5: Add a student to the course (from a list)",
      "6: Remove the student from one of his or her courses", "7: Exit" };

  public void runSchoolApp() throws DaoException {
    Initializer initializer = new Initializer();
    initializer.initializeTables();
    cleanConsole();
    int option = 1;
    while (option != 7) {
      printMenu(options);
      try {
        option = scanner.nextInt();
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

  private void handleOptions(int option) {
    if (option == 1) {
      command = new GroupSizeCommand(scanner);
      command.execute();
    }
    if (option == 2) {
      command = new FindStudentByCourseCommand(scanner);
      command.execute();
    }
    if (option == 3) {
      command = new AddStudentCommand(scanner);
      command.execute();
    }
    if (option == 4) {
      command = new DeleteByIdCommand(scanner);
      command.execute();
    }
    if (option == 5) {
      command = new AddToCourseCommand(scanner);
      command.execute();
    }
    if (option == 6) {
      command = new RemoveFromCourseCommand(scanner);
      command.execute();
    }
    if (option == 7) {
      command = new ExitCommand(scanner);
      command.execute();
    }
  }

  private void pressEnterMessage() {
    System.out.println("press \"ENTER\" to continue...");
    scanner.nextLine();
  }

  private void validateOption(int option) {
    if (option < 1 || option > 7) {
      throw new InputMismatchException();
    }
  }

  private static void printMenu(String[] options) {
    for (String option : options) {
      System.out.println(option);
    }
    System.out.print("Choose your option : ");
  }

  private void cleanConsole() {
    for (int i = 0; i < 100; i++) {
      System.out.println();
    }
  }
}
