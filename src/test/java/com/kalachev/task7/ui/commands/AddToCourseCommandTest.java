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
import org.mockito.Mockito;

import com.kalachev.task7.exceptions.CourseNotFoundException;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.exceptions.StudentNotFoundException;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.initialization.InitializerImpl;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.service.options.CoursesOptions;

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
  void testAddToCourse_shouldCallAllNeedeMethods_whenValidInput()
      throws UiException, CourseNotFoundException, StudentNotFoundException {
    // given
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);
    Mockito
        .when(mockOptions.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
        .thenReturn(false);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    command.execute();
    // then
    verify(mockOptions, times(1)).addStudentToCourse(Integer.parseInt(id),
        course);
  }

  @Test
  void testAddToCourse_shouldPrintAdded_whenValidInput() throws Exception {
    // given
    String expected = "Enter ID of a student you want to add" + NEWLINE + "Eng"
        + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "Student with id 1 added to course Eng";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);
    Mockito
        .when(mockOptions.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
        .thenReturn(false);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldprintWrongName_whenNameWasNotInCourses()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to add" + NEWLINE + "Eng"
        + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "Wrong course name";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn("wrong course");
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldprintWrongInput_whenIdWasNotInt()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to add" + NEWLINE
        + "Your Input was not correct";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("not an int id");
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldPrintWrongId_whenNegativeId() throws Exception {
    // given
    String expected = "Enter ID of a student you want to add" + NEWLINE
        + "id cant be negative";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("-322");
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldPrintStudentNotExist_whenIdIsNotInTable()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to add" + NEWLINE
        + "There is no student with such id";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("5555");
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt()))
        .thenReturn(false);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldPrintAlreadyInCourse_whenStudentInCourse()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to add" + NEWLINE + "Eng"
        + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Enter a name of a course from the list" + NEWLINE
        + "student already in course";
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(id).thenReturn(course);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);
    Mockito
        .when(mockOptions.checkIfStudentAlreadyInCourse(anyInt(), anyString()))
        .thenReturn(true);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testAddToCourse_shouldPrintNoCourses_whenCoursesCorrupted()
      throws Exception {
    // given
    String expected = "No Courses Found";
    Mockito.when(mockOptions.findCourseNames())
        .thenThrow(CourseNotFoundException.class);
    command = new AddToCourseCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }
}
