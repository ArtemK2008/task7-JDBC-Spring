package com.kalachev.task7;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.ui.ConsoleMenu;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class AddStudentCommandIT {
  static ConsoleMenu spyMenu;
  static Scanner mockScanner;
  private final PrintStream standardOut = System.out;
  ByteArrayOutputStream outputStreamCaptor;
  final static String NEWLINE = System.lineSeparator();
  final static String EXIT = "7";
  private static final String MAX_SIZE = "20";
  private static final String COURSE = "Ukrainian";

  @BeforeAll
  public static void startUp() {
    mockScanner = Mockito.mock(Scanner.class);
    spyMenu = Mockito.spy(new ConsoleMenu(mockScanner));
    doNothing().when(spyMenu).cleanConsole();
  }

  @BeforeEach
  public void setUp() {
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  public void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void testConsoleMenu_shouldPrintGroupsWithCondition_whenUserAsksForGrouNames()
      throws Exception {
    Mockito.when(mockScanner.next()).thenReturn("1").thenReturn(MAX_SIZE)
        .thenReturn(EXIT);
    Mockito.when(mockScanner.nextLine()).thenReturn("enter");

    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
    String output = outputStreamCaptor.toString().trim();
    String[] outputLines = output.split(NEWLINE);
    assertTrue(outputLines.length > 21);

    String[] removedLinesBeforeGroups = removeLinesBefore(outputLines, 9);
    String[] onlyGroupNames = removeLinesAfter(removedLinesBeforeGroups, 12);

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<String> expectedGroups = new LinkedList<>();
    final String FIND_GROUP_BY_SIZE = "SELECT g.group_name FROM Students"
        + " as s " + "INNER JOIN Groups as g " + "ON s.group_id = g.group_id "
        + "GROUP BY g.group_id,group_name " + "HAVING COUNT (s.group_id) >= "
        + MAX_SIZE;
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.createStatement();
      rs = statement.executeQuery(FIND_GROUP_BY_SIZE);
      while (rs.next()) {
        expectedGroups.add(rs.getString("group_name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    List<String> actualGroups = Arrays.asList(onlyGroupNames);
    assertEquals(expectedGroups.size(), actualGroups.size());
    assertTrue(actualGroups.size() == expectedGroups.size()
        && actualGroups.containsAll(expectedGroups)
        && expectedGroups.containsAll(actualGroups));
  }

  @Test
  void testConsoleMenu_shouldPrintAllStudentOfCourse_whenUserAsksForGroupStudentNames()
      throws Exception {
    Mockito.when(mockScanner.next()).thenReturn("2").thenReturn(COURSE)
        .thenReturn(EXIT);
    Mockito.when(mockScanner.nextLine()).thenReturn("enter");

    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
    String output = outputStreamCaptor.toString().trim();
    String[] outputLines = output.split(NEWLINE);

    String[] removedLinesBeforeStudents = removeLinesBefore(outputLines, 19);
    String[] onlyStudentNames = removeLinesAfter(removedLinesBeforeStudents,
        12);
    assertTrue(onlyStudentNames.length > 0);

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    List<String> expectedStudents = new ArrayList<>();
    final String FIND_STUDENTS = "SELECT first_name,last_name "
        + "FROM studentscoursesdata " + "WHERE course_name = 'Ukrainian'";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.createStatement();
      rs = statement.executeQuery(FIND_STUDENTS);
      while (rs.next()) {
        expectedStudents
            .add(rs.getString("first_name") + " " + rs.getString("last_name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    List<String> actualStudent = Arrays.asList(onlyStudentNames);
    assertTrue(actualStudent.size() == expectedStudents.size()
        && actualStudent.containsAll(expectedStudents)
        && expectedStudents.containsAll(actualStudent));
  }

  private String[] removeLinesBefore(String[] data, int amount) {
    String[] removedLinesBefore = new String[data.length - amount];
    for (int i = amount; i < data.length; i++) {
      removedLinesBefore[i - amount] = data[i];
    }
    return removedLinesBefore;
  }

  private String[] removeLinesAfter(String[] data, int amount) {
    String[] removedLinesAfter = Arrays.copyOf(data, data.length - amount);
    return removedLinesAfter;
  }

}
