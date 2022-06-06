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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.kalachev.task7.dao.initialization.Initializer;
import com.kalachev.task7.dao.initialization.InitializerImpl;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.validations.Validator;

class AddToCourseCommandTest {

  Command command;
  final static String NEWLINE = System.lineSeparator();
  Scanner mockScanner;
  CoursesOptions mockOptions;
  String id = "1";
  String course = "Eng";
  static Initializer intInitializer = new InitializerImpl();

  @BeforeAll
  static void startUp() throws DaoException {
    intInitializer.initializeTables();
  }

  @BeforeEach
  void setUp() {
    mockScanner = Mockito.mock(Scanner.class);
    mockOptions = Mockito.mock(CoursesOptions.class);
  }

  @Test
  void testAddToCourse_shouldCallAllNeedeMethods_whenMocked()
      throws NumberFormatException, UiException {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(
          () -> Validator.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
          .thenReturn(false);

      command = new AddToCourseCommand(mockScanner, mockOptions);
      command.execute();
      verify(mockOptions, times(1)).addStudentToCourse(Integer.parseInt(id),
          course);
    }
  }

  @Test
  void testAddToCourse_shouldPrintAdded_whenValidInput() throws Exception {
    String expected = "Enter ID of a student you want to add" + NEWLINE + "Eng"
        + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "Student with id 1 added to course Eng";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(
          () -> Validator.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
          .thenReturn(false);

      command = new AddToCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testAddToCourse_shouldprintWrongName_whenNameWasNotInCourses()
      throws Exception {
    String expected = "Enter ID of a student you want to add" + NEWLINE + "Eng"
        + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "Wrong course name";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn("wrong course");
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      command = new AddToCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testAddToCourse_shouldprintWrongInput_whenIdWasNotInt()
      throws Exception {
    String expected = "Enter ID of a student you want to add" + NEWLINE
        + "Your Input was not correct";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("not an int id");

    command = new AddToCourseCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldPrintWrongId_whenNegativeId() throws Exception {
    String expected = "Enter ID of a student you want to add" + NEWLINE
        + "id cant be negative";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("-322");
    command = new AddToCourseCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldPrintStudentNotExist_whenIdIsNotInTable()
      throws Exception {
    String expected = "Enter ID of a student you want to add" + NEWLINE
        + "There is no student with such id";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("5555");
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt()))
        .thenReturn(false);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      command = new AddToCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testAddToCourse_shouldPrintAlreadyInCourse_whenStudentInCourse()
      throws Exception {
    String expected = "Enter ID of a student you want to add" + NEWLINE + "Eng"
        + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "student already in course";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(
          () -> Validator.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
          .thenReturn(true);
      command = new AddToCourseCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expected, actual.trim());
    }
  }

  @Test
  void testAddToCourse_shouldPrintNoCourses_whenCoursesCorrupted()
      throws Exception {
    String expected = "No Courses Found";
    Mockito.when(mockOptions.findCourseNames()).thenThrow(UiException.class);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }
}
