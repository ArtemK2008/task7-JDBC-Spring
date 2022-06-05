package com.kalachev.task7;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.kalachev.task7.dao.initialization.Initializer;
import com.kalachev.task7.dao.initialization.InitializerImpl;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.ui.ConsoleMenu;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class ConsoleMenuIT {
  ConsoleMenu spyMenu;
  Scanner mockScanner;
  Initializer spyInitializer;
  private final PrintStream standardOut = System.out;
  ByteArrayOutputStream outputStreamCaptor;
  final static String NEWLINE = System.lineSeparator();
  final static String EXIT = "7";
  private static final String MAX_SIZE = "20";
  private static final String COURSE = "Ukrainian";
  private static final String FIRSTNAME = "DummyName";
  private static final String LASTNAME = "DummyLastName";
  private static final String GROUP_ID = "1";
  private static final String STUDENT_TO_DELETE_ID = "77";
  private static final String STUDENT_ID = "77";
  private static final String ALREADY_IN_COURSE = "student already in course";

  @BeforeEach
  public void setUp() {
    outputStreamCaptor = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStreamCaptor));

    mockScanner = Mockito.mock(Scanner.class);
    spyInitializer = Mockito.spy(InitializerImpl.class);
    spyMenu = Mockito.spy(new ConsoleMenu(mockScanner, spyInitializer));
    doNothing().when(spyMenu).cleanConsole();
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
    List<String> expectedGroups = retrieveGroupNamesWithCondition(MAX_SIZE);
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
    List<String> expectedStudents = retrieveUkrainianRealStudentNames(COURSE);
    List<String> actualStudent = Arrays.asList(onlyStudentNames);
    assertTrue(actualStudent.size() == expectedStudents.size()
        && actualStudent.containsAll(expectedStudents)
        && expectedStudents.containsAll(actualStudent));
  }

  @Test
  void testConsoleMenu_shouldAddStudentToDatabase_whenUserAsksToInsert()
      throws Exception {
    createData();
    int countBefore = countStudentsInDatabase();
    assertEquals(200, countBefore);
    Mockito.when(mockScanner.next()).thenReturn("3").thenReturn(FIRSTNAME)
        .thenReturn(LASTNAME).thenReturn(GROUP_ID).thenReturn(EXIT);
    Mockito.when(mockScanner.nextLine()).thenReturn("enter");

    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
    String output = outputStreamCaptor.toString().trim();
    String[] outputLines = output.split(NEWLINE);
    String expectedMessage = "Student " + FIRSTNAME + " " + LASTNAME
        + " added to group " + GROUP_ID;
    String actualMessage = outputLines[22];
    assertEquals(expectedMessage, actualMessage);
    int countAfter = countStudentsInDatabase();
    assertEquals(201, countAfter);
    assertTrue(checkIfStudentExistsInDatabase(FIRSTNAME, LASTNAME));
  }

  @Test
  void testConsoleMenu_shouldDeleteStudentFromDatabase_whenUserAsksToDelete()
      throws Exception {
    createData();
    int countBefore = countStudentsInDatabase();
    assertEquals(200, countBefore);
    Mockito.when(mockScanner.next()).thenReturn("4")
        .thenReturn(STUDENT_TO_DELETE_ID).thenReturn(EXIT);
    Mockito.when(mockScanner.nextLine()).thenReturn("enter");

    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
    String output = outputStreamCaptor.toString().trim();
    String[] outputLines = output.split(NEWLINE);
    String expectedMessage = "student with id " + STUDENT_TO_DELETE_ID
        + " deleted";
    String actualMessage = outputLines[20];
    assertEquals(expectedMessage, actualMessage);
    int countAfter = countStudentsInDatabase();
    assertEquals(199, countAfter);
    assertFalse(checkIfStudentExistsInDatabase(STUDENT_TO_DELETE_ID));
  }

  @Test
  void testConsoleMenu_shouldAddStudentToCourse_whenUserAsksToAddToCourse()
      throws Exception {
    createData();
    int coursesNumberBefore = countStudentCourses(STUDENT_ID);
    assertNotEquals(0, coursesNumberBefore);
    assertTrue(coursesNumberBefore < 4);

    Mockito.when(mockScanner.next()).thenReturn("5").thenReturn(STUDENT_ID)
        .thenReturn(COURSE).thenReturn(EXIT);
    Mockito.when(mockScanner.nextLine()).thenReturn("enter");
    doNothing().when(spyInitializer).initializeTables();

    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
    String output = outputStreamCaptor.toString().trim();
    String[] outputLines = output.split(NEWLINE);
    String actualMessage = outputLines[31];
    String expectedMessage = "Student with id " + STUDENT_ID
        + " added to course " + COURSE;
    if (actualMessage.equals(ALREADY_IN_COURSE)) {
      expectedMessage = ALREADY_IN_COURSE;
    }
    assertEquals(expectedMessage, actualMessage);

    int coursesNumberAfter = countStudentCourses(STUDENT_ID);
    assertEquals(coursesNumberAfter, coursesNumberBefore + 1);
  }

  @Test
  void testConsoleMenu_shouldRemoveStudentFromCourse_whenUserAsksToDelete()
      throws Exception {
    createData();
    int coursesNumberBefore = countStudentCourses(STUDENT_ID);
    assertNotEquals(0, coursesNumberBefore);
    assertTrue(coursesNumberBefore < 4);
    String course = findStudentCourse(STUDENT_ID);
    Mockito.when(mockScanner.next()).thenReturn("6").thenReturn(STUDENT_ID)
        .thenReturn(course).thenReturn(EXIT);
    Mockito.when(mockScanner.nextLine()).thenReturn("enter");
    doNothing().when(spyInitializer).initializeTables();
    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
    String output = outputStreamCaptor.toString().trim();
    String[] outputLines = output.split(NEWLINE);
    String actualMessage = outputLines[21 + coursesNumberBefore];
    String expectedMessage = "Student with id " + STUDENT_ID + " removed from "
        + course;
    assertEquals(expectedMessage, actualMessage);
    int coursesNumberAfter = countStudentCourses(STUDENT_ID);
    assertEquals(coursesNumberAfter, coursesNumberBefore - 1);
  }

  @Test
  void testConsoleMenu_shouldTermitateApp_whenUserAsksForExit()
      throws Exception {
    Mockito.when(mockScanner.next()).thenReturn(EXIT);
    int statusCode = catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
    assertEquals(0, statusCode);
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

  private List<String> retrieveUkrainianRealStudentNames(String course)
      throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    List<String> students = new ArrayList<>();
    final String FIND_STUDENTS = "SELECT first_name,last_name "
        + "FROM studentscoursesdata " + "WHERE course_name = (?)";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(FIND_STUDENTS);
      statement.setString(1, course);
      rs = statement.executeQuery();
      while (rs.next()) {
        students
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
    return students;
  }

  private List<String> retrieveGroupNamesWithCondition(String count)
      throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    List<String> groups = new LinkedList<>();
    final String FIND_GROUP_BY_SIZE = "SELECT g.group_name FROM Students"
        + " as s " + "INNER JOIN Groups as g " + "ON s.group_id = g.group_id "
        + "GROUP BY g.group_id,group_name "
        + "HAVING COUNT (s.group_id) >= (?)";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(FIND_GROUP_BY_SIZE);
      statement.setInt(1, Integer.parseInt(count));
      rs = statement.executeQuery();
      while (rs.next()) {
        groups.add(rs.getString("group_name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return groups;
  }

  private void createData() throws Exception {
    Mockito.when(mockScanner.next()).thenReturn(EXIT);
    catchSystemExit(() -> {
      spyMenu.runSchoolApp();
    });
  }

  private int countStudentsInDatabase() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    int count = 0;
    final String COUNT_STUDENTS = "SELECT COUNT(*) FROM STUDENTS";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.createStatement();
      rs = statement.executeQuery(COUNT_STUDENTS);
      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return count;
  }

  private boolean checkIfStudentExistsInDatabase(String name, String lastname)
      throws DaoException {
    boolean isExist = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    int count = 0;
    final String COUNT_STUDENTS = "SELECT COUNT(*) FROM STUDENTS WHERE first_name = (?) AND last_name = (?)";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(COUNT_STUDENTS);
      statement.setString(1, name);
      statement.setString(2, lastname);
      rs = statement.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
      }
      if (count != 0) {
        isExist = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return isExist;
  }

  private boolean checkIfStudentExistsInDatabase(String studentId)
      throws DaoException {
    boolean isExist = false;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    int count = 0;
    final String COUNT_STUDENTS = "SELECT COUNT(*) FROM STUDENTS WHERE student_id = (?)";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(COUNT_STUDENTS);
      statement.setInt(1, Integer.parseInt(studentId));
      rs = statement.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
      }
      if (count != 0) {
        isExist = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return isExist;
  }

  private int countStudentCourses(String studentId) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    int count = 0;
    final String COUNT_STUDENTS = "SELECT COUNT(*) FROM studentscoursesdata WHERE student_id = (?)";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(COUNT_STUDENTS);
      statement.setInt(1, Integer.parseInt(studentId));
      rs = statement.executeQuery();
      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return count;
  }

  private String findStudentCourse(String studentId) throws DaoException {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    String course = "";
    final String FIND_COURSE = "SELECT course_name FROM studentscoursesdata WHERE student_id = (?)";
    try {
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.prepareStatement(FIND_COURSE);
      statement.setInt(1, Integer.parseInt(studentId));
      rs = statement.executeQuery();
      if (rs.next()) {
        course = rs.getString(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(rs, statement, connection);
    }
    return course;
  }
}
