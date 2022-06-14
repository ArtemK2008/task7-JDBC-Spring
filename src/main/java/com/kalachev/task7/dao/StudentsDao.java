package com.kalachev.task7.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kalachev.task7.dao.entities.Student;

@Component
public interface StudentsDao {

  Map<String, String> studentNamesById();

  List<Student> findByCourse(String courseName);

  boolean insert(String firstName, String lastName, int groupId);

  boolean delete(int id);

  boolean isExistsInGroup(String firstName, String lastName, int groupId);

  boolean isIdExists(int id);

  boolean checkIfStudentInCourse(int studentId, String course);
}
