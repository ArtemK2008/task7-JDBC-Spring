package com.kalachev.task7.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.kalachev.task7.business.UiException;
import com.kalachev.task7.business.UserOptions;
import com.kalachev.task7.dao.DaoException;
import com.kalachev.task7.dao.Initializer;

public class ConsoleApp {
  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner = new Scanner(System.in);
  UserOptions userOptions = new UserOptions();

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
        pressEnter();
        cleanConsole();
      }
    }
  }

  private void handleOptions(int option) {
    if (option == 1) {
      handleGroupSizeOption();
    }
    if (option == 2) {
      handleFindStudentsByCourseName();
    }
    if (option == 3) {
      handleAddStudentOption();
    }
    if (option == 4) {
      handleDeleteByIdOption();
    }
    if (option == 5) {
      handleAddToCourseOption();
    }
    if (option == 6) {
      handleRemoveStudentFromCourseOption();
    }
    if (option == 7) {
      handleExitOption();
    }
  }

  private void handleGroupSizeOption() {
    System.out.println("Choose maximal group size ");
    try {
      int size = scanner.nextInt();
      userOptions.printGroupsOfSize(size);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void handleFindStudentsByCourseName() {
    try {
      userOptions.printCourseNames();
      System.out.println("Choose a course to see its students");
      String course = scanner.next();
      userOptions.printStudentsByCourse(course);
    } catch (UiException e) {
      e.printStackTrace();
    }
  }

  private void handleAddStudentOption() {
    try {
      System.out.println("Enter student name");
      String name = scanner.next();
      System.out.println("Enter student last name");
      String lastname = scanner.next();
      System.out.println("Enter group id to add this student");
      int grpouId = scanner.nextInt();
      userOptions.addNewStudent(name, lastname, grpouId);

    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void handleDeleteByIdOption() {
    System.out.println("Enter ID of a student you want to delete");
    try {
      int id = scanner.nextInt();
      userOptions.deleteStudentById(id);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void handleAddToCourseOption() {
    try {
      System.out.println("Enter ID of a student you want to add");
      int id = scanner.nextInt();
      if (!userOptions.checkIfStudentIdExists(id)) {
        System.out.println("There is no student with such id");
        return;
      }
      userOptions.printCourseNames();
      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      userOptions.addStudentToCourse(id, course);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void handleRemoveStudentFromCourseOption() {
    try {
      System.out.println("Enter ID of a student you want to delete");
      int id = scanner.nextInt();
      userOptions.printCourseNamesByID(id);
      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      userOptions.removeStudentFromCourse(id, course);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void pressEnter() {
    System.out.println("press \"ENTER\" to continue...");
    scanner.nextLine();
  }

  private void handleExitOption() {
    System.out.println("Have a good day!");
    scanner.close();
    System.exit(0);

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
