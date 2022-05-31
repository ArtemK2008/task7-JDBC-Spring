package com.kalachev.task7.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.exceptions.DaoException;

public interface StudentsDao {

  Map<String, String> retrieveStudentsId() throws DaoException;

  List<Student> findStudentsByCourse(String courseName) throws DaoException;

  void addNewStudent(String firstName, String lastName, int groupId)
      throws DaoException;

  void deleteStudentById(int id) throws DaoException;
}
