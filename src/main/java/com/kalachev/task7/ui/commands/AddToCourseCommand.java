package com.kalachev.task7.ui.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.management.OperationsException;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.checks.ValidationChecks;
import com.kalachev.task7.service.options.CoursesOptions;

public class AddToCourseCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;

  CoursesOptions options = new CoursesOptions();

  public AddToCourseCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
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
      options.addStudentToCourse(id, course);
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

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

  private boolean checkIfIdExists(int id) throws UiException {
    if (id < 0) {
      System.out.println("Wrong id");
      return false;
    }
    boolean isExist = true;

    if (!ValidationChecks.checkIfStudentIdExists(id)) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private List<String> retrieveCoursesNames() {
    List<String> courses = new ArrayList<>();
    try {
      courses = options.findCourseNames();
    } catch (UiException e) {
      System.out.println("No Courses Found");
    }
    return courses;
  }
}
