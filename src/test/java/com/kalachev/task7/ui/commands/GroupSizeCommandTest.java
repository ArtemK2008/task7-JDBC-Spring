package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kalachev.task7.exceptions.GroupNotFoundException;
import com.kalachev.task7.initialization.InitializerImpl;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.service.options.GroupOptions;

class GroupSizeCommandTest {
  Command command;
  final static String NEWLINE = System.lineSeparator();
  Scanner mockScanner;
  GroupOptions mockOptions;
  String size = "3";
  static Initializer intInitializer = new InitializerImpl();

  @BeforeAll
  static void startUp() {
    intInitializer.initializeTables();
  }

  @BeforeEach
  void setUp() {
    mockScanner = Mockito.mock(Scanner.class);
    mockOptions = Mockito.mock(GroupOptions.class);
  }

  @Test
  void testFindGroup_shouldCallAllNeedeMethods_whenValidInput()
      throws GroupNotFoundException {
    // given
    List<String> groups = Arrays.asList("11-aa", "22-bb");
    Mockito.when(mockOptions.findBySize(Integer.parseInt(size)))
        .thenReturn(groups);
    Mockito.when(mockScanner.next()).thenReturn(size);
    command = new GroupSizeCommand(mockScanner, mockOptions);
    // when
    command.execute();
    // then
    verify(mockOptions, times(1)).findBySize(Integer.parseInt(size));
  }

  @Test
  void testFindGroup_shouldPrintAllValidGroups_whenAllWasValid()
      throws Exception {
    // given
    String expected = "Choose maximal group size" + NEWLINE + "11-aa" + NEWLINE
        + "22-bb";
    List<String> groups = Arrays.asList("11-aa", "22-bb");
    Mockito.when(mockOptions.findBySize(Integer.parseInt(size)))
        .thenReturn(groups);
    Mockito.when(mockScanner.next()).thenReturn(size);
    command = new GroupSizeCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindGroup_shouldPrintNotNegativeMessage_whenSizeWasNegative()
      throws Exception {
    // given
    String expected = "Choose maximal group size" + NEWLINE
        + "Max size cant be negative";
    Mockito.when(mockScanner.next()).thenReturn("-44");
    command = new GroupSizeCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindGroup_shouldPrintBadInputMessage_whenSizeWasNotInt()
      throws Exception {
    // given
    String expected = "Choose maximal group size" + NEWLINE
        + "Your Input was not correct";
    Mockito.when(mockScanner.next()).thenReturn("not an int");
    command = new GroupSizeCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }

  @Test
  void testFindGroup1_shouldPrintNoGroupMessage_whenGroupDidNotExist()
      throws Exception {
    // given
    String expected = "Choose maximal group size" + NEWLINE + "no such groups";
    Mockito.when(mockScanner.next()).thenReturn("322");
    Mockito.when(mockOptions.findBySize(Integer.parseInt("322")))
        .thenThrow(GroupNotFoundException.class);
    command = new GroupSizeCommand(mockScanner, mockOptions);
    // when
    String actual = tapSystemOut(() -> command.execute());
    // then
    assertEquals(expected, actual.trim());
  }
}
