package com.kalachev.task7.ui.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kalachev.task7.dao.implementations.CoursesDaoImpl;
import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.options.StudentOptions;

public class FindStudentByCourseCommand implements Command {
  Scanner scanner;
  CoursesDao coursesDao = new CoursesDaoImpl();
  CoursesOptions options = new CoursesOptions(coursesDao);

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
      courses = options.findNames();
    } catch (UiException e) {
      System.out.println("No Courses Found");
    }
    return courses;
  }

  private void printAllCourseStudents(String course) throws UiException {
    StudentsDao studentsDao = new StudentsDaoImpl();
    StudentOptions studentOptions = new StudentOptions(studentsDao);
    List<String> students = studentOptions.findByCourse(course);
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
