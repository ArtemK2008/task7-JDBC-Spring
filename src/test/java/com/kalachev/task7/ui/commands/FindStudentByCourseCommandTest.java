package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.CoursesOptions;
import com.kalachev.task7.service.options.StudentOptions;

class FindStudentByCourseCommandTest {

  Command command;
  final static String NEWLINE = System.lineSeparator();
  Scanner mockScanner;
  CoursesOptions mockCourseOptions;
  StudentOptions mockStudentOptions;
  String course = "Eng";

  @BeforeEach
  void setUp() {
    mockScanner = Mockito.mock(Scanner.class);
    mockCourseOptions = Mockito.mock(CoursesOptions.class);
    mockStudentOptions = Mockito.mock(StudentOptions.class);
  }

  @Test
  void testFindNames_shouldCallAllNeedeMethods_whenMocked() throws UiException {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    List<String> students = Arrays.asList("a", "b", "c");
    Mockito.when(mockCourseOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(course);
    Mockito.when(mockStudentOptions.findByCourse(course)).thenReturn(students);

    command = new FindStudentsByCourseCommand(mockScanner, mockCourseOptions,
        mockStudentOptions);
    command.execute();
    verify(mockCourseOptions, times(1)).findCourseNames();
    verify(mockStudentOptions, times(1)).findByCourse(course);
  }

  @Test
  void testFindNames_shouldPrintAllCourses_whenValidInput() throws Exception {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    List<String> students = Arrays.asList("a", "b", "c");
    Mockito.when(mockCourseOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(course);
    Mockito.when(mockStudentOptions.findByCourse(course)).thenReturn(students);

    String expected = "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Choose a course to see its students" + NEWLINE + "a" + NEWLINE + "b"
        + NEWLINE + "c";

    command = new FindStudentsByCourseCommand(mockScanner, mockCourseOptions,
        mockStudentOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindNames_shouldPrintNoCourse_whenValidInput() throws Exception {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    List<String> students = Arrays.asList("a", "b", "c");
    Mockito.when(mockCourseOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(course);
    Mockito.when(mockStudentOptions.findByCourse(course)).thenReturn(students);

    String expected = "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Choose a course to see its students" + NEWLINE + "a" + NEWLINE + "b"
        + NEWLINE + "c";

    command = new FindStudentsByCourseCommand(mockScanner, mockCourseOptions,
        mockStudentOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindNames_shouldPrintThatWasNotCorrect_whenInputIsNotACourseFromList()
      throws Exception {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    Mockito.when(mockCourseOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn("not a course");

    String expected = "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Choose a course to see its students" + NEWLINE + "no such course";

    command = new FindStudentsByCourseCommand(mockScanner, mockCourseOptions,
        mockStudentOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindNames_shouldPrintNoCourseFound_whenInputCourseTableCorrupted()
      throws Exception {
    List<String> students = Arrays.asList("a", "b", "c");
    Mockito.when(mockCourseOptions.findCourseNames()).thenThrow(UiException.class);
    Mockito.when(mockScanner.next()).thenReturn(course);
    Mockito.when(mockStudentOptions.findByCourse(course)).thenReturn(students);

    String expected = "No Courses Found";

    command = new FindStudentsByCourseCommand(mockScanner, mockCourseOptions,
        mockStudentOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindNames_shouldPrintNoStudents_whenEmptyCourse() throws Exception {
    List<String> courses = Arrays.asList("Eng", "Rus", "Uk");
    List<String> students = new ArrayList<>();
    Mockito.when(mockCourseOptions.findCourseNames()).thenReturn(courses);
    Mockito.when(mockScanner.next()).thenReturn(course);
    Mockito.when(mockStudentOptions.findByCourse(course)).thenReturn(students);

    String expected = "Eng" + NEWLINE + "Rus" + NEWLINE + "Uk" + NEWLINE
        + "Choose a course to see its students" + NEWLINE
        + "No students in this course";

    command = new FindStudentsByCourseCommand(mockScanner, mockCourseOptions,
        mockStudentOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expected, actual.trim());
  }
}
