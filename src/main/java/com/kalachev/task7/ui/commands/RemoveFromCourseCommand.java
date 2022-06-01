package com.kalachev.task7.ui.commands;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.management.OperationsException;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.checks.ValidationChecks;
import com.kalachev.task7.service.options.CoursesOptions;

public class RemoveFromCourseCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  CoursesOptions options = new CoursesOptions();

  public RemoveFromCourseCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    try {
      System.out.println("Enter ID of a student you want to delete");
      int id = scanner.nextInt();
      if (!checkIfIdExists(id)) {
        return;
      }
      List<String> courses = options.retrieveCourseNamesByID(id);
      printCourses(courses);

      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      if (!courses.contains(course)) {
        System.out.println("Wrong course name");
        return;
      }
      options.removeStudentFromCourse(id, course);
      System.out.println("Student with id " + id + " removed from " + course);
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
    if (!ValidationChecks.checkIfStudentIdExists(id)) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

}
