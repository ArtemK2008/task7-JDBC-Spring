package com.kalachev.task7.dao.interfaces;

import com.kalachev.task7.exceptions.DaoException;

public interface DaoChecks {

  boolean checkIfStudentInCourse(int studentId, String course)
      throws DaoException;

}