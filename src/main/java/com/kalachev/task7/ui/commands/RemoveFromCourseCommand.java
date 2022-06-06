package com.kalachev.task7.ui.commands;

import java.util.List;
import java.util.Scanner;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.validations.Validator;

public class RemoveFromCourseCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  CoursesOptions options;

  public RemoveFromCourseCommand(Scanner scanner, CoursesOptions options) {
    super();
    this.scanner = scanner;
    this.options = options;
  }

  @Override
  public void execute() {
    try {
      System.out.println("Enter ID of a student you want to delete");
      int id = Integer.parseInt(scanner.next());
      if (!checkIfIdExists(id)) {
        return;
      }
      List<String> courses = options.findCourseNamesByID(id);
      printCourses(courses);
      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      if (!courses.contains(course)) {
        System.out.println("Wrong course name");
        return;
      }
      if (!checkIfCourseHaveThisStudent(id, course)) {
        return;
      }
      options.removeStudentFromCourse(id, course);
      System.out.println("Student with id " + id + " removed from " + course);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private boolean checkIfIdExists(int id) throws UiException {
    if (id < 0) {
      System.out.println("Wrong id");
      return false;
    }
    boolean isExist = true;
    if (!options.checkIfStudentIdExists(id)) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private boolean checkIfCourseHaveThisStudent(int id, String course)
      throws UiException {
    boolean isInCourse = true;
    if (!Validator.checkIfStudentAlreadyInCourse(id, course)) {
      System.out.println("no such student in this course");
      isInCourse = false;
    }
    return isInCourse;
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

}
