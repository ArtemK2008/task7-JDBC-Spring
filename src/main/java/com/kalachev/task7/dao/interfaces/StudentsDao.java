package com.kalachev.task7.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.exceptions.DaoException;

public interface StudentsDao {

  Map<String, String> studentNamesById() throws DaoException;

  List<Student> findByCourse(String courseName) throws DaoException;

  void insert(String firstName, String lastName, int groupId)
      throws DaoException;

  void delete(int id) throws DaoException;

  boolean isExistsInGroup(String firstName, String lastName, int groupId)
      throws DaoException;

  boolean isIdExists(int id) throws DaoException;

  boolean checkIfStudentInCourse(int studentId, String course)
      throws DaoException;
}
