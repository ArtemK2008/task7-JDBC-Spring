package com.kalachev.task7.initialization.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.kalachev.task7.initialization.GroupInitializer;

@Component
public class GroupInitializerImpl implements GroupInitializer {
  Random random;
  private static final String GROUPLESS = "students without groups";
  static final int MAX_GROUP_SIZE = 30;
  static final int MIN_GROUP_SIZE = 10;

  public GroupInitializerImpl() {
    super();
    random = new Random();
  }

  public GroupInitializerImpl(int seed) {
    super();
    this.random = new Random(seed);
  }

  @Override
  public List<String> generateGroups() {
    List<String> groups = new ArrayList<>();
    List<String> groupsUsed = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String tempString = generateGroupName();
      while (groupsUsed.contains(tempString)) {
        tempString = generateGroupName();
      }
      groupsUsed.add(tempString);
      groups.add(tempString);
    }
    groups.add(GROUPLESS);
    return groups;
  }

  private String generateCharPart() {
    int leftLimit = 97;
    int rightLimit = 122;
    int targetStringLength = 2;
    return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();
  }

  private String generateNumberPart() {
    int leftLimit = 48;
    int rightLimit = 57;
    int targetStringLength = 2;
    return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();
  }

  @Override
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
      }
      while (fullGroups.contains(currRandom)) {
        currRandom = random.nextInt(11);
      }
      currentStudents = studentsInGroup.get(groups.get(currRandom));
      currentStudents.add(student);
      if (currentStudents.size() == 30) {
        fullGroups.add(currRandom);
      }
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

  private String generateGroupName() {
    return generateCharPart() + "-" + generateNumberPart();
  }

}
