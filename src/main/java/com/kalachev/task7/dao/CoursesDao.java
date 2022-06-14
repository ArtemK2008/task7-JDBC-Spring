package com.kalachev.task7.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kalachev.task7.dao.entities.Course;

@Component
public interface CoursesDao {

  boolean addStudent(int studentId, String course);

  boolean removeStudent(int studentId, String course);

  List<Course> getAll();

  List<Course> getById(int studentId);

  boolean isExists(String course);

}