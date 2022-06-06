package com.kalachev.task7.dao.initialization;

import java.util.List;
import java.util.Map;

import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.initialization.tables.CoursesDataDbPopulator;
import com.kalachev.task7.dao.initialization.tables.GroupsDataDbPopulator;
import com.kalachev.task7.dao.initialization.tables.StudentsDataDbPopulator;
import com.kalachev.task7.dao.initialization.tables.StudentsToCoursesDataDbPopulator;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.service.data.CoursesInitializer;
import com.kalachev.task7.service.data.GroupInitializer;
import com.kalachev.task7.service.data.StudentInitializer;

public class InitializerImpl implements Initializer {
  List<String> groups;
  List<String> students;
  Map<String, String> courses;

  @Override
  public void initializeTables() throws DaoException {
    initializeStartTables();
    generateStudentData();
    fillGroupsTable(groups);
    fillStudentsTable(students, groups);
    fillCourseTable(courses);
    fillTempManyToManyTable(courses);
    createStudentsCoursesTable();
  }

  private void generateStudentData() {
    GroupInitializer gp = new GroupInitializer();
    StudentInitializer studentInitializer = new StudentInitializer();
    CoursesInitializer coursesInitializer = new CoursesInitializer();
    groups = gp.generateGroups();
    students = studentInitializer.generateStudents();
    courses = coursesInitializer.generateCourses();
  }

  private void initializeStartTables() throws DaoException {
    SchemaInitializer schema = new SchemaInitializer();
    schema.createSchema();
  }

  private void fillStudentsTable(List<String> students, List<String> groups)
      throws DaoException {
    GroupInitializer groupInitializer = new GroupInitializer();
    Map<String, List<String>> studentsInEachGroup = groupInitializer
        .assignStudentsToGroups(students, groups);
    StudentsDataDbPopulator filler = new StudentsDataDbPopulator();
    filler.populateStudents(studentsInEachGroup);
  }

  private void fillGroupsTable(List<String> groups) throws DaoException {
    GroupsDataDbPopulator filler = new GroupsDataDbPopulator();
    filler.populateGroups(groups);
  }

  private void fillCourseTable(Map<String, String> courses)
      throws DaoException {
    CoursesDataDbPopulator filler = new CoursesDataDbPopulator();
    filler.populateCourses(courses);
  }

  private void fillTempManyToManyTable(Map<String, String> courses)
      throws DaoException {
    CoursesInitializer coursesInitializer = new CoursesInitializer();

    List<String> courseList = coursesInitializer.retrieveCoursesNames(courses);
    StudentsDao studentsDao = new StudentsDaoImpl();
    Map<String, String> studentIds = studentsDao.studentNamesById();
    Map<String, List<String>> studentIdAndHisCourses = coursesInitializer
        .assignStudentsIdToCourse(studentIds, courseList);
    StudentsToCoursesDataDbPopulator filler = new StudentsToCoursesDataDbPopulator();
    filler.createManyToManyTable(studentIdAndHisCourses);
  }

  private void createStudentsCoursesTable() throws DaoException {
    StudentsToCoursesDataDbPopulator filler = new StudentsToCoursesDataDbPopulator();
    filler.createStudentsCoursesFullTable();
  }

}
