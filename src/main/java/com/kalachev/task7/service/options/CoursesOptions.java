package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.CourseNotFoundException;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.StudentNotFoundException;
import com.kalachev.task7.exceptions.UiException;

public class CoursesOptions {

  CoursesDao coursesDao;
  StudentsDao studentsDao;

  public CoursesOptions(CoursesDao coursesDao, StudentsDao studentsDao) {
    this.coursesDao = coursesDao;
    this.studentsDao = studentsDao;
  }

  public void addStudentToCourse(int studentId, String course)
      throws UiException, CourseNotFoundException, StudentNotFoundException {

    validateInput(studentId, course);
    if (checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new IllegalArgumentException();
    }
    try {
      coursesDao.addStudent(studentId, course);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

  public boolean removeStudentFromCourse(int studentId, String course)
      throws CourseNotFoundException, StudentNotFoundException, UiException {
    boolean isRemoved = false;
    validateInput(studentId, course);
    if (!checkIfStudentAlreadyInCourse(studentId, course)) {
      throw new IllegalArgumentException();
    }
    try {
      coursesDao.removeStudent(studentId, course);
      isRemoved = true;
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException("Can not remove Student with ID: " + studentId
          + " from course " + course);
    }
    return isRemoved;
  }

  public List<String> findCourseNames() throws CourseNotFoundException {
    List<Course> courses = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();
    try {
      courses = coursesDao.getAll();
      if (courses.isEmpty()) {
        throw new CourseNotFoundException();
      }
      courses.forEach(c -> courseNames.add(c.getCourseName()));
    } catch (DaoException e) {
      throw new CourseNotFoundException();
    }
    return courseNames;
  }

  public List<String> findCourseNamesByID(int id)
      throws CourseNotFoundException {
    List<Course> courses = new ArrayList<>();
    List<String> courseNames = new ArrayList<>();
    try {
      courses = coursesDao.getById(id);
      courses.forEach(c -> courseNames.add(c.getCourseName()));
    } catch (DaoException e) {
      throw new CourseNotFoundException(id);
    }
    return courseNames;
  }

  public boolean checkIfStudentIdExists(int id)
      throws StudentNotFoundException {
    boolean isExist = false;
    try {
      if (studentsDao.isIdExists(id)) {
        isExist = true;
      }
    } catch (DaoException e) {
      throw new StudentNotFoundException(id);
    }
    return isExist;
  }

  private boolean checkIfCourseExists(String course)
      throws CourseNotFoundException {
    boolean isExist = false;
    try {
      if (coursesDao.isExists(course)) {
        isExist = true;
      }
    } catch (DaoException e) {
      throw new CourseNotFoundException(course);
    }
    return isExist;
  }

  private void validateInput(int studentId, String course)
      throws CourseNotFoundException, StudentNotFoundException {
    if (studentId < 0) {
      throw new IllegalArgumentException();
    }

    if (!checkIfCourseExists(course)) {
      throw new CourseNotFoundException(course);
    }
    try {
      if (!studentsDao.isIdExists(studentId)) {
        throw new StudentNotFoundException(studentId);
      }
    } catch (DaoException e) {
      throw new IllegalArgumentException();
    }
  }

  public boolean checkIfStudentAlreadyInCourse(int id, String course) {
    boolean isExist = false;
    try {
      if (studentsDao.checkIfStudentInCourse(id, course)) {
        isExist = true;
      }
    } catch (DaoException e) {
      throw new IllegalArgumentException();
    }
    return isExist;
  }

}
