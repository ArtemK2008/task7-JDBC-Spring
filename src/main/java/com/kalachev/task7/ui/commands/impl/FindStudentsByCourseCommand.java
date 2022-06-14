package com.kalachev.task7.ui.commands.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalachev.task7.service.CoursesOptions;
import com.kalachev.task7.service.StudentOptions;
import com.kalachev.task7.ui.commands.Command;

@Component
public class FindStudentsByCourseCommand implements Command {
  @Autowired
  Scanner scanner;
  @Autowired
  CoursesOptions courseOptions;
  @Autowired
  StudentOptions studentOptions;

  public FindStudentsByCourseCommand(Scanner scanner,
      CoursesOptions courseOptions, StudentOptions studentOptions) {
    super();
    this.scanner = scanner;
    this.courseOptions = courseOptions;
    this.studentOptions = studentOptions;
  }

  @Autowired
  public void setStudentOptions(StudentOptions studentOptions) {
    this.studentOptions = studentOptions;
  }

  @Override
  public void execute() {
    List<String> courses = retrieveCoursesNames();
    if (courses.isEmpty()) {
      System.out.println("No Courses Found");
      return;
    }
    printCourses(courses);
    System.out.println("Choose a course to see its students");
    String course = scanner.next();
    if (!courses.contains(course)) {
      System.out.println("no such course");
      return;
    }
    printAllCourseStudents(course);
  }

  private List<String> retrieveCoursesNames() {
    List<String> courses = new ArrayList<>();
    courses = courseOptions.findCourseNames();
    return courses;
  }

  private void printAllCourseStudents(String course) {
    List<String> students = new ArrayList<>();
    students = studentOptions.findByCourse(course);

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
