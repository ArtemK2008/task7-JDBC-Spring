package com.kalachev.task7.business;

import java.util.ArrayList;
import java.util.List;

import com.kalachev.task7.DAO.DAOException;
import com.kalachev.task7.DAO.UserOptionsDAO;

public class UserOptions {

	UserOptionsDAO dao = new UserOptionsDAO();

	public void printGroupsOfSize(int maxSize) throws UIException {
		if (maxSize < 0) {
			System.out.println("Max size cant be negative");
			return;
		}
		List<String> groups = new ArrayList<>();
		try {
			groups = dao.findGroupsBySize(maxSize);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}

		if (groups.isEmpty()) {
			System.out.println("No such group found");
		} else {
			groups.forEach(System.out::println);
		}
	}

	public void printStudentsByCourse(String course) throws UIException {
		if (!checkIfCourseExists(course)) {
			System.out.println("No such course");
			return;
		}
		List<String> studentOfThisCourse = new ArrayList<>();
		try {
			studentOfThisCourse = dao.findStudentsByCourse(course);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
		if (studentOfThisCourse.isEmpty()) {
			System.out.println("No users in this course");
		} else {
			studentOfThisCourse.forEach(System.out::println);
		}
	}

	public void addNewStudent(String firstName, String lastName, int groupId) throws UIException {
		if (groupId < 1 || groupId > 11) {
			System.out.println("Wrong groupd id");
			return;
		}
		if (checkIfStudentAlreadyInGroup(groupId, firstName, lastName)) {
			System.out.println("Such user exists");
			return;
		}

		try {
			dao.addNewStudent(firstName, lastName, groupId);
			System.out.println("Student " + firstName + " " + lastName + " added to group " + groupId);
		} catch (DAOException e) {
			throw new UIException();
		}
	}

	public void deleteStudentById(int id) throws UIException {
		if (id < 1) {
			System.out.println("Wrong student id");
			return;
		}
		if (!checkIfStudentIdExists(id)) {
			System.out.println("no such student id");
			return;
		}
		try {
			dao.deleteStudentById(id);
			System.out.println("student with id " + id + " deleted");
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void addStudentToCourse(int studentId, String course) throws UIException {
		if (studentId < 0) {
			System.out.println("Wrong id");
			return;
		}
		if (!checkIfCourseExists(course)) {
			System.out.println("no such course");
			return;
		}

		if (!checkIfStudentIdExists(studentId)) {
			System.out.println("no such student id");
			return;
		}
		if (checkIfStudentAlreadyInCourse(studentId, course)) {
			System.out.println("Student already in this course");
			return;
		}

		try {
			dao.addStudentToCourse(studentId, course);
			System.out.println("Student with id " + studentId + " added to course " + course);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void removeStudentFromCourse(int studentId, String course) throws UIException {
		if (studentId < 0) {
			System.out.println("Wrong id");
			return;
		}
		if (!checkIfCourseExists(course)) {
			System.out.println("no such course");
			return;
		}
		if (!checkIfStudentIdExists(studentId)) {
			System.out.println("no such student id");
			return;
		}
		if (!checkIfStudentAlreadyInCourse(studentId, course)) {
			System.out.println("There is no such student in this course");
			return;
		}

		try {
			dao.removeStudentFromCourse(studentId, course);
			System.out.println("Student with id " + studentId + " removed from " + course);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
	}

	public void printCourseNames() throws UIException {
		try {
			List<String> courses = dao.retrieveCoursesNames();
			for (String c : courses) {
				System.out.println(c);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
	}

	public void printCourseNamesByID(int id) throws UIException {
		try {
			List<String> courses = dao.retrieveCoursesNamesByID(id);
			for (String c : courses) {
				System.out.println(c);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
	}

	private boolean checkIfStudentAlreadyInCourse(int id, String course) throws UIException {
		boolean isExist = false;
		try {
			if (dao.checkIfStudentInCourse(id, course)) {
				isExist = true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
		return isExist;
	}

	public boolean checkIfStudentIdExists(int id) throws UIException {
		boolean isExist = false;
		try {
			if (dao.checkStudentIdIfExists(id)) {
				isExist = true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
		return isExist;
	}

	private boolean checkIfCourseExists(String course) throws UIException {
		boolean isExist = false;
		try {
			if (dao.checkCourseIfExists(course)) {
				isExist = true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
		return isExist;
	}

	private boolean checkIfStudentAlreadyInGroup(int groupId, String firstName, String lastName) throws UIException {
		boolean isInGroup = false;
		try {
			if (dao.checkStudntIfExistsInGroup(firstName, lastName, groupId)) {
				isInGroup = true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new UIException();
		}
		return isInGroup;
	}
}
