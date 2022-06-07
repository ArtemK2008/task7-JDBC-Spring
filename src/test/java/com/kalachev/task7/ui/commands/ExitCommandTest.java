package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.initialization.InitializerImpl;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;

class ExitCommandTest {

  Scanner scanner;
  Command command;
  static Initializer intInitializer = new InitializerImpl();

  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    scanner = new Scanner(System.in);
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  void testExit_shouldPrintMessageAndTerminate_whenCommandIsCalled()
      throws Exception {
    // given
    int expectedCode = 0;
    command = new ExitCommand(scanner);
    // when
    int statusCode = catchSystemExit(() -> {
      command.execute();
    });
    // then
    assertEquals(expectedCode, statusCode);
    assertEquals("Have a good day!", outputStreamCaptor.toString().trim());
  }

}
