package com.kalachev.task7;

import com.kalachev.task7.DAO.DAOException;
import com.kalachev.task7.DAO.UserOptions;

public class testUI {

	public static void main(String[] args) throws DAOException {
		UserOptions options = new UserOptions();
		// options.findGroupsBySize(20);
		// options.findUsersByCourse("Russian");
		options.addNewStudent("TEST", "TESTOV", 1);
		options.deleteStudentById(201);
	}
}
