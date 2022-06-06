package com.kalachev.task7.dao.interfaces;

import java.util.List;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.exceptions.DaoException;

public interface CoursesDao {

  void addStudent(int studentId, String course) throws DaoException;

  void removeStudent(int studentId, String course) throws DaoException;

  List<Course> getAll() throws DaoException;

  List<Course> getById(int studentId) throws DaoException;

  boolean isExists(String course) throws DaoException;

}