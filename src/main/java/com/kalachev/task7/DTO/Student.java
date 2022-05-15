package com.kalachev.task7.DTO;

public class Student {

	private int student_id;
	private int group_id;
	private String first_name;
	private String last_name;

	public Student(int student_id, int group_id, String first_name, String last_name) {
		super();
		this.student_id = student_id;
		this.group_id = group_id;
		this.first_name = first_name;
		this.last_name = last_name;
	}

}
