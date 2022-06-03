package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.StudentOptions;
import com.kalachev.task7.service.validations.Validator;

class DeleteByIdCommandTest {
  Command command;
  final static String NEWLINE = System.lineSeparator();
  String id = "1";
  Scanner mockScanner;
  StudentOptions mockOptions;

  @BeforeEach
  void setUp() {
    mockScanner = Mockito.mock(Scanner.class);
    mockOptions = Mockito.mock(StudentOptions.class);
  }

  @Test
  void testDeleteStudent_shouldCallAllNeedeMethods_whenMocked()
      throws UiException {
    Mockito.when(mockOptions.deleteStudentById(Integer.parseInt(id)))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(true);
      command = new DeleteByIdCommand(mockScanner, mockOptions);
      command.execute();
      verify(mockOptions, times(1)).deleteStudentById(Integer.parseInt(id));
    }
  }

  @Test
  void testAddStudent_shouldPrintThatStudentDeleted_whenAllWasValid()
      throws Exception {
    String expexted = "Enter ID of a student you want to delete" + NEWLINE
        + "student with id " + id + " deleted";
    Mockito.when(mockOptions.deleteStudentById(Integer.parseInt(id)))
        .thenReturn(true);
    Mockito.when(mockScanner.next()).thenReturn(id);

    try (MockedStatic<Validator> validator = Mockito
        .mockStatic(Validator.class)) {
      validator.when(() -> Validator.checkIfStudentIdExists(anyInt()))
          .thenReturn(true);
      command = new DeleteByIdCommand(mockScanner, mockOptions);
      String actual = tapSystemOut(() -> command.execute());
      assertEquals(expexted, actual.trim());
    }

  }
}