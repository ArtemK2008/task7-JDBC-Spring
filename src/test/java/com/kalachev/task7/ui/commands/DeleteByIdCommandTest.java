package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kalachev.task7.initialization.core.InitializerImpl;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.service.options.StudentOptionsImpl;

class DeleteByIdCommandTest {
  Command command;
  final static String NEWLINE = System.lineSeparator();
  String id = "1";
  Scanner mockScanner;
  StudentOptionsImpl mockOptions;
  static Initializer intInitializer = new InitializerImpl();

  @BeforeAll
  static void startUp() {
    intInitializer.initializeTables();
  }

  @BeforeEach
  void setUp() {
    mockScanner = Mockito.mock(Scanner.class);
    mockOptions = Mockito.mock(StudentOptionsImpl.class);
  }

  @Test
  void testDeleteStudent_shouldCallAllNeedeMethods_whenValidInput() {
    // given
    Mockito.when(mockOptions.deleteStudentById(Integer.parseInt(id)))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);
    command = new DeleteByIdCommand(mockScanner, mockOptions);
    // when
    command.execute();
    // then
    verify(mockOptions, times(1)).deleteStudentById(Integer.parseInt(id));
    verify(mockOptions, times(1)).checkIfStudentIdExists(Integer.parseInt(id));
  }

  @Test
  void testDeleteStudent_shouldPrintThatStudentDeleted_whenAllWasValid()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "student with id " + id + " deleted";
    Mockito.when(mockOptions.deleteStudentById(Integer.parseInt(id)))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id);
    Mockito.when(mockOptions.checkIfStudentIdExists(anyInt())).thenReturn(true);
    command = new DeleteByIdCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testDeleteStudent_shouldPrintThatWasNotCorrect_whenIdWasNotAnInt()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Your Input was not correct";
    Mockito.when(mockScanner.next()).thenReturn("not an integer id");
    command = new DeleteByIdCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testDeleteStudent_shouldPrintNoSushStudent_whenIdWasNotInDatabase()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "no such student";
    Mockito.when(mockScanner.next()).thenReturn("322");
    command = new DeleteByIdCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testDeleteStudent_shouldPrintThatWasNotCorrect_whenIdWasNegative()
      throws Exception {
    // given
    String expected = "Enter ID of a student you want to delete" + NEWLINE
        + "Wrong student id";
    Mockito.when(mockScanner.next()).thenReturn("-4");
    command = new DeleteByIdCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

}