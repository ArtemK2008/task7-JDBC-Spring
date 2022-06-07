package com.kalachev.task7.service.data.idata;

import java.util.List;
import java.util.Map;

public interface CourseInitializer {

  List<String> generateGroups();

  Map<String, List<String>> assignStudentsToGroups(List<String> students,
      List<String> groups);

}