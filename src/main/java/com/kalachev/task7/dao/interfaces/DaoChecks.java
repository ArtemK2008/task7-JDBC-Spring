package com.kalachev.task7.dao.interfaces;

import com.kalachev.task7.exceptions.DaoException;

public interface DaoChecks {

  boolean checkCourseIfExists(String course) throws DaoException;

  boolean checkStudntIfExistsInGroup(String firstName, String lastName,
      int groupId) throws DaoException;

  boolean checkStudentIdIfExists(int id) throws DaoException;

  boolean checkIfStudentInCourse(int studentId, String course)
      throws DaoException;

}