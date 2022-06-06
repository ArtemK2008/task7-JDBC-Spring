package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.validations.Validator;
import com.kalachev.task7.utilities.ExceptionsUtil;

public class CoursesOptions {

  CoursesDao coursesDao;
  StudentsDao studentsDao;

  public CoursesOptions(CoursesDao coursesDao, StudentsDao studentsDao) {
    this.coursesDao = coursesDao;
    this.studentsDao = studentsDao;
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

  public boolean removeStudentFromCourse(int studentId, String course)
      throws UiException {
    boolean isRemoved = false;
    validateInput(studentId, course);
    if (!Validator.checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new IllegalArgumentException();
    }
    try {
      coursesDao.removeStudent(studentId, course);
      isRemoved = true;
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return isRemoved;
  }

  public List<String> findCourseNames() throws UiException {
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

  public List<String> findCourseNamesByID(int id) throws UiException {
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

  public boolean checkIfStudentIdExists(int id) throws UiException {
    boolean isExist = false;
    try {
      if (studentsDao.isIdExists(id)) {
        isExist = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return isExist;
  }

  private boolean checkIfCourseExists(String course) throws UiException {
    boolean isExist = false;
    try {
      if (coursesDao.isExists(course)) {
        isExist = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return isExist;
  }

  private void validateInput(int studentId, String course) throws UiException {
    if (studentId < 0) {
      throw new IllegalArgumentException();
    }

    if (!checkIfCourseExists(course)) {
      throw new UiException();
    }
    try {
      if (!studentsDao.isIdExists(studentId)) {
        throw new UiException();
      }
    } catch (DaoException e) {
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
  }

}
