package com.kalachev.task7.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.kalachev.task7.service.data.StudentInitializer;

class TestStudentInitializer {
  List<String> expected = Arrays.asList("Gwen Kuleshova", "Jane Ivanova",
      "Mike Sidorov", "Viktoria Lermontova", "Artem Trump", "Gomer Smith",
      "Wendy Petrova", "Gale Nosova", "Wendy Holodnaya", "Nikolay Tesla",
      "Viktoria Gorbacheva", "Ilon Dashkivich", "Sonya Morozova",
      "Peter Makarevich");

  static final String MUSK = "Ilon Musk";

  @Test
  void testGenerateStudents_shouldCreateRandomStudentsNames_whenCalledWithKnownSeed() {
    StudentInitializer studentInitializer = new StudentInitializer(1);
    List<String> students = studentInitializer.generateStudents();
    assertEquals(200, students.size());

    Set<String> uniqStudents = new HashSet<String>(students);
    assertEquals(123, uniqStudents.size());

    assertFalse(uniqStudents.contains(MUSK));

    assertTrue(uniqStudents.containsAll(expected));
  }

  @Test
  void testGenerateStudents_shouldCreateRandomStudentsNames_whenCalledWithOtherSeed() {
    StudentInitializer studentInitializer = new StudentInitializer(2);
    List<String> students = studentInitializer.generateStudents();
    assertEquals(200, students.size());

    Set<String> uniqStudents = new HashSet<String>(students);
    assertEquals(126, uniqStudents.size());

    assertTrue(uniqStudents.contains(MUSK));
    assertFalse(uniqStudents.containsAll(expected));
  }
}
