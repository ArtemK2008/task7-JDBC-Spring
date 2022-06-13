package com.kalachev.task7.ui.commands;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.initialization.CoursesInitializer;
import com.kalachev.task7.initialization.GroupInitializer;
import com.kalachev.task7.initialization.Initializer;
import com.kalachev.task7.initialization.SchemaInitializer;
import com.kalachev.task7.initialization.StudentInitializer;
import com.kalachev.task7.initialization.imp.CoursesInitializerImpl;
import com.kalachev.task7.initialization.imp.GroupInitializerImpl;
import com.kalachev.task7.initialization.imp.InitializerImpl;
import com.kalachev.task7.initialization.imp.SchemaInitializerImpl;
import com.kalachev.task7.initialization.imp.StudentInitializerImpl;
import com.kalachev.task7.ui.commands.impl.ExitCommand;

class ExitCommandTest {

  Scanner scanner;
  Command command;
  static StudentInitializer studentInitializer = new StudentInitializerImpl();
  static GroupInitializer groupInitializer = new GroupInitializerImpl();
  static CoursesInitializer coursesInitializer = new CoursesInitializerImpl();
  static SchemaInitializer schemaInitializer = new SchemaInitializerImpl();
  static Initializer intInitializer = new InitializerImpl(studentInitializer,
      coursesInitializer, groupInitializer, schemaInitializer);

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
