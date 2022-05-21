package com.kalachev.task7.dao;

import java.util.List;
import java.util.Map;

import com.kalachev.task7.data.CoursesCreator;
import com.kalachev.task7.data.CoursesOfEachStudent;
import com.kalachev.task7.data.GroupCreator;
import com.kalachev.task7.data.StudentCreator;
import com.kalachev.task7.data.StudentsOfEachGroup;

public class Initializer {
  TablesDataDao tablesDataDao = new TablesDataDao();

  public void initializeTables() throws DaoException {
    createDatabaseAndUser();
    initializeStartTables();
    GroupCreator gp = new GroupCreator();
    StudentCreator studentCreator = new StudentCreator();
    List<String> groups = gp.generateGroups();
    List<String> students = studentCreator.generateStudents();
    fillGroupsTable(groups);
    fillStudentsTable(students, groups);
    CoursesCreator coursesCreator = new CoursesCreator();
    Map<String, String> courses = coursesCreator.generateCourses();
    fillCourseTable(courses);
    fillTempManyToManyTable(courses);
    createStudentsCoursesTable();
  }

  private void createDatabaseAndUser() throws DaoException {
    DatabaseCreator databaseCreator = new DatabaseCreator();
    databaseCreator.createDatabase();
    databaseCreator.createUser();
  }

  private void initializeStartTables() throws DaoException {
    tablesDataDao.createTables();
  }

  private void fillStudentsTable(List<String> students, List<String> groups)
      throws DaoException {
    StudentsOfEachGroup studentsOfEachGroup = new StudentsOfEachGroup();
    Map<String, List<String>> studentsInEachGroup = studentsOfEachGroup
        .assignStudentsToGroups(students, groups);
    tablesDataDao.pupulateStudents(students, studentsInEachGroup);
  }

  private void fillGroupsTable(List<String> groups) throws DaoException {
    tablesDataDao.populateGroups(groups);
  }

  private void fillCourseTable(Map<String, String> courses)
      throws DaoException {
    tablesDataDao.populateCourses(courses);
  }

  private void fillTempManyToManyTable(Map<String, String> courses)
      throws DaoException {
    CoursesCreator coursesCreator = new CoursesCreator();
    List<String> courseList = coursesCreator.retrieveCoursesNames(courses);
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
    Map<String, String> studentIds = tablesDataDao.retrieveStudentsId();
    Map<String, List<String>> studentIdAndHisCourses = coursesOfEachStudent
        .assignStudentsIdToCourse(studentIds, courseList);
    tablesDataDao.createManyToManyTable(studentIdAndHisCourses);
  }

  private void createStudentsCoursesTable() throws DaoException {
    tablesDataDao.createStudentsCoursesFullTable();
  }

}
