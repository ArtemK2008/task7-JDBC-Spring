package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.kalachev.task7.service.options.StudentOptions;
import com.kalachev.task7.service.validations.Validator;

public class AddStudentCommandTest {
  Command command;
  final static String NEWLINE = System.lineSeparator();
  String name = "b";
  String lastname = "b";
  String group = "1";
  Scanner mockScanner;
  StudentOptions mockOptions;
  static Initializer intInitializer = new InitializerImpl();

  @BeforeAll
  static void startUp() throws DaoException {
    intInitializer.initializeTables();
  }

  @BeforeEach
  void setUp() {
    mockOptions = Mockito.mock(StudentOptions.class);
    mockScanner = Mockito.mock(Scanner.class);
  }

  @Test
  void testAddStudent_shouldCallAllNeedeMethods_whenMocked()
      throws NumberFormatException, UiException {
    Mockito.when(mockScanner.next()).thenReturn(name).thenReturn(lastname)
        .thenReturn(group);
    Mockito
        .when(mockOptions.addNewStudent(name, lastname, Integer.valueOf(group)))
        .thenReturn(true);
    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentAlreadyInGroup(anyInt(),
          anyString(), anyString())).thenReturn(false);
      command = new AddStudentCommand(mockScanner, mockOptions);
      command.execute();
      verify(mockOptions, times(1)).addNewStudent(name, lastname,
          Integer.valueOf(group));
    }
  }

  @Test
  void testAddStudent_shouldPrintThatStudentAdded_whenAllWasValid()
      throws Exception {
    String expexted = "Enter student name" + NEWLINE + "Enter student last name"
        + NEWLINE + "Enter group id to add this student" + NEWLINE
        + "Student b b added to group 1";

    Mockito.when(mockScanner.next()).thenReturn(name).thenReturn(lastname)
        .thenReturn(group);
    Mockito
        .when(mockOptions.addNewStudent(name, lastname, Integer.valueOf(group)))
        .thenReturn(true);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentAlreadyInGroup(anyInt(),
          anyString(), anyString())).thenReturn(false);
      command = new AddStudentCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expexted, actual.trim());
    }
  }

  @Test
  void testAddStudent_shouldPrintThatWasNotCorrect_whenIdWasNotAnInt()
      throws Exception {
    String expexted = "Enter student name" + NEWLINE + "Enter student last name"
        + NEWLINE + "Enter group id to add this student" + NEWLINE
        + "Your Input was not correct";
    Mockito.when(mockScanner.next()).thenReturn(name).thenReturn(lastname)
        .thenReturn("not an integer id");
    command = new AddStudentCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expexted, actual.trim());
  }

  @Test
  void testAddStudent_shouldPrintWrongGroupId_whenIdWasOutOfRange()
      throws Exception {
    String expexted = "Enter student name" + NEWLINE + "Enter student last name"
        + NEWLINE + "Enter group id to add this student" + NEWLINE
        + "Wrong groupd id";
    Mockito.when(mockScanner.next()).thenReturn(name).thenReturn(lastname)
        .thenReturn("322");
    command = new AddStudentCommand(mockScanner, mockOptions);
    String actual = tapSystemOut(() -> command.execute());
    assertEquals(expexted, actual.trim());
  }

}
