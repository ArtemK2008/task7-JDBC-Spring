package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.utilities.ExceptionsUtil;

public class StudentOptions {

  static final String SPACE = " ";

  StudentsDao studentsDao;
  CoursesDao coursesDao;

  public StudentOptions(StudentsDao studentsDao, CoursesDao coursesDao) {
    super();
    this.studentsDao = studentsDao;
    this.coursesDao = coursesDao;
  }

  public List<String> findByCourse(String course) throws UiException {
    if (!checkIfCourseExists(course)) {
      throw new IllegalArgumentException();
    }
    List<String> studentOfThisCourse = new ArrayList<>();
    try {
      List<Student> student = studentsDao.findByCourse(course);
      student.forEach(s -> studentOfThisCourse
          .add(s.getFirstName() + SPACE + s.getLastName()));
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return studentOfThisCourse;
  }

  public boolean addNewStudent(String firstName, String lastName, int groupId)
      throws UiException {
    boolean isAdded = false;
    if (groupId < 1 || groupId > 11) {
      throw new IllegalArgumentException();
    }
    if (checkIfStudentAlreadyInGroup(groupId, firstName, lastName)) {
      throw new IllegalArgumentException();
    }

    try {
      studentsDao.insert(firstName, lastName, groupId);
      isAdded = true;
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return isAdded;
  }

  public boolean deleteStudentById(int id) throws UiException {
    boolean isDeleted = false;
    if (id < 1) {
      throw new IllegalArgumentException();
    }
    try {
      if (!studentsDao.isIdExists(id)) {
        throw new IllegalArgumentException();
      }
      studentsDao.delete(id);
      isDeleted = true;
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return isDeleted;
  }

  public boolean checkIfStudentAlreadyInGroup(int groupId, String firstName,
      String lastName) throws UiException {
    boolean isInGroup = false;
    try {
      if (studentsDao.isExistsInGroup(firstName, lastName, groupId)) {
        isInGroup = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
    return isInGroup;
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
}
