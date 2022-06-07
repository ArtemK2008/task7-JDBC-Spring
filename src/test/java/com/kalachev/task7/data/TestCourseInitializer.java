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

import com.kalachev.task7.dao.initialization.CoursesInitializerImpl;
import com.kalachev.task7.dao.initialization_interfaces.CoursesInitializer;

class TestCourseInitializer {
  Map<String, String> courseWithDescription;
  List<String> courses;
  HashMap<String, String> students;
  CoursesInitializer coursesInitializerImpl;

  @BeforeEach
  void fillExpexted() {
    courseWithDescription = new LinkedHashMap<String, String>();
    courseWithDescription.put("English", "place to learn English");
    courseWithDescription.put("Mandarin", "place to learn Mandarin");
    courseWithDescription.put("Hindi", "place to learn Hindi");
    courseWithDescription.put("Spanish", "place to learn Spanish");
    courseWithDescription.put("French", "place to learn French");
    courseWithDescription.put("Arabic", "place to learn Arabic");
    courseWithDescription.put("Bengali", "place to learn Bengali");
    courseWithDescription.put("Russian", "place to learn Russian");
    courseWithDescription.put("Portuguese", "place to learn Portuguese");
    courseWithDescription.put("Ukrainian", "place to learn Ukrainian");

    courses = Arrays.asList("English", "Russian", "Mandarin", "Ukranian");
    students = new HashMap<>();
    students.put("1", "Artem");
    students.put("2", "Ivan");
    students.put("3", "Peter");
  }

  @Test
  void testGenerateCourses_shouldReturnHardcodedValues_whenMethoIsCalled() {
    // given
    Map<String, String> expected = courseWithDescription;
    CoursesInitializer coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    Map<String, String> actual = coursesInitializerImpl.generateCourses();
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testRetrieveInput_shouldReturnCourseNames_whenCoursesExists() {
    // given
    CoursesInitializer coursesInitializerImpl = new CoursesInitializerImpl();
    List<String> expected = new ArrayList<String>();
    expected.add("English");
    expected.add("Mandarin");
    expected.add("Hindi");
    expected.add("Spanish");
    expected.add("French");
    expected.add("Arabic");
    expected.add("Bengali");
    expected.add("Russian");
    expected.add("Portuguese");
    expected.add("Ukrainian");
    // when
    List<String> actual = coursesInitializerImpl
        .retrieveCoursesNames(courseWithDescription);
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testRetrieveInput_shouldThrowException_whenCoursesIsNull() {
    // given
    CoursesInitializer coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    Map<String, String> course = null;
    // then
    assertThrows(IllegalArgumentException.class,
        () -> coursesInitializerImpl.retrieveCoursesNames(course));
  }

  @Test
  void testRetrieveInput_shouldThrowException_whenCoursesIsEmpty() {
    // given
    CoursesInitializer coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    courseWithDescription = new HashMap<String, String>();
    // then
    assertThrows(IllegalArgumentException.class, () -> coursesInitializerImpl
        .retrieveCoursesNames(courseWithDescription));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhenStudentsIsNull() {
    // given
    coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    HashMap<String, String> studentsWithId = null;
    List<String> courses = Arrays.asList("1", "2");
    // then
    assertThrows(IllegalArgumentException.class, () -> coursesInitializerImpl
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhencoursesIsNull() {
    // given
    coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    HashMap<String, String> studentsWithId = new HashMap<String, String>();
    studentsWithId.put("1", "1");
    List<String> courses = null;
    // then
    assertThrows(IllegalArgumentException.class, () -> coursesInitializerImpl
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhenStudentsIsEmpty() {
    // given
    coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    HashMap<String, String> studentsWithId = new HashMap<String, String>();
    List<String> courses = Arrays.asList("1", "2");
    // then
    assertThrows(IllegalArgumentException.class, () -> coursesInitializerImpl
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldThrowExcception_WhencoursesIsEmpty() {
    // given
    coursesInitializerImpl = new CoursesInitializerImpl();
    // when
    HashMap<String, String> studentsWithId = new HashMap<String, String>();
    studentsWithId.put("1", "1");
    List<String> courses = new ArrayList<>();
    // then
    assertThrows(IllegalArgumentException.class, () -> coursesInitializerImpl
        .assignStudentsIdToCourse(studentsWithId, courses));
  }

  @Test
  void testAssignStudents_shouldAssignStudentsCorrectly_whenExpectertResultIsKnown() {
    // given
    coursesInitializerImpl = new CoursesInitializerImpl(5);
    Map<String, List<String>> expected = new LinkedHashMap<>();
    expected.put("1", Arrays.asList("English", "Mandarin", "Russian"));
    expected.put("2", Arrays.asList("Russian", "Ukranian", "Mandarin"));
    expected.put("3", Arrays.asList("Russian"));
    // when
    Map<String, List<String>> actual = coursesInitializerImpl
        .assignStudentsIdToCourse(students, courses);
    // then
    assertEquals(expected, actual);
  }

  @Test
  void testAssignStudents_shouldNotAssignToSameCourse_whenLotsOfTriesWereTaken() {
    // given
    Map<String, List<String>> actual;
    boolean isAnyRepetitions = false;
    // when
    for (int i = 0; i < 200; i++) {
      coursesInitializerImpl = new CoursesInitializerImpl();
      actual = coursesInitializerImpl.assignStudentsIdToCourse(students,
          courses);
      for (String student : actual.keySet()) {
        Set<String> courseSet = new HashSet<>(actual.get(student));
        if (courseSet.size() != actual.get(student).size()) {
          isAnyRepetitions = true;
        }
      }
    }
    // then
    assertFalse(isAnyRepetitions);
  }

  @Test
  void testAssignStudents_shouldAssignToAtLeastOne_whenLotsOfTriesWereTaken() {
    // given
    Map<String, List<String>> actual;
    boolean isStudentWithoutCourse = false;
    // when
    for (int i = 0; i < 200; i++) {
      coursesInitializerImpl = new CoursesInitializerImpl();
      actual = coursesInitializerImpl.assignStudentsIdToCourse(students,
          courses);
      for (String student : actual.keySet()) {
        if (actual.get(student).size() == 0) {
          isStudentWithoutCourse = true;
        }
      }
    }
    // then
    assertFalse(isStudentWithoutCourse);
  }

  @Test
  void testAssignStudents_shouldAssignToAtMaxThree_whenLotsOfTriesWereTaken() {
    // given
    Map<String, List<String>> actual;
    boolean isStudentWithMoreThenThree = false;
    // when
    for (int i = 0; i < 200; i++) {
      coursesInitializerImpl = new CoursesInitializerImpl();
      actual = coursesInitializerImpl.assignStudentsIdToCourse(students,
          courses);
      for (String student : actual.keySet()) {
        if (actual.get(student).size() > 3) {
          isStudentWithMoreThenThree = true;
        }
      }
    }
    // then
    assertFalse(isStudentWithMoreThenThree);
  }

}
