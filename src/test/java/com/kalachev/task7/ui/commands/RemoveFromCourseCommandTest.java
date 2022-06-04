package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.validations.Validator;

class RemoveFromCourseCommandTest {

  Command command;
  final static String NEWLINE = System.lineSeparator();
  Scanner mockScanner;
  CoursesOptions mockOptions;
  String id = "1";
  String course = "Eng";

  @BeforeEach
  void setUp() {
    mockScanner = Mockito.mock(Scanner.class);
    mockOptions = Mockito.mock(CoursesOptions.class);
  }

  @Test
  void testRemoveFromCourse_shouldCallAllNeedeMethods_whenMocked()
      throws NumberFormatException, UiException {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNamesByID(Integer.parseInt(id)))
        .thenReturn(courses);
    Mockito
        .when(mockOptions.removeStudentFromCourse(Integer.parseInt(id), course))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(true);
      validator.when(
          () -> Validator.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
          .thenReturn(true);

      command = new RemoveFromCourseCommand(mockScanner, mockOptions);
      command.execute();
      verify(mockOptions, times(1))
          .removeStudentFromCourse(Integer.parseInt(id), course);
      verify(mockOptions, times(1)).findCourseNamesByID(Integer.parseInt(id));
    }
  }

  @Test
  void testRemoveFromCourse_shouldPrintRemoved_whenValidInput()
      throws Exception {
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "Student with id 1 removed from Eng";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNamesByID(Integer.parseInt(id)))
        .thenReturn(courses);
    Mockito
        .when(mockOptions.removeStudentFromCourse(Integer.parseInt(id), course))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(true);
      validator.when(
          () -> Validator.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
          .thenReturn(true);

      command = new RemoveFromCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testRemoveFromCourse_shouldPrintWrongName_whenCourseNotInTable()
      throws Exception {
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "Wrong course name";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNamesByID(Integer.parseInt(id)))
        .thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id)
        .thenReturn("wrong course name");

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(true);
      command = new RemoveFromCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testRemoveFromCourse_shouldPrintBadInput_whenIdIsNotInt()
      throws Exception {
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Your Input was not correct";
    Mockito.when(mockScanner.next()).thenReturn("this is not an int");
    command = new RemoveFromCourseCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testRemoveFromCourse_shouldPrintWrongId_whenNegativeId()
      throws Exception {
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Wrong id";
    Mockito.when(mockScanner.next()).thenReturn("-3222");
    command = new RemoveFromCourseCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testRemoveFromCourse_shouldPrintNoStudent_whenNoStudentId()
      throws Exception {
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "There is no student with such id";
    Mockito.when(mockScanner.next()).thenReturn(id);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(false);
      command = new RemoveFromCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testRemoveFromCourse_shouldPrintNotInCourse_whenNoStudentInCourse()
      throws Exception {
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "no such student in this course";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNamesByID(Integer.parseInt(id)))
        .thenReturn(courses);
    Mockito
        .when(mockOptions.removeStudentFromCourse(Integer.parseInt(id), course))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(true);
      validator.when(
          () -> Validator.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
          .thenReturn(false);
      command = new RemoveFromCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }
}
