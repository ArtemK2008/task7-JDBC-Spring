package com.kalachev.task7.dao.daoInterfaces;

import java.util.List;

import com.kalachev.task7.entities.Course;
import com.kalachev.task7.exceptions.DaoException;

public interface CoursesDao {

  void addStudentToCourse(int studentId, String course) throws DaoException;

  void removeStudentFromCourse(int studentId, String course)
      throws DaoException;

  List<Course> retrieveCourses() throws DaoException;

  List<Course> retrieveCoursesById(int studentId) throws DaoException;
}