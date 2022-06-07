package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.CourseNotFoundException;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.StudentNotFoundException;
import com.kalachev.task7.exceptions.UiException;

public class StudentOptions {

  static final String SPACE = " ";

  StudentsDao studentsDao;
  CoursesDao coursesDao;

  public StudentOptions(StudentsDao studentsDao, CoursesDao coursesDao) {
    super();
    this.studentsDao = studentsDao;
    this.coursesDao = coursesDao;
  }

  public List<String> findByCourse(String course)
      throws StudentNotFoundException, CourseNotFoundException {
    if (!checkIfCourseExists(course)) {
      throw new IllegalArgumentException();
    }
    List<String> studentOfThisCourse = new ArrayList<>();
    try {
      List<Student> students = studentsDao.findByCourse(course);
      studentOfThisCourse = students.stream()
          .map(s -> s.getFirstName() + SPACE + s.getLastName())
          .collect(Collectors.toList());

    } catch (DaoException e) {
      throw new StudentNotFoundException(
          "Cant find any students in course " + course);
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
      throw new UiException("Can not add Student " + firstName + " " + lastName
          + " to group " + groupId);
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
      throw new UiException("Can not delete Student with ID: " + id);
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
      throw new UiException("Can not check if Student " + firstName + " "
          + lastName + " exists in group " + groupId);
    }
    return isInGroup;
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
}
