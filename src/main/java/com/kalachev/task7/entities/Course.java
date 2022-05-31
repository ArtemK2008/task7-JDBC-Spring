package com.kalachev.task7.entities;

public class Course {

  private int id;
  private String courseName;
  private String CourseDescription;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCourseDescription() {
    return CourseDescription;
  }

  public void setCourseDescription(String courseDescription) {
    CourseDescription = courseDescription;
  }

}
