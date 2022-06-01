package com.kalachev.task7.service.options;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.validations.Validator;
import com.kalachev.task7.utilities.ExceptionsUtil;

public class StudentOptions {

  static final String SPACE = " ";

  StudentsDao studentsDao;

  public StudentOptions(StudentsDao studentsDao) {
    super();
    this.studentsDao = studentsDao;
  }

  public List<String> findByCourse(String course) throws UiException {
    if (!Validator.checkIfCourseExists(course)) {
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

  public void addNewStudent(String firstName, String lastName, int groupId)
      throws UiException {
    if (groupId < 1 || groupId > 11) {
      throw new IllegalArgumentException();
    }
    if (Validator.checkIfStudentAlreadyInGroup(groupId, firstName, lastName)) {
      throw new IllegalArgumentException();
    }

    try {
      studentsDao.insert(firstName, lastName, groupId);
    } catch (DaoException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new UiException(methodName, className);
    }
  }

  public void deleteStudentById(int id) throws UiException {
    if (id < 1) {
      throw new IllegalArgumentException();
    }
    if (!Validator.checkIfStudentIdExists(id)) {
      throw new IllegalArgumentException();
    }
    try {
      studentsDao.delete(id);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

}
