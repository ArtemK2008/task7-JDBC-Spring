package com.kalachev.task7.ui;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.kalachev.task7.dao.implementations.CoursesDaoImpl;
import com.kalachev.task7.dao.implementations.GroupsDaoImpl;
import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.GroupsDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.options.GroupOptions;
import com.kalachev.task7.service.options.StudentOptions;
import com.kalachev.task7.ui.commands.AddStudentCommand;
import com.kalachev.task7.ui.commands.AddToCourseCommand;
import com.kalachev.task7.ui.commands.Command;
import com.kalachev.task7.ui.commands.DeleteByIdCommand;
import com.kalachev.task7.ui.commands.ExitCommand;
import com.kalachev.task7.ui.commands.FindStudentsByCourseCommand;
import com.kalachev.task7.ui.commands.GroupSizeCommand;
import com.kalachev.task7.ui.commands.RemoveFromCourseCommand;

public class ConsoleMenu {
  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  Initializer initializerImpl;
  Map<String, Command> commands;

  public ConsoleMenu(Scanner scanner, Initializer initializerImpl) {
    super();
    this.scanner = scanner;
    this.initializerImpl = initializerImpl;
  }

  private static String[] options = {
      "1: Find all groups with less or equals student count",
      "2: Find all students related to course with given name",
      "3: Add new student", "4: Delete student by ID",
      "5: Add a student to the course (from a list)",
      "6: Remove the student from one of his or her courses", "7: Exit" };

  public void runSchoolApp() throws DaoException {
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
    GroupsDao groupsDao = new GroupsDaoImpl();
    CoursesDao coursesDao = new CoursesDaoImpl();
    StudentsDao studentsDao = new StudentsDaoImpl();
    GroupOptions groupOptions = new GroupOptions(groupsDao);
    CoursesOptions coursesOptions = new CoursesOptions(coursesDao, studentsDao);
    StudentOptions studentOptions = new StudentOptions(studentsDao, coursesDao);
    commands = new HashMap<>();
    commands.put("1", new GroupSizeCommand(scanner, groupOptions));
    commands.put("2", new FindStudentsByCourseCommand(scanner, coursesOptions,
        studentOptions));
    commands.put("3", new AddStudentCommand(scanner, studentOptions));
    commands.put("4", new DeleteByIdCommand(scanner, studentOptions));
    commands.put("5", new AddToCourseCommand(scanner, coursesOptions));
    commands.put("6", new RemoveFromCourseCommand(scanner, coursesOptions));
    commands.put("7", new ExitCommand(scanner));
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
