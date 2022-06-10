package com.kalachev.task7.initialization;

import java.util.List;
import java.util.Map;

import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.initialization.initialization_interfaces.CoursesInitializer;
import com.kalachev.task7.initialization.initialization_interfaces.GroupInitializer;
import com.kalachev.task7.initialization.initialization_interfaces.Initializer;
import com.kalachev.task7.initialization.initialization_interfaces.SchemaInitializer;
import com.kalachev.task7.initialization.initialization_interfaces.StudentInitializer;
import com.kalachev.task7.initialization.tables.CoursesDataDbPopulator;
import com.kalachev.task7.initialization.tables.GroupsDataDbPopulator;
import com.kalachev.task7.initialization.tables.StudentsDataDbPopulator;
import com.kalachev.task7.initialization.tables.StudentsToCoursesDataDbPopulator;

public class InitializerImpl implements Initializer {
  List<String> groups;
  List<String> students;
  Map<String, String> courses;

  @Override
  public void initializeTables() {
    initializeStartTables();
    generateStudentData();
    fillGroupsTable(groups);
    fillStudentsTable(students, groups);
    fillCourseTable(courses);
    fillTempManyToManyTable(courses);
    createStudentsCoursesTable();
  }

  private void generateStudentData() {
    GroupInitializer gp = new GroupInitializerImpl();
    StudentInitializer studentInitializerImpl = new StudentInitializerImpl();
    CoursesInitializer coursesInitializerImpl = new CoursesInitializerImpl();
    groups = gp.generateGroups();
    students = studentInitializerImpl.generateStudents();
    courses = coursesInitializerImpl.generateCourses();
  }

  private void initializeStartTables() {
    SchemaInitializer schema = new SchemaInitializerImpl();
    schema.createSchema();
  }

  private void fillStudentsTable(List<String> students, List<String> groups) {
    GroupInitializer groupInitializerImpl = new GroupInitializerImpl();
    Map<String, List<String>> studentsInEachGroup = groupInitializerImpl
        .assignStudentsToGroups(students, groups);
    StudentsDataDbPopulator filler = new StudentsDataDbPopulator();
    filler.populateStudents(studentsInEachGroup);
  }

  private void fillGroupsTable(List<String> groups) {
    GroupsDataDbPopulator filler = new GroupsDataDbPopulator();
    filler.populateGroups(groups);
  }

  private void fillCourseTable(Map<String, String> courses) {
    CoursesDataDbPopulator filler = new CoursesDataDbPopulator();
    filler.populateCourses(courses);
  }

  private void fillTempManyToManyTable(Map<String, String> courses) {
    CoursesInitializer coursesInitializerImpl = new CoursesInitializerImpl();

    List<String> courseList = coursesInitializerImpl
        .retrieveCoursesNames(courses);
    StudentsDao studentsDao = new StudentsDaoImpl();
    Map<String, String> studentIds = studentsDao.studentNamesById();
    Map<String, List<String>> studentIdAndHisCourses = coursesInitializerImpl
        .assignStudentsIdToCourse(studentIds, courseList);
    StudentsToCoursesDataDbPopulator filler = new StudentsToCoursesDataDbPopulator();
    filler.createManyToManyTable(studentIdAndHisCourses);
  }

  private void createStudentsCoursesTable() {
    StudentsToCoursesDataDbPopulator filler = new StudentsToCoursesDataDbPopulator();
    filler.createStudentsCoursesFullTable();
  }

}
