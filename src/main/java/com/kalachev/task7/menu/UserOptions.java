package com.kalachev.task7.menu;

import java.util.ArrayList;
import java.util.List;

import javax.management.OperationsException;

import com.kalachev.task7.dao.CoursesDaoImpl;
import com.kalachev.task7.dao.DaoChecks;
import com.kalachev.task7.dao.GroupsDaoImpl;
import com.kalachev.task7.dao.StudentsDaoImpl;
import com.kalachev.task7.dao.daoInterfaces.CoursesDao;
import com.kalachev.task7.dao.daoInterfaces.GroupsDao;
import com.kalachev.task7.dao.daoInterfaces.StudentsDao;
import com.kalachev.task7.entities.Course;
import com.kalachev.task7.entities.Group;
import com.kalachev.task7.entities.Student;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;

public class UserOptions {
  static final String NOT_EXIST = "no such student id";
  static final String SPACE = " ";

  DaoChecks dao = new DaoChecks();
  StudentsDao studentsDao = new StudentsDaoImpl();
  CoursesDao coursesDao = new CoursesDaoImpl();

  public List<String> findGroupsBySize(int maxSize) throws UiException {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Group> group = new ArrayList<>();
    List<String> groupNames = new ArrayList<>();
    try {
      GroupsDao groupsDaoImpl = new GroupsDaoImpl();
      group = groupsDaoImpl.findGroupsBySize(maxSize);
      group.forEach(g -> groupNames.add(g.getGroupName()));
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }

    if (group.isEmpty()) {
      throw new UiException();
    }
    return groupNames;
  }

  public List<String> findStudentsByCourse(String course) throws UiException {
    if (!checkIfCourseExists(course)) {
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
    if (checkIfStudentAlreadyInGroup(groupId, firstName, lastName)) {
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
    if (!checkIfStudentIdExists(id)) {
      throw new OperationsException();
    }
    try {
      studentsDao.deleteStudentById(id);
    } catch (DaoException e) {
      e.printStackTrace();
    }
  }

  public void addStudentToCourse(int studentId, String course)
      throws UiException, OperationsException {
    if (studentId < 0) {
      throw new IllegalArgumentException();
    }

    if (!checkIfCourseExists(course)) {
      throw new UiException();
    }

    if (!checkIfStudentIdExists(studentId)) {
      throw new UiException();
    }
    if (checkIfStudentAlreadyInCourse(studentId, course)) {
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
    if (studentId < 0) {
      throw new IllegalArgumentException();
    }
    if (!checkIfCourseExists(course)) {
      throw new UiException();
    }
    if (!checkIfStudentIdExists(studentId)) {
      throw new UiException();
    }
    if (!checkIfStudentAlreadyInCourse(studentId, course)) {
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

  private boolean checkIfStudentAlreadyInCourse(int id, String course)
      throws UiException {
    boolean isExist = false;
    try {
      if (dao.checkIfStudentInCourse(id, course)) {
        isExist = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return isExist;
  }

  public boolean checkIfStudentIdExists(int id) throws UiException {
    boolean isExist = false;
    try {
      if (dao.checkStudentIdIfExists(id)) {
        isExist = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return isExist;
  }

  public boolean checkIfCourseExists(String course) throws UiException {
    boolean isExist = false;
    try {
      if (dao.checkCourseIfExists(course)) {
        isExist = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return isExist;
  }

  private boolean checkIfStudentAlreadyInGroup(int groupId, String firstName,
      String lastName) throws UiException {
    boolean isInGroup = false;
    try {
      if (dao.checkStudntIfExistsInGroup(firstName, lastName, groupId)) {
        isInGroup = true;
      }
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return isInGroup;
  }
}
