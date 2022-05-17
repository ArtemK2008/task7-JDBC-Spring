package com.kalachev.task7.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestCourseCreator {
	Map<String, String> courseWithDescription;

	@BeforeEach
	void fillExpexted() {
		courseWithDescription = new LinkedHashMap<String, String>();
		courseWithDescription.put("English", "place to learn English");
		courseWithDescription.put("Mandarin", "place to learn Mandarin");
		courseWithDescription.put("Hindi", "place to learn Hindi");
		courseWithDescription.put("Spanish", "place to learn Spanish");
		courseWithDescription.put("French", "place to learn French");
		courseWithDescription.put("Arabic", "place to learn Arabic");
		courseWithDescription.put("Bengali", "place to learn Bengali");
		courseWithDescription.put("Russian", "place to learn Russian");
		courseWithDescription.put("Portuguese", "place to learn Portuguese");
		courseWithDescription.put("Ukrainian", "place to learn Ukrainian");
	}

	@Test
	void testGenerateCourses_shouldReturnHardcodedValues_whenMethoIsCalled() {
		CoursesCreator coursesCreator = new CoursesCreator();
		Map<String, String> actual = coursesCreator.generateCourses();
		Map<String, String> expected = courseWithDescription;
		assertEquals(expected, actual);
	}

	@Test
	void testRetrieveInput_shouldReturnCourseNames_whenCoursesExists() {
		CoursesCreator coursesCreator = new CoursesCreator();
		List<String> actual = coursesCreator.retrieveCoursesNames(courseWithDescription);
		List<String> expected = new ArrayList<String>();
		expected.add("English");
		expected.add("Mandarin");
		expected.add("Hindi");
		expected.add("Spanish");
		expected.add("French");
		expected.add("Arabic");
		expected.add("Bengali");
		expected.add("Russian");
		expected.add("Portuguese");
		expected.add("Ukrainian");
		assertEquals(expected, actual);
	}

	@Test
	void testRetrieveInput_shouldThrowException_whenCoursesIsNull() {
		CoursesCreator coursesCreator = new CoursesCreator();
		assertThrows(IllegalArgumentException.class, () -> coursesCreator.retrieveCoursesNames(null));
	}

	@Test
	void testRetrieveInput_shouldThrowException_whenCoursesIsEmpty() {
		CoursesCreator coursesCreator = new CoursesCreator();
		courseWithDescription = new HashMap<String, String>();
		assertThrows(IllegalArgumentException.class, () -> coursesCreator.retrieveCoursesNames(courseWithDescription));
	}

}
