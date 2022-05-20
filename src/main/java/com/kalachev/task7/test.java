package com.kalachev.task7;

import java.io.IOException;

import com.kalachev.task7.DAO.DAOException;
import com.kalachev.task7.DAO.DatabaseCreator;
import com.kalachev.task7.UI.ConsoleApp;

public class test {

	public static void main(String[] args) throws DAOException, IOException {

		DatabaseCreator db = new DatabaseCreator();
		db.createDatabase();
		db.createUser();

		ConsoleApp app = new ConsoleApp();
		app.runSchoolApp();

	}

}
