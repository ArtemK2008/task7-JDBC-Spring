package com.kalachev.task7.ui.commands;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.kalachev.task7.service.options_interfaces.CoursesOptions;

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
    System.out.println("Enter ID of a student you want to delete");
    String inputedId = scanner.next();
    if (!validateId(inputedId)) {
      return;
    }
    int id = Integer.parseInt(inputedId);
    List<String> courses = findCourseNames(id);
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
    removeStudentFromCourse(id, course);
  }

  private boolean validateId(String id) {
    if (!NumberUtils.isParsable(id)) {
      System.out.println(BAD_INPUT);
      return false;
    }
    if (Integer.parseInt(id) < 0) {
      System.out.println("Wrong id");
      return false;
    }
    boolean isExist = true;

    if (!options.checkIfStudentIdExists(Integer.valueOf(id))) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private List<String> findCourseNames(int id) {
    return options.findCourseNamesByID(id);
  }

  private boolean checkIfCourseHaveThisStudent(int id, String course) {
    boolean isInCourse = true;
    if (!options.checkIfStudentAlreadyInCourse(id, course)) {
      System.out.println("no such student in this course");
      isInCourse = false;
    }
    return isInCourse;
  }

  private void removeStudentFromCourse(int id, String course) {

    if (options.removeStudentFromCourse(id, course)) {
      System.out.println("Student with id " + id + " removed from " + course);
    }
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

}
