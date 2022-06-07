package com.kalachev.task7.initialization.initialization_interfaces;

import java.util.List;
import java.util.Map;

public interface GroupInitializer {

  List<String> generateGroups();

  Map<String, List<String>> assignStudentsToGroups(List<String> students,
      List<String> groups);

}