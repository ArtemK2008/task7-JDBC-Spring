package com.kalachev.task7.service.options_interfaces;

import java.util.List;

public interface StudentOptions {

  List<String> findByCourse(String course);

  boolean addNewStudent(String firstName, String lastName, int groupId);

  boolean deleteStudentById(int id);

  boolean checkIfStudentAlreadyInGroup(int groupId, String firstName,
      String lastName);

  boolean checkIfStudentIdExists(int id);

}