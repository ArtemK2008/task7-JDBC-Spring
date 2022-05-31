package com.kalachev.task7.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.service.data.GroupCreator;
import com.kalachev.task7.service.data.StudentCreator;
import com.kalachev.task7.service.data.StudentsOfEachGroup;

class TestStudentsOfEachGroup {
  static List<String> groups;
  static List<String> students;
  private static final String GROUPLESS = "students without groups";

  @BeforeEach
  public void createGroupsAndStudents() {
    GroupCreator gc = new GroupCreator(1);
    StudentCreator sc = new StudentCreator(1);
    groups = gc.generateGroups();
    students = sc.generateStudents();
  }

  @Test
  void testAssignStudents_shouldPopulateGroupsWithValidStudentNumbers_whenDataIsGeneratedLotsOfTimes() {
    StudentsOfEachGroup studentOfGroup = new StudentsOfEachGroup();
    GroupCreator gc = new GroupCreator();
    StudentCreator sc = new StudentCreator();
    int minInGroup = 10;
    int maxInGroup = 30;
    boolean isGroupSizeValid = true;
    for (int i = 0; i < 200; i++) {
      groups = gc.generateGroups();
      students = sc.generateStudents();
      Map<String, List<String>> groupsWithStudentsIn = studentOfGroup
          .assignStudentsToGroups(students, groups);
      for (Entry<String, List<String>> entry : groupsWithStudentsIn
          .entrySet()) {
        if (entry.getValue().size() < minInGroup
            || entry.getValue().size() > maxInGroup) {
          if (!entry.getKey().equals(GROUPLESS)) {
            if (entry.getValue().size() != 0) {
              isGroupSizeValid = false;
            }
          }
        }
      }
    }
    assertTrue(isGroupSizeValid);
  }

  @Test
  void testAssignStudents_shouldPopulateAllGroupsExceptSmall_whenOneGroupIsTooSmall() {
    StudentsOfEachGroup studentOfGroup = new StudentsOfEachGroup(63);
    Map<String, List<String>> groupsWithStudentsIn = studentOfGroup
        .assignStudentsToGroups(students, groups);
    final String smallGroup = "nf-24";
    int actualSize = groupsWithStudentsIn.get(smallGroup).size();
    int expectedSize = 0;
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testAssignStudents_shouldThrowException_whenStudentsIsNull() {
    StudentsOfEachGroup studentOfGroup = new StudentsOfEachGroup();
    assertThrows(IllegalArgumentException.class,
        () -> studentOfGroup.assignStudentsToGroups(null, groups));
  }

  @Test
  void testAssignStudents_shouldThrowException_whenGroupsIsNull() {
    StudentsOfEachGroup studentOfGroup = new StudentsOfEachGroup();
    assertThrows(IllegalArgumentException.class,
        () -> studentOfGroup.assignStudentsToGroups(students, null));
  }

  @Test
  void testAssignStudents_shouldThrowException_whenGroupsIsEmpty() {
    StudentsOfEachGroup studentOfGroup = new StudentsOfEachGroup();
    groups = new ArrayList<>();
    assertThrows(IllegalArgumentException.class,
        () -> studentOfGroup.assignStudentsToGroups(students, groups));
  }

  @Test
  void testAssignStudents_shouldThrowException_whenStudentsIsEmpty() {
    StudentsOfEachGroup studentOfGroup = new StudentsOfEachGroup();
    students = new ArrayList<>();
    assertThrows(IllegalArgumentException.class,
        () -> studentOfGroup.assignStudentsToGroups(students, groups));
  }
}
