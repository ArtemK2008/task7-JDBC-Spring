package com.kalachev.task7.DAO;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.kalachev.task7.Data.CoursesCreator;
import com.kalachev.task7.Data.CoursesOfEachStudent;
import com.kalachev.task7.Data.GroupCreator;
import com.kalachev.task7.Data.StudentCreator;
import com.kalachev.task7.Data.StudentsOfEachGroup;

public class Initializer {
	TablesDataDAO tablesDataDAO = new TablesDataDAO();

	public void initializeTables() throws FileNotFoundException, DAOException {
		createDatabaseAndUser();
		InitializeStartTables();
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

	private void createDatabaseAndUser() throws DAOException {
		DatabaseCreator databaseCreator = new DatabaseCreator();
		databaseCreator.createDatabase();
		databaseCreator.createUser();
	}

	private void InitializeStartTables() throws FileNotFoundException, DAOException {
		tablesDataDAO.createTables();
	}

	private void fillStudentsTable(List<String> students, List<String> groups) throws DAOException {
		StudentsOfEachGroup studentsOfEachGroup = new StudentsOfEachGroup();
		Map<String, List<String>> studentsInEachGroup = studentsOfEachGroup.assignStudentsToGroups(students, groups);
		tablesDataDAO.pupulateStudents(students, studentsInEachGroup);
	}

	private void fillGroupsTable(List<String> groups) throws DAOException {
		tablesDataDAO.populateGroups(groups);
	}

	private void fillCourseTable(Map<String, String> courses) throws DAOException {
		tablesDataDAO.populateCourses(courses);
	}

	private void fillTempManyToManyTable(Map<String, String> courses) throws DAOException {
		CoursesCreator coursesCreator = new CoursesCreator();
		List<String> courseList = coursesCreator.retrieveCoursesNames(courses);
		CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
		Map<String, String> studentIDs = tablesDataDAO.retrieveStudentsId();
		Map<String, List<String>> studentIdAndHisCourses = coursesOfEachStudent.assignStudentsIDToCourse(studentIDs,
				courseList);
		tablesDataDAO.createManyToManyTable(studentIdAndHisCourses);
	}

	private void createStudentsCoursesTable() throws DAOException {
		tablesDataDAO.createStudentsCoursesFullTable();
	}

}
