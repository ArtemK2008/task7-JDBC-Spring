package com.kalachev.task7.service.data.idata;

import java.util.List;
import java.util.Map;

public interface CoursesInitializer {

  Map<String, String> generateCourses();

  List<String> retrieveCoursesNames(Map<String, String> courses);

  Map<String, List<String>> assignStudentsIdToCourse(
      Map<String, String> studentsWithId, List<String> courses);

}