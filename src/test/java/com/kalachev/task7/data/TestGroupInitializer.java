package com.kalachev.task7.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.initialization.GroupInitializer;
import com.kalachev.task7.initialization.StudentInitializer;
import com.kalachev.task7.initialization.impl.GroupInitializerImpl;
import com.kalachev.task7.initialization.impl.StudentInitializerImpl;

class TestGroupInitializer {
  static List<String> groups;
  static List<String> students;
  GroupInitializer gc;
  StudentInitializer sc;

  private static final String GROUPLESS = "students without groups";
  List<String> expected = Arrays.asList("ra-73", "my-46", "wk-93", "nf-24",
      "ge-69", "eo-09", "ez-83", "zs-54", "qc-35", "tg-54", GROUPLESS);

  @BeforeEach
  public void createGroupsAndStudents() {
    gc = new GroupInitializerImpl(1);
    sc = new StudentInitializerImpl(1);
    groups = gc.generateGroups();
    students = sc.generateStudents();
  }

  @Test
  void testCreateGroups_shouldCreateElevenDifferentGroups_whenMethodIsCalled() {
    // given
    int groupSize = 11;
    // then
    assertEquals(groupSize, groups.size());
    assertTrue(groups.contains(GROUPLESS));
    assertEquals(expected, groups);
  }

  @Test
  void testCreateGroups_shouldCreateUniqueGroupsOnly_whenMethodIsCalledLotOfTimes() {
    // given
    gc = new GroupInitializerImpl();
    boolean isRightSize = true;
    // when
    for (int i = 0; i < 1000; i++) {
      List<String> groups = gc.generateGroups();
      Set<String> uniqGrousp = new HashSet<String>(groups);
      if (uniqGrousp.size() != 11) {
        isRightSize = false;
      }
    }
    // then
    assertTrue(isRightSize);
  }

  @Test
  void testAssignStudents_shouldPopulateGroupsWithValidStudentNumbers_whenDataIsGeneratedLotsOfTimes() {
    // given
    gc = new GroupInitializerImpl();
    sc = new StudentInitializerImpl();
    int minInGroup = 10;
    int maxInGroup = 30;
    boolean isGroupSizeValid = true;
    // when
    for (int i = 0; i < 200; i++) {
      groups = gc.generateGroups();
      students = sc.generateStudents();
      Map<String, List<String>> groupsWithStudentsIn = gc
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
    // then
    assertTrue(isGroupSizeValid);
  }

  @Test
  void testAssignStudents_shouldPopulateAllGroupsExceptSmall_whenOneGroupIsTooSmall() {
    // given
    gc = new GroupInitializerImpl(63);
    final String smallGroup = "nf-24";
    int expectedSize = 0;
    // when
    Map<String, List<String>> groupsWithStudentsIn = gc
        .assignStudentsToGroups(students, groups);
    int actualSize = groupsWithStudentsIn.get(smallGroup).size();
    // then
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void testAssignStudents_shouldThrowException_whenStudentsIsNull() {
    // when
    List<String> students = null;
    // then
    assertThrows(IllegalArgumentException.class,
        () -> gc.assignStudentsToGroups(students, groups));
  }

  @Test
  void testAssignStudents_shouldThrowException_whenGroupsIsNull() {
    // when
    List<String> groups = null;
    // then
    assertThrows(IllegalArgumentException.class,
        () -> gc.assignStudentsToGroups(students, groups));
  }

  @Test
  void testAssignStudents_shouldThrowException_whenGroupsIsEmpty() {
    // when
    groups = new ArrayList<>();
    // then
    assertThrows(IllegalArgumentException.class,
        () -> gc.assignStudentsToGroups(students, groups));
  }

  @Test
  void testAssignStudents_shouldThrowException_whenStudentsIsEmpty() {
    // when
    students = new ArrayList<>();
    // then
    assertThrows(IllegalArgumentException.class,
        () -> gc.assignStudentsToGroups(students, groups));
  }
}
