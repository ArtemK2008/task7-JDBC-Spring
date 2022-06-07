package com.kalachev.task7.ui.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.kalachev.task7.exceptions.CourseNotFoundException;
import com.kalachev.task7.exceptions.StudentNotFoundException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;

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
    try {
      if (!options.checkIfStudentIdExists(Integer.valueOf(id))) {
        System.out.println("There is no student with such id");
        isExist = false;
      }
    } catch (StudentNotFoundException e) {
      e.printStackTrace();
    }
    return isExist;
  }

  private List<String> findCourseNames(int id) {
    List<String> courses = new ArrayList<>();
    try {
      courses = options.findCourseNamesByID(id);
    } catch (CourseNotFoundException e) {
      e.printStackTrace();
    }
    return courses;
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
    try {
      options.removeStudentFromCourse(id, course);
      System.out.println("Student with id " + id + " removed from " + course);
    } catch (StudentNotFoundException | CourseNotFoundException
        | UiException e) {
      e.printStackTrace();
    }
  }

  private void printCourses(List<String> courses) {
    for (String c : courses) {
      System.out.println(c);
    }
  }

}
