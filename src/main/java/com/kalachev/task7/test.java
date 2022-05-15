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

	public static void main(String[] args) throws FileNotFoundException, DAOException {

		InitalizeStartData initalizeStartData = new InitalizeStartData();
		initalizeStartData.createTables();

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

		initalizeStartData.createManyToMany(studentIdAndHisCourses);
		initalizeStartData.createStudentsCoursesFullTable();

		/*
		 * Map<String, String> testIds = initalizeStartData.retrieveStudentsId(); for
		 * (String s : testIds.keySet()) { System.out.println(s + "    " +
		 * testIds.get(s)); }
		 */

		/*
		 * CoursesOfEachStudent coursesOfEachStudent = new CoursesOfEachStudent();
		 * Map<String, List<String>> coursesOfAStudent =
		 * coursesOfEachStudent.assignStudentsToCourse(students,
		 * coursesCreator.retrieveCoursesNames(courses));
		 */

		/*
		 * int temp = 0; for (String s : coursesOfAStudent.keySet()) {
		 * System.out.println("student is " + s); for (String c :
		 * coursesOfAStudent.get(s)) { System.out.println("his course is " + c); temp++;
		 * } System.out.println(temp); System.out.println(); }
		 */
		/*
		 * StudentCreator testData = new StudentCreator(); List<String> test =
		 * testData.createStudents(); test.forEach(System.out::println);
		 */

		/*
		 * GroupCreator gp = new GroupCreator(); List<String> groups =
		 * gp.generateGroups(); groups.forEach(System.out::println);
		 */

		/*
		 * StudentsInGroup studentsInGroup = new StudentsInGroup();
		 * Map<String,List<String>> gropMap = studentsInGroup.assignStudentsToGroups();
		 * for(Entry<String, List<String>> entry :gropMap.entrySet()) {
		 * System.out.print("group is " + entry.getKey() +" ");
		 * System.out.print(" its size is " + entry.getValue().size() + " ");
		 * System.out.print("its students are: "); List<String> students =
		 * entry.getValue(); for(String s: students) { System.out.print(s + " "); }
		 * System.out.println(); }
		 */

		/*
		 * CoursesForStudent coursesForStudent = new CoursesForStudent(); StudentCreator
		 * studentCreator = new StudentCreator(); Map<String, List<String>> coursesMap =
		 * coursesForStudent .assignStudentsToCourse(studentCreator.createStudents());
		 * for (Entry<String, List<String>> entry : coursesMap.entrySet()) {
		 * System.out.println("current student is ");
		 * System.out.println(entry.getKey()); System.out.println("his courses are: ");
		 * List<String> courses = entry.getValue(); for (String s : courses) {
		 * System.out.print(s + " "); } System.out.println(); System.out.println(); }
		 */
	}

}
