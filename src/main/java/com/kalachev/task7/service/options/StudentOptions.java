package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import javax.management.OperationsException;

import com.kalachev.task7.dao.classes.StudentsDaoImpl;
import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.checks.ValidationChecks;

public class StudentOptions {

  static final String SPACE = " ";

  StudentsDao studentsDao;

  public StudentOptions() {
    super();
    this.studentsDao = new StudentsDaoImpl();
  }

  public List<String> findStudentsByCourse(String course) throws UiException {
    if (!ValidationChecks.checkIfCourseExists(course)) {
      throw new IllegalArgumentException();
    }
    List<String> studentOfThisCourse = new ArrayList<>();
    try {
      List<Student> student = studentsDao.findStudentsByCourse(course);
      student.forEach(s -> studentOfThisCourse
          .add(s.getFirstName() + SPACE + s.getLastName()));
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return studentOfThisCourse;
  }

  public void addNewStudent(String firstName, String lastName, int groupId)
      throws UiException, OperationsException {
    if (groupId < 1 || groupId > 11) {
      throw new IllegalArgumentException();
    }
    if (ValidationChecks.checkIfStudentAlreadyInGroup(groupId, firstName,
        lastName)) {
      throw new OperationsException();
    }

    try {
      studentsDao.addNewStudent(firstName, lastName, groupId);
    } catch (DaoException e) {
      throw new UiException();
    }
  }

  public void deleteStudentById(int id)
      throws UiException, OperationsException {
    if (id < 1) {
      throw new IllegalArgumentException();
    }
    if (!ValidationChecks.checkIfStudentIdExists(id)) {
      throw new OperationsException();
    }
    try {
      studentsDao.deleteStudentById(id);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

}
