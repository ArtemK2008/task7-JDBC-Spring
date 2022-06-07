package com.kalachev.task7.dao;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.dao.entities.Student;
import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;

class StudentDaoImplTest extends DbUnitConfig {
  StudentsDao studentsDao = new StudentsDaoImpl();

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    beforeData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/student/ActualStudentDataSet.xml").getFile()));
    databaseTester.setDataSet(beforeData);
    databaseTester.onSetup();
  }

  @Test
  void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual()
      throws Exception {
    IDataSet expectedDataSet = getDataSet();
    ITable expectedTable = expectedDataSet.getTable("students");
    IDataSet databaseDataSet = getConnection().createDataSet();
    ITable actualTable = databaseDataSet.getTable("students");
    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  void testDaoImpl_whenInsert_thenNewStudentShouldBeAdded()
      throws SQLException, Exception {
    studentsDao.insert("artem", "artemov", 6);
    String[] excludedColumns = { "student_id" };

    IDataSet expectedData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/student/ExpectedInsertStudentDataSet.xml")
            .getFile()));
    ITable expectedTable = expectedData.getTable("students");
    IDataSet actualData = databaseTester.getConnection().createDataSet();
    ITable actualTable = actualData.getTable("students");
    Assertion.assertEqualsIgnoreCols(expectedTable, actualTable,
        excludedColumns);
  }

  @Test
  void testDaoImpl_whenDelete_thenStudentNoLongerInTable()
      throws SQLException, Exception {
    studentsDao.delete(10);
    IDataSet expectedData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/student/ExpectedDeleteStudentDataSet.xml")
            .getFile()));
    ITable expectedTable = expectedData.getTable("students");
    IDataSet actualData = databaseTester.getConnection().createDataSet();
    ITable actualTable = actualData.getTable("students");
    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  void testStudentNamesById_shouldMapStudentsToIds_whenStudentTableExists()
      throws DaoException {

    Map<String, String> expected = new LinkedHashMap<>();
    expected.put("1", "tom tomov");
    expected.put("2", "ivan ivanov");
    expected.put("3", "petr petrov");
    expected.put("4", "sidor sidorov");
    expected.put("5", "aleksandr aleksandrov");
    expected.put("6", "pavel pavlov");
    expected.put("7", "sveta svetova");
    expected.put("8", "liza lizova");
    expected.put("9", "marina marinova");
    expected.put("10", "lena lenova");

    Map<String, String> actual = studentsDao.studentNamesById();
    assertEquals(expected, actual);
  }

  @Test
  void testFindByCourse_shouldReturnAllStudentsOfChousenCourse_whenTableExists()
      throws DaoException {
    List<Student> expected = new ArrayList<>();
    Student marina = new Student();
    marina.setFirstName("marina");
    marina.setLastName("marinova");
    marina.setGroupdId(4);
    marina.setId(9);
    Student lena = new Student();
    lena.setFirstName("lena");
    lena.setLastName("lenova");
    lena.setGroupdId(5);
    lena.setId(10);
    expected.add(marina);
    expected.add(lena);

    List<Student> actual = studentsDao.findByCourse("Mandarin");
    assertEquals(expected, actual);
  }

  @Test
  void testCheckStudntIfExistsInGroup_shouldReturnTrue_whenStudentIsInGroup()
      throws DaoException {
    boolean check = studentsDao.isExistsInGroup("tom", "tomov", 1);
    assertTrue(check);
  }

  @Test
  void testCheckStudntIfExistsInGroup_shouldReturnFalse_whenStudentIsNotInGroup()
      throws DaoException {
    boolean check = studentsDao.isExistsInGroup("tom", "tomov", 2);
    assertFalse(check);
  }

  @Test
  void testcheckStudentIdIfExists_shouldReturnTrue_whenIdExists()
      throws DaoException {
    boolean check = studentsDao.isIdExists(2);
    assertTrue(check);
  }

  @Test
  void testcheckStudentIdIfExists_shouldReturnFalse_whenIdNotExists()
      throws DaoException {
    boolean check = studentsDao.isIdExists(15);
    assertFalse(check);
  }

  @Test
  void testcheckIfStudentInCourse_shouldReturnTrue_whenStudentInCourse()
      throws DaoException {
    boolean check = studentsDao.checkIfStudentInCourse(1, "Russian");
    assertTrue(check);
  }

  @Test
  void testcheckIfStudentInCourse_shouldReturnFalse_whenStudentNotInCourse()
      throws DaoException {
    boolean check = studentsDao.checkIfStudentInCourse(1, "Hindi");
    assertFalse(check);
  }
}
