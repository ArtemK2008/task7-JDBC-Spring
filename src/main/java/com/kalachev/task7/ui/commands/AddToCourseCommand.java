package com.kalachev.task7.ui.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.kalachev.task7.dao.implementations.core.CoursesDaoImpl;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.service.options_interfaces.CoursesOptions;

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
    List<String> courses = retrieveCoursesNames();
    if (courses.isEmpty()) {
      System.out.println("No Courses Found");
      return;
    }
    System.out.println("Enter ID of a student you want to add");
    String idInputed = scanner.next();
    if (!checkIfIdValid(idInputed)) {
      return;
    }
    int id = Integer.parseInt(idInputed);
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
    addStudent(id, course);
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

  private boolean checkIfIdValid(String id) {
    if (!NumberUtils.isParsable(id)) {
      System.out.println(BAD_INPUT);
      return false;
    }

    if (Integer.parseInt(id) < 0) {
      System.out.println("id cant be negative");
      return false;
    }
    boolean isExist = true;

    if (!options.checkIfStudentIdExists(Integer.parseInt(id))) {
      System.out.println("There is no student with such id");
      isExist = false;
    }
    return isExist;
  }

  private boolean checkIfAlreadyInCourse(int id, String course) {
    boolean isInCourse = false;
    if (options.checkIfStudentAlreadyInCourse(id, course)) {
      System.out.println("student already in course");
      isInCourse = true;
    }
    return isInCourse;
  }

  private List<String> retrieveCoursesNames() {
    List<String> courses = new ArrayList<>();
    courses = options.findCourseNames();
    return courses;
  }

  private void addStudent(int id, String course) {
    if (options.addStudentToCourse(id, course)) {
      System.out
          .println("Student with id " + id + " added to course " + course);
    }
  }
}
