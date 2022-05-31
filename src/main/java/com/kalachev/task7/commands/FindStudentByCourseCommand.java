package com.kalachev.task7.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.menu.UserOptions;

public class FindStudentByCourseCommand implements Command {
  Scanner scanner;
  UserOptions userOptions = new UserOptions();

  public FindStudentByCourseCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
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

  private List<String> retrieveCoursesNames() {
    List<String> courses = new ArrayList<>();
    try {
      courses = userOptions.findCourseNames();
    } catch (UiException e) {
      System.out.println("No Courses Found");
    }
    return courses;
  }

  private void printAllCourseStudents(String course) throws UiException {
    List<String> students = userOptions.findStudentsByCourse(course);
    if (students.isEmpty()) {
      System.out.println("No users in this course");
    } else {
      students.forEach(System.out::println);
    }
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

}
