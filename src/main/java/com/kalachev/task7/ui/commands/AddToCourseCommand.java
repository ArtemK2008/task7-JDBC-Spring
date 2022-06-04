package com.kalachev.task7.ui.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kalachev.task7.dao.implementations.CoursesDaoImpl;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.validations.Validator;

public class AddToCourseCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;

  CoursesDao coursesDao = new CoursesDaoImpl();
  CoursesOptions options;

  public AddToCourseCommand(Scanner scanner, CoursesOptions options) {
    super();
    this.scanner = scanner;
    this.options = options;
  }

  @Override
  public void execute() {
    try {
      List<String> courses = retrieveCoursesNames();
      if (courses.isEmpty()) {
        return;
      }
      System.out.println("Enter ID of a student you want to add");
      int id = Integer.parseInt(scanner.next());
      if (!checkIfIdExists(id)) {
        return;
      }
      printCourses(courses);
      System.out.println("Enter a name of a course from the list");
      String course = scanner.next();
      if (!courses.contains(course)) {
        System.out.println("Wrong course name");
        return;
      }

      if (checkIfAlreadyInCourse(id, course)) {
        return;
      }

      options.addStudentToCourse(id, course);
      System.out
          .println("Student with id " + id + " added to course " + course);
    } catch (UiException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

  private boolean checkIfIdExists(int id) throws UiException {
    if (id < 0) {
      System.out.println("id cant be negative");
      return false;
    }
    boolean isExist = true;

    if (!Validator.checkIfStudentIdExists(id)) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private boolean checkIfAlreadyInCourse(int id, String course)
      throws UiException {
    boolean isInCourse = false;
    if (Validator.checkIfStudentAlreadyInCourse(id, course)) {
      System.out.println("student already in course");
      isInCourse = true;
    }
    return isInCourse;
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
