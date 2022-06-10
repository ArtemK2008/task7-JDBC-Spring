package com.kalachev.task7.service.options;

import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.service.options_interfaces.CoursesOptions;

public class CoursesOptionsImpl implements CoursesOptions {

  CoursesDao coursesDao;
  StudentsDao studentsDao;

  public CoursesOptionsImpl(CoursesDao coursesDao, StudentsDao studentsDao) {
    this.coursesDao = coursesDao;
    this.studentsDao = studentsDao;
  }

  @Override
  public boolean addStudentToCourse(int studentId, String course) {
    boolean isAdded = true;
    if (!validateInput(studentId, course)) {
      return false;
    }
    if (checkIfStudentAlreadyInCourse(studentId, course)) {
      return false;
    }
    coursesDao.addStudent(studentId, course);
    return isAdded;
  }

  @Override
  public boolean removeStudentFromCourse(int studentId, String course) {

    boolean isRemoved = false;
    if (!validateInput(studentId, course)) {
      return false;
    }
    if (!checkIfStudentAlreadyInCourse(studentId, course)) {
      return false;
    }
    coursesDao.removeStudent(studentId, course);
    isRemoved = true;
    return isRemoved;
  }

  @Override
  public List<String> findCourseNames() {
    List<Course> courses = coursesDao.getAll();
    return courses.stream().map(Course::getCourseName)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> findCourseNamesByID(int id) {
    List<Course> courses = coursesDao.getById(id);
    return courses.stream().map(Course::getCourseName)
        .collect(Collectors.toList());
  }

  @Override
  public boolean checkIfStudentIdExists(int id) {
    boolean isExist = false;
    if (studentsDao.isIdExists(id)) {
      isExist = true;
    }
    return isExist;
  }

  private boolean checkIfCourseExists(String course) {
    boolean isExist = false;
    if (coursesDao.isExists(course)) {
      isExist = true;
    }
    return isExist;
  }

  private boolean validateInput(int studentId, String course) {
    boolean isExist = true;
    if (studentId < 0) {
      isExist = false;
    }
    if (!checkIfCourseExists(course)) {
      isExist = false;
    }
    if (!studentsDao.isIdExists(studentId)) {
      isExist = false;
    }
    return isExist;
  }

  @Override
  public boolean checkIfStudentAlreadyInCourse(int id, String course) {
    boolean isExist = false;
    if (studentsDao.checkIfStudentInCourse(id, course)) {
      isExist = true;
    }
    return isExist;
  }

}
