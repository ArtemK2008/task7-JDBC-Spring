package userMenu;

import java.util.ArrayList;
import java.util.List;

import javax.management.OperationsException;

import com.kalachev.task7.dao.GroupsDao;
import com.kalachev.task7.dao.GroupsDaoImpl;
import com.kalachev.task7.dao.UserOptionsDao;
import com.kalachev.task7.entities.Groups;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;

public class UserOptions {
  static final String NOT_EXIST = "no such student id";

  UserOptionsDao dao = new UserOptionsDao();

  public List<Groups> findGroupsBySize(int maxSize) throws UiException {
    if (maxSize < 0) {
      throw new IllegalArgumentException();
    }
    List<Groups> groups = new ArrayList<>();
    try {
      GroupsDao groupsDaoImpl = new GroupsDaoImpl();
      groups = groupsDaoImpl.findGroupsBySize(maxSize);
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }

    if (groups.isEmpty()) {
      throw new UiException();
    }
    return groups;
  }

  public List<String> findStudentsByCourse(String course) throws UiException {
    if (!checkIfCourseExists(course)) {
      throw new IllegalArgumentException();
    }
    List<String> studentOfThisCourse = new ArrayList<>();
    try {
      studentOfThisCourse = dao.findStudentsByCourse(course);
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
      dao.addNewStudent(firstName, lastName, groupId);
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
      dao.deleteStudentById(id);
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
      dao.addStudentToCourse(studentId, course);
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
      dao.removeStudentFromCourse(studentId, course);
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
  }

  public List<String> findCourseNames() throws UiException {
    List<String> courses = new ArrayList<>();
    try {
      courses = dao.retrieveCoursesNames();
      if (courses.isEmpty()) {
        throw new UiException();
      }
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return courses;
  }

  public List<String> retrieveCourseNamesByID(int id) throws UiException {
    List<String> courses = new ArrayList<>();
    try {
      courses = dao.retrieveCoursesNamesById(id);
    } catch (DaoException e) {
      e.printStackTrace();
      throw new UiException();
    }
    return courses;
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
