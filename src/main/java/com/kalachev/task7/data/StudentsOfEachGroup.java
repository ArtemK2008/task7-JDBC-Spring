package com.kalachev.task7.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class StudentsOfEachGroup {
  Random random;

  public StudentsOfEachGroup() {
    super();
    random = new Random();
  }

  public StudentsOfEachGroup(int seed) {
    super();
    this.random = new Random(seed);
  }

  static final int MAX_GROUP_SIZE = 30;
  static final int MIN_GROUP_SIZE = 10;
  static final String GROUPLESS = "students without groups";

  public Map<String, List<String>> assignStudentsToGroups(List<String> students,
      List<String> groups) {
    checkInput(students, groups);
    Map<String, List<String>> studentsInGroup = prepareEmptyGroups(groups);
    List<Integer> fullGroups = new ArrayList<>();
    for (String student : students) {
      int currRandom = random.nextInt(11);
      while (fullGroups.contains(currRandom)) {
        currRandom = random.nextInt(11);
      }
      String currGroup = groups.get(currRandom);
      List<String> currentStudents = studentsInGroup.get(currGroup);
      if (currentStudents.size() >= MAX_GROUP_SIZE) {
        fullGroups.add(currRandom);
        while (fullGroups.contains(currRandom)) {
          currRandom = random.nextInt(11);
          currentStudents = studentsInGroup.get(groups.get(currRandom));
        }
      }
      currentStudents.add(student);
      studentsInGroup.put(groups.get(currRandom), currentStudents);
    }

    checkForSmallGroup(studentsInGroup);
    return studentsInGroup;
  }

  private void checkInput(List<String> students, List<String> groups) {
    if (students == null || groups == null) {
      throw new IllegalArgumentException();
    }
    if (students.isEmpty() || groups.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  private void checkForSmallGroup(Map<String, List<String>> studentsInGroup) {
    List<String> tooSmallGroup = new ArrayList<>();
    for (Entry<String, List<String>> entry : studentsInGroup.entrySet()) {
      if (entry.getValue().size() < MIN_GROUP_SIZE) {

        entry.getValue().stream().forEach(tooSmallGroup::add);
        studentsInGroup.put(entry.getKey(), new ArrayList<>());
      }
    }

    List<String> groupless = studentsInGroup.get(GROUPLESS);
    for (String s : tooSmallGroup) {
      groupless.add(s);
    }
    studentsInGroup.put(GROUPLESS, groupless);
  }

  private Map<String, List<String>> prepareEmptyGroups(List<String> groups) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    groups.stream().forEach(g -> map.put(g, new ArrayList<>()));
    return map;
  }
}
