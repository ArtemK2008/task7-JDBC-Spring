package com.kalachev.task7;

import java.io.IOException;

import com.kalachev.task7.DAO.DAOException;

public class test {
	private void cleanConsole() {
		for (int i = 0; i < 25; i++) {
			System.out.println();
		}

	}

	public static void main(String[] args) throws DAOException, IOException {

		/*
		 * ConsoleApp app = new ConsoleApp(); app.logic();
		 */

		/*
		 * tablesDataDAO tablesDataDAO = new tablesDataDAO();
		 * tablesDataDAO.createTables(); test testt = new test(); testt.cleanConsole();
		 * 
		 * GroupCreator gp = new GroupCreator(); List<String> groups =
		 * gp.generateGroups(); tablesDataDAO.populateGroups(groups);
		 * 
		 * StudentCreator studentCreator = new StudentCreator(); List<String> students =
		 * studentCreator.generateStudents();
		 * 
		 * StudentsOfEachGroup studentsOfEachGroup = new StudentsOfEachGroup();
		 * Map<String, List<String>> studentsInEachGroup =
		 * studentsOfEachGroup.assignStudentsToGroups(students, groups);
		 * tablesDataDAO.pupulateStudents(students, studentsInEachGroup);
		 * 
		 * CoursesCreator coursesCreator = new CoursesCreator(); Map<String, String>
		 * courses = coursesCreator.createCourses();
		 * tablesDataDAO.populateCourses(courses);
		 * 
		 * List<String> courseList = coursesCreator.retrieveCoursesNames(courses);
		 * CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
		 * Map<String, String> studentIDs = tablesDataDAO.retrieveStudentsId();
		 * Map<String, List<String>> studentIdAndHisCourses =
		 * coursesOfEachStudent.assignStudentsIDToCourse(studentIDs, courseList);
		 * 
		 * tablesDataDAO.createManyToManyTable(studentIdAndHisCourses);
		 * tablesDataDAO.createStudentsCoursesFullTable();
		 */

	}

}
