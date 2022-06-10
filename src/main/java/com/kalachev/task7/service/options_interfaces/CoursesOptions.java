package com.kalachev.task7.service.options_interfaces;

import java.util.List;

public interface CoursesOptions {

  boolean addStudentToCourse(int studentId, String course);

  boolean removeStudentFromCourse(int studentId, String course);

  List<String> findCourseNames();

  List<String> findCourseNamesByID(int id);

  boolean checkIfStudentIdExists(int id);

  boolean checkIfStudentAlreadyInCourse(int id, String course);

}