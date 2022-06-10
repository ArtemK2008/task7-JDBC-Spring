package com.kalachev.task7.dao.entities;

import java.util.Objects;

public class Course {

  private int id;
  private String courseName;
  private String courseDescription;

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
    return courseDescription;
  }

  public void setCourseDescription(String courseDescription) {
    this.courseDescription = courseDescription;
  }

  @Override
  public int hashCode() {
    return Objects.hash(courseName, id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Course other = (Course) obj;
    return Objects.equals(courseName, other.courseName) && id == other.id;
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", courseName=" + courseName
        + ", courseDescription=" + courseDescription + "]";
  }

}
