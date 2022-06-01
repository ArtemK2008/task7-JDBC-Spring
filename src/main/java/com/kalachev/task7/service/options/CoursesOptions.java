package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import javax.management.OperationsException;

import com.kalachev.task7.dao.classes.CoursesDaoImpl;
import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.checks.ValidationChecks;

public class CoursesOptions {

  CoursesDao coursesDao;

  public CoursesOptions() {
    this.coursesDao = new CoursesDaoImpl();
  }

  public void addStudentToCourse(int studentId, String course)
      throws UiException, OperationsException {

    checkIfInputValid(studentId, course);
    if (ValidationChecks.checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new OperationsException();
    }
    try {
      coursesDao.addStudentToCourse(studentId, course);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

  public void removeStudentFromCourse(int studentId, String course)
      throws UiException, OperationsException {

    checkIfInputValid(studentId, course);
    if (!ValidationChecks.checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new OperationsException();
    }
    try {
      coursesDao.removeStudentFromCourse(studentId, course);
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
  }

  public List<String> findCourseNames() throws UiException {
    List<Course> courses = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();
    try {
      courses = coursesDao.retrieveCourses();
      if (courses.isEmpty()) {
        throw new UiException();
      }
      courses.forEach(c -> courseNames.add(c.getCourseName()));
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return courseNames;
  }

  public List<String> retrieveCourseNamesByID(int id) throws UiException {
    List<Course> courses = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();
    try {
      courses = coursesDao.retrieveCoursesById(id);
      courses.forEach(c -> courseNames.add(c.getCourseName()));
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return courseNames;
  }

  private void checkIfInputValid(int studentId, String course)
      throws UiException, OperationsException {
    if (studentId < 0) {
      throw new IllegalArgumentException();
    }

    if (!ValidationChecks.checkIfCourseExists(course)) {
      throw new UiException();
    }

    if (!ValidationChecks.checkIfStudentIdExists(studentId)) {
      throw new UiException();
    }
  }

}
