package com.kalachev.task7.service.options;

import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;

public class StudentOptions {

  static final String SPACE = " ";

  StudentsDao studentsDao;
  CoursesDao coursesDao;

  public StudentOptions(StudentsDao studentsDao, CoursesDao coursesDao) {
    super();
    this.studentsDao = studentsDao;
    this.coursesDao = coursesDao;
  }

  public List<String> findByCourse(String course) {
    if (!checkIfCourseExists(course)) {
      throw new IllegalArgumentException();
    }
    List<Student> students = studentsDao.findByCourse(course);
    return students.stream()
        .map(s -> s.getFirstName() + SPACE + s.getLastName())
        .collect(Collectors.toList());
  }

  public boolean addNewStudent(String firstName, String lastName, int groupId) {
    boolean isAdded = false;
    if (groupId < 1 || groupId > 11) {
      return false;
    }
    if (checkIfStudentAlreadyInGroup(groupId, firstName, lastName)) {
      return false;
    }
    if (studentsDao.insert(firstName, lastName, groupId)) {
      isAdded = true;
    }
    return isAdded;
  }

  public boolean deleteStudentById(int id) {
    boolean isDeleted = false;
    if (id < 1) {
      return false;
    }
    if (!studentsDao.isIdExists(id)) {
      return false;
    }
    if (studentsDao.delete(id)) {
      isDeleted = true;
    }
    return isDeleted;
  }

  public boolean checkIfStudentAlreadyInGroup(int groupId, String firstName,
      String lastName) {
    boolean isInGroup = false;
    if (studentsDao.isExistsInGroup(firstName, lastName, groupId)) {
      isInGroup = true;
    }
    return isInGroup;
  }

  private boolean checkIfCourseExists(String course) {
    boolean isExist = false;
    if (coursesDao.isExists(course)) {
      isExist = true;
    }
    return isExist;
  }

  public boolean checkIfStudentIdExists(int id) {
    boolean isExist = false;
    if (studentsDao.isIdExists(id)) {
      isExist = true;
    }
    return isExist;
  }
}
