package com.kalachev.task7.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.management.OperationsException;

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
        pressEnterMessage();
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
      if (size < 0) {
        System.out.println("Max size cant be negative");
        return;
      }
      List<String> groups = findGroups(size);
      groups.forEach(System.out::println);
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<String> findGroups(int size) {
    List<String> groups = new ArrayList<>();
    try {
      groups = userOptions.findGroupsBySize(size);
    } catch (UiException e) {
      System.out.println("no such groups");
    }
    return groups;
  }

  private void handleFindStudentsByCourseName() {
    List<String> courses = new ArrayList<>();
    try {
      courses = retrieveCoursesNames();
      printCourses(courses);
      if (courses.isEmpty()) {
        return;
      }
      System.out.println("Choose a course to see its students");
      String course = scanner.next();

      if (!courses.contains(course)) {
        System.out.println("no such course");
        return;
      }
      printAllCourseStudents(course);
    } catch (UiException e) {
      e.printStackTrace();
    }
  }

  private void printAllCourseStudents(String course) throws UiException {
    List<String> students = userOptions.findStudentsByCourse(course);
    if (students.isEmpty()) {
      System.out.println("No users in this course");
    } else {
      students.forEach(System.out::println);
    }
  }

  private List<String> retrieveCoursesNames() {
    List<String> courses = new ArrayList<>();
    try {
      courses = userOptions.findCourseNames();
    } catch (UiException e) {
      System.out.println("No Courses Found");
    }
    return courses;
  }

  private void handleAddStudentOption() {
    try {
      System.out.println("Enter student name");
      String name = scanner.next();
      System.out.println("Enter student last name");
      String lastname = scanner.next();
      System.out.println("Enter group id to add this student");
      int groupId = scanner.nextInt();
      if (groupId < 1 || groupId > 11) {
        System.out.println("Wrong groupd id");
        return;
      }
      addStudent(name, lastname, groupId);
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void addStudent(String name, String lastname, int groupId) {
    try {
      userOptions.addNewStudent(name, lastname, groupId);
      System.out.println(
          "Student " + name + " " + lastname + " added to group " + groupId);
    } catch (OperationsException e) {
      System.out.println("User Already exists");
    } catch (UiException e) {
      System.out.println(BAD_INPUT);
    }

  }

  private void handleDeleteByIdOption() {
    System.out.println("Enter ID of a student you want to delete");
    try {
      int id = scanner.nextInt();
      if (id < 1) {
        System.out.println("Wrong student id");
        return;
      }
      userOptions.deleteStudentById(id);
      System.out.println("student with id " + id + " deleted");
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    } catch (OperationsException e) {
      System.out.println("no such student");
    }
  }

  private void handleAddToCourseOption() {
    try {
      System.out.println("Enter ID of a student you want to add");
      int id = scanner.nextInt();
      if (!checkIfIdExists(id)) {
        return;
      }
      List<String> courses = retrieveCoursesNames();
      printCourses(courses);
      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      if (!courses.contains(course)) {
        System.out.println("Wrong course name");
        return;
      }
      userOptions.addStudentToCourse(id, course);
      System.out
          .println("Student with id " + id + " added to course " + course);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    } catch (OperationsException e) {
      System.out.println("student already in course");
    }
  }

  private boolean checkIfIdExists(int id) throws UiException {
    if (id < 0) {
      System.out.println("Wrong id");
      return false;
    }
    boolean isExist = true;
    if (!userOptions.checkIfStudentIdExists(id)) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private void handleRemoveStudentFromCourseOption()
       {
    try {
      System.out.println("Enter ID of a student you want to delete");
      int id = scanner.nextInt();
      if (!checkIfIdExists(id)) {
        return;
      }
      List<String> courses = userOptions.retrieveCourseNamesByID(id);
      printCourses(courses);

      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      if (!courses.contains(course)) {
        System.out.println("Wrong course name");
        return;
      }
      userOptions.removeStudentFromCourse(id, course);
      System.out.println("Student with id " + id + " removed from " + course);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    } catch (OperationsException e) {
      System.out.println("student already in course");
    }
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

  private void pressEnterMessage() {
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
