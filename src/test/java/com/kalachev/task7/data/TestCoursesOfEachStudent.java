package com.kalachev.task7.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCoursesOfEachStudent {

  List<String> courses;
  HashMap<String, String> students;

  @BeforeEach
  void initialize() {
    courses = Arrays.asList("English", "Russian", "Mandarin", "Ukranian");
    students = new HashMap<>();
    students.put("1", "Artem");
    students.put("2", "Ivan");
    students.put("3", "Peter");
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhenStudentsIsNull() {
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
    HashMap<String, String> studentsWithId = null;
    List<String> courses = Arrays.asList("1", "2");
    assertThrows(IllegalArgumentException.class, () -> coursesOfEachStudent
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhencoursesIsNull() {
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
    HashMap<String, String> studentsWithId = new HashMap<String, String>();
    studentsWithId.put("1", "1");
    List<String> courses = null;
    assertThrows(IllegalArgumentException.class, () -> coursesOfEachStudent
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhenStudentsIsEmpty() {
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
    HashMap<String, String> studentsWithId = new HashMap<String, String>();
    List<String> courses = Arrays.asList("1", "2");
    assertThrows(IllegalArgumentException.class, () -> coursesOfEachStudent
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhencoursesIsEmpty() {
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
    HashMap<String, String> studentsWithId = new HashMap<String, String>();
    studentsWithId.put("1", "1");
    List<String> courses = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> coursesOfEachStudent
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldAssignStudentsCorrectly_whenExpectertResultIsKnown() {
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent(5);
    Map<String, List<String>> expected = new LinkedHashMap<>();
    expected.put("1", Arrays.asList("English", "Mandarin", "Russian"));
    expected.put("2", Arrays.asList("Russian", "Ukranian", "Mandarin"));
    expected.put("3", Arrays.asList("Russian"));
    Map<String, List<String>> actual = coursesOfEachStudent
        .assignStudentsIdToCourse(students, courses);
    assertEquals(expected, actual);
  }

  @Test
  void testAssignStudents_shouldNotAssignToSameCourse_whenLotsOfTriesWereTaken() {
    Map<String, List<String>> actual;
    boolean isAnyRepetitions = false;
    for (int i = 0; i < 200; i++) {
      CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
      actual = coursesOfEachStudent.assignStudentsIdToCourse(students, courses);
      for (String student : actual.keySet()) {
        Set<String> courseSet = new HashSet<>(actual.get(student));
        if (courseSet.size() != actual.get(student).size()) {
          isAnyRepetitions = true;
        }
      }
    }
    assertFalse(isAnyRepetitions);
  }

  @Test
  void testAssignStudents_shouldAssignToAtLeastOne_whenLotsOfTriesWereTaken() {
    Map<String, List<String>> actual;
    boolean isStudentWithoutCourse = false;
    for (int i = 0; i < 200; i++) {
      CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
      actual = coursesOfEachStudent.assignStudentsIdToCourse(students, courses);
      for (String student : actual.keySet()) {
        if (actual.get(student).size() == 0) {
          isStudentWithoutCourse = true;
        }
      }
    }
    assertFalse(isStudentWithoutCourse);
  }

  @Test
  void testAssignStudents_shouldAssignToAtMaxThree_whenLotsOfTriesWereTaken() {
    Map<String, List<String>> actual;
    boolean isStudentWithMoreThenThree = false;
    for (int i = 0; i < 200; i++) {
      CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
      actual = coursesOfEachStudent.assignStudentsIdToCourse(students, courses);
      for (String student : actual.keySet()) {
        if (actual.get(student).size() > 3) {
          isStudentWithMoreThenThree = true;
        }
      }
    }
    assertFalse(isStudentWithMoreThenThree);
  }

}
