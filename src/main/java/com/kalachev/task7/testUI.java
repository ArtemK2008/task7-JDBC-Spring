package com.kalachev.task7;

import com.kalachev.task7.DAO.DAOException;
import com.kalachev.task7.DAO.UserOptionsDAO;
import com.kalachev.task7.UI.UIException;
import com.kalachev.task7.UI.UserOptions;

public class testUI {

	public static void main(String[] args) throws DAOException, UIException {
		UserOptionsDAO options = new UserOptionsDAO();
		// List<String> testgr = options.findGroupsBySize(20);
		// testgr.forEach(System.out::println);
		// List<String> testgr = options.findStudentsByCourse("Russian");
		// testgr.forEach(System.out::println);
		// options.addNewStudent("TEST", "TESTOV", 1);
		// options.deleteStudentById(201);
		// options.addStudentToCourse(1, "Mandarin");
		// options.removeStudentFromCourse(150, "Bengali");
		// System.out.println(options.checkCourseIfExists("Rusdsian"));
		UserOptions uo = new UserOptions();
		uo.printStudentsByCourse("English");
	}
}
