package com.kalachev.task7.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoursesCreator {

	public Map<String, String> generateCourses() {
		Map<String, String> courses = new LinkedHashMap<>();
		courses.put("English", "place to learn English");
		courses.put("Mandarin", "place to learn Mandarin");
		courses.put("Hindi", "place to learn Hindi");
		courses.put("Spanish", "place to learn Spanish");
		courses.put("French", "place to learn French");
		courses.put("Arabic", "place to learn Arabic");
		courses.put("Bengali", "place to learn Bengali");
		courses.put("Russian", "place to learn Russian");
		courses.put("Portuguese", "place to learn Portuguese");
		courses.put("Ukrainian", "place to learn Ukrainian");
		return courses;
	}

	public List<String> retrieveCoursesNames(Map<String, String> courses) {
		if (courses == null || courses.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return courses.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
	}

}
