package com.kalachev.task7.ui.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.options.StudentOptions;

public class FindStudentsByCourseCommand implements Command {
  Scanner scanner;
  CoursesOptions courseOptions;
  StudentOptions studentOptions;

  public FindStudentsByCourseCommand(Scanner scanner,
      CoursesOptions courseOptions, StudentOptions studentOptions) {
    super();
    this.scanner = scanner;
    this.courseOptions = courseOptions;
    this.studentOptions = studentOptions;
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
      courses = courseOptions.findCourseNames();
    } catch (UiException e) {
      System.out.println("No Courses Found");
    }
    return courses;
  }

  private void printAllCourseStudents(String course) throws UiException {
    List<String> students = studentOptions.findByCourse(course);
    if (students.isEmpty()) {
      System.out.println("No students in this course");
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
