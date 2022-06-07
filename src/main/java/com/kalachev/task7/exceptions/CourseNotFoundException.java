package com.kalachev.task7.exceptions;

public class CourseNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CourseNotFoundException() {
    System.out.println("Could not found any courses");
  }

  public CourseNotFoundException(int id) {
    System.out.println("Could not found any courses for Student with id " + id);
  }

  public CourseNotFoundException(String course) {
    System.out.println("Could not found course " + course);
  }
}
