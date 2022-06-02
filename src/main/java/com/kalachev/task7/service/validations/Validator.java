package com.kalachev.task7.service.validations;

import com.kalachev.task7.dao.implementations.DaoChecksImpl;
import com.kalachev.task7.dao.interfaces.DaoChecks;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.utilities.ExceptionsUtil;

public class Validator {

  static DaoChecks dao = new DaoChecksImpl();

  private Validator() {
    super();
  }

  public static boolean checkIfCourseExists(String course) throws UiException {
    boolean isExist = false;
    try {
      if (dao.checkCourseIfExists(course)) {
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

  public static boolean checkIfStudentAlreadyInGroup(int groupId,
      String firstName, String lastName) throws UiException {
    boolean isInGroup = false;
    try {
      if (dao.checkStudntIfExistsInGroup(firstName, lastName, groupId)) {
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

  public static boolean checkIfStudentIdExists(int id) throws UiException {
    boolean isExist = false;
    try {
      if (dao.checkStudentIdIfExists(id)) {
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

  public static boolean checkIfStudentAlreadyInCourse(int id, String course)
      throws UiException {
    boolean isExist = false;
    try {
      if (dao.checkIfStudentInCourse(id, course)) {
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
