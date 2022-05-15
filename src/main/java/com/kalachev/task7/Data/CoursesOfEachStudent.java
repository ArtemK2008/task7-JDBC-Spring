package com.kalachev.task7.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CoursesOfEachStudent {

	Random random = new Random();

	public Map<String, List<String>> assignStudentsIDToCourse(Map<String, String> studentsWithId,
			List<String> courses) {
		Map<String, List<String>> coursesOfEachStudent = new HashMap<>();
		for (String id : studentsWithId.keySet()) {
			int amoutOfCourses = random.nextInt(3) + 1;
			List<String> coursesTaken = new ArrayList<>();
			List<String> coursesAlreadyEnrolled = new ArrayList<>();
			for (int i = 0; i < amoutOfCourses; i++) {
				String tempCourse = courses.get(random.nextInt(10));
				while (coursesAlreadyEnrolled.contains(tempCourse)) {
					tempCourse = courses.get(random.nextInt(10));
				}
				coursesTaken.add(tempCourse);
				coursesAlreadyEnrolled.add(tempCourse);
			}
			coursesOfEachStudent.put(id, coursesTaken);
		}
		return coursesOfEachStudent;
	}

}
