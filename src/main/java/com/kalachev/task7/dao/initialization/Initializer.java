package com.kalachev.task7.dao.initialization;

import java.util.List;
import java.util.Map;

import com.kalachev.task7.dao.classes.StudentsDaoImpl;
import com.kalachev.task7.dao.initialization.tables.CoursesFiller;
import com.kalachev.task7.dao.initialization.tables.GroupsFiller;
import com.kalachev.task7.dao.initialization.tables.StudentsFiller;
import com.kalachev.task7.dao.initialization.tables.StudentsToCoursesFiller;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.service.data.CoursesCreator;
import com.kalachev.task7.service.data.CoursesOfEachStudent;
import com.kalachev.task7.service.data.GroupCreator;
import com.kalachev.task7.service.data.StudentCreator;
import com.kalachev.task7.service.data.StudentsOfEachGroup;

public class Initializer {
  List<String> groups;
  List<String> students;
  Map<String, String> courses;

  public void initializeTables() throws DaoException {
    createDatabase();
    createUser();
    initializeStartTables();
    generateStudentData();
    fillGroupsTable(groups);
    fillStudentsTable(students, groups);
    fillCourseTable(courses);
    fillTempManyToManyTable(courses);
    createStudentsCoursesTable();
  }

  private void generateStudentData() {
    GroupCreator gp = new GroupCreator();
    StudentCreator studentCreator = new StudentCreator();
    CoursesCreator coursesCreator = new CoursesCreator();
    groups = gp.generateGroups();
    students = studentCreator.generateStudents();
    courses = coursesCreator.generateCourses();
  }

  private void createDatabase() throws DaoException {
    DatabaseCreator databaseCreator = new DatabaseCreator();
    databaseCreator.createDatabase();
  }

  private void createUser() throws DaoException {
    UserCreator userCreator = new UserCreator();
    userCreator.createUser();
  }

  private void initializeStartTables() throws DaoException {
    SchemaInitializer schema = new SchemaInitializer();
    schema.createSchema();
  }

  private void fillStudentsTable(List<String> students, List<String> groups)
      throws DaoException {
    StudentsOfEachGroup studentsOfEachGroup = new StudentsOfEachGroup();
    Map<String, List<String>> studentsInEachGroup = studentsOfEachGroup
        .assignStudentsToGroups(students, groups);
    StudentsFiller filler = new StudentsFiller();
    filler.populateStudents(studentsInEachGroup);
  }

  private void fillGroupsTable(List<String> groups) throws DaoException {
    GroupsFiller filler = new GroupsFiller();
    filler.populateGroups(groups);
  }

  private void fillCourseTable(Map<String, String> courses)
      throws DaoException {
    CoursesFiller filler = new CoursesFiller();
    filler.populateCourses(courses);
  }

  private void fillTempManyToManyTable(Map<String, String> courses)
      throws DaoException {
    CoursesCreator coursesCreator = new CoursesCreator();
    List<String> courseList = coursesCreator.retrieveCoursesNames(courses);
    CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();

    StudentsDao studentsDao = new StudentsDaoImpl();
    Map<String, String> studentIds = studentsDao.retrieveStudentsId();
    Map<String, List<String>> studentIdAndHisCourses = coursesOfEachStudent
        .assignStudentsIdToCourse(studentIds, courseList);
    StudentsToCoursesFiller filler = new StudentsToCoursesFiller();
    filler.createManyToManyTable(studentIdAndHisCourses);
  }

  private void createStudentsCoursesTable() throws DaoException {
    StudentsToCoursesFiller filler = new StudentsToCoursesFiller();
    filler.createStudentsCoursesFullTable();
  }

}
