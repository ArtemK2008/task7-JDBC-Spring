package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.dao.initialization.Initializer;
import com.kalachev.task7.dao.initialization.InitializerImpl;

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
    command = new ExitCommand(scanner);

    int statusCode = catchSystemExit(() -> {
      command.execute();
    });

    assertEquals(0, statusCode);
    assertEquals("Have a good day!", outputStreamCaptor.toString().trim());
  }

}
