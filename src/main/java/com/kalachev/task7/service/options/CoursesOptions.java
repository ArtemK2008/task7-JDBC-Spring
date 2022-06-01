package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.validations.Validator;
import com.kalachev.task7.utilities.ExceptionsUtil;

public class CoursesOptions {

  CoursesDao coursesDao;

  public CoursesOptions(CoursesDao coursesDao) {
    this.coursesDao = coursesDao;
  }

  public void addStudentToCourse(int studentId, String course)
      throws UiException {

    validateInput(studentId, course);
    if (Validator.checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new IllegalArgumentException();
    }
    try {
      coursesDao.addStudent(studentId, course);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

  public void removeStudentFromCourse(int studentId, String course)
      throws UiException {

    validateInput(studentId, course);
    if (!Validator.checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new IllegalArgumentException();
    }
    try {
      coursesDao.removeStudent(studentId, course);
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
  }

  public List<String> findNames() throws UiException {
    List<Course> courses = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();
    try {
      courses = coursesDao.getAll();
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

  public List<String> findNamesByID(int id) throws UiException {
    List<Course> courses = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();
    try {
      courses = coursesDao.getById(id);
      courses.forEach(c -> courseNames.add(c.getCourseName()));
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return courseNames;
  }

  private void validateInput(int studentId, String course) throws UiException {
    if (studentId < 0) {
      throw new IllegalArgumentException();
    }

    if (!Validator.checkIfCourseExists(course)) {
      throw new UiException();
    }

    if (!Validator.checkIfStudentIdExists(studentId)) {
      throw new UiException();
    }
  }

}
