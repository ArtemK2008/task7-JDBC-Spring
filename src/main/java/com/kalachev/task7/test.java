package com.kalachev.task7;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.kalachev.task7.DAO.DAOException;
import com.kalachev.task7.DAO.InitalizeStartData;
import com.kalachev.task7.Data.CoursesCreator;
import com.kalachev.task7.Data.CoursesOfEachStudent;
import com.kalachev.task7.Data.GroupCreator;
import com.kalachev.task7.Data.StudentCreator;
import com.kalachev.task7.Data.StudentsOfEachGroup;

public class test {
	private void cleanConsole() {
		for (int i = 0; i < 25; i++) {
			System.out.println();
		}

	}

	public static void main(String[] args) throws FileNotFoundException, DAOException {

		InitalizeStartData initalizeStartData = new InitalizeStartData();
		initalizeStartData.createTables();
		test testt = new test();
		testt.cleanConsole();

		GroupCreator gp = new GroupCreator();
		List<String> groups = gp.generateGroups();
		initalizeStartData.populateGroups(groups);

		StudentCreator studentCreator = new StudentCreator();
		List<String> students = studentCreator.generateStudents();

		StudentsOfEachGroup studentsOfEachGroup = new StudentsOfEachGroup();
		Map<String, List<String>> studentsInEachGroup = studentsOfEachGroup.assignStudentsToGroups(students, groups);
		initalizeStartData.pupulateStudents(students, studentsInEachGroup);

		CoursesCreator coursesCreator = new CoursesCreator();
		Map<String, String> courses = coursesCreator.createCourses();
		initalizeStartData.populateCourses(courses);

		List<String> courseList = coursesCreator.retrieveCoursesNames(courses);
		CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
		Map<String, String> studentIDs = initalizeStartData.retrieveStudentsId();
		Map<String, List<String>> studentIdAndHisCourses = coursesOfEachStudent.assignStudentsIDToCourse(studentIDs,
				courseList);

		initalizeStartData.createManyToManyTable(studentIdAndHisCourses);
		initalizeStartData.createStudentsCoursesFullTable();

	}

}
