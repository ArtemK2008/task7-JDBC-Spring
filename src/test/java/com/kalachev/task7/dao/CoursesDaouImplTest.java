package com.kalachev.task7.dao;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kalachev.task7.dao.entities.Course;
import com.kalachev.task7.dao.implementations.CoursesDaoImpl;
import com.kalachev.task7.dao.interfaces.CoursesDao;
import com.kalachev.task7.exceptions.DaoException;

public class CoursesDaouImplTest extends DbUnitConfig {
  CoursesDao coursesDao = new CoursesDaoImpl();

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    beforeData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/course/ActualCourseDataSet.xml").getFile()));
    databaseTester.setDataSet(beforeData);
    databaseTester.onSetup();
  }

  @Test
  void testAddStudent_shouldAddStudentToCourse_whenStudentWasNotInThisCourse()
      throws SQLException, Exception {
    IDataSet expectedData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/course/ExpectedInsertCourseDataSet.xml")
            .getFile()));

    coursesDao.addStudent(5, "Arabic");

    ITable expectedTable = expectedData.getTable("studentscoursesdata");
    IDataSet actualData = databaseTester.getConnection().createDataSet();
    ITable actualTable = actualData.getTable("studentscoursesdata");
    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  void testRemoveStudent_shouldDeleteStudentFromCourse_whenStudentWasInCourse()
      throws SQLException, Exception {
    IDataSet expectedData = new FlatXmlDataSetBuilder()
        .build(new FileInputStream(getClass().getClassLoader()
            .getResource("dao/course/ExpectedDeleteCourseDataSet.xml")
            .getFile()));

    coursesDao.removeStudent(1, "Russian");
    ITable expectedTable = expectedData.getTable("studentscoursesdata");
    IDataSet actualData = databaseTester.getConnection().createDataSet();
    ITable actualTable = actualData.getTable("studentscoursesdata");
    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  void testGetAll_shouldReturnAllCoursesinList_whenCalledWithValidData()
      throws DaoException {
    List<Course> expected = new ArrayList<>();
    Course course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("Russian");
    expected.add(course);
    course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("Ukrainian");
    expected.add(course);
    course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("Mandarin");
    expected.add(course);
    course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("English");
    expected.add(course);
    course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("Arabic");
    expected.add(course);

    List<Course> actual = coursesDao.getAll();
    assertEquals(expected, actual);
  }

  @Test
  void testGetById_shouldReturnAllCoursesOfChosenStudent_WhenCalledOnExistingStudent()
      throws DaoException {
    List<Course> expected = new ArrayList<>();
    Course course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("Mandarin");
    expected.add(course);
    course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("Arabic");
    expected.add(course);
    course = new Course();
    course.setCourseDescription("-");
    course.setCourseName("English");
    expected.add(course);

    List<Course> actual = coursesDao.getById(10);
    assertEquals(expected, actual);
  }

  @Test
  void testCheckCourseIfExist_shouldReturnTrue_whenCourseExists()
      throws DaoException {
    boolean check = coursesDao.isExists("Ukrainian");
    assertTrue(check);
  }

  @Test
  void testCheckCourseIfExist_shouldReturnFalse_whenCourseNotExists()
      throws DaoException {
    boolean check = coursesDao.isExists("Hindi");
    assertFalse(check);
  }
}
