package com.kalachev.task7.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StudentsOfEachGroup {
	Random random = new Random();
	List<Integer> fullGroups = new ArrayList<>();
	static final int MAX_GROUP_SIZE = 30;
	static final String GROUPLESS = "students without groups";

	public Map<String, List<String>> assignStudentsToGroups(List<String> students, List<String> groups) {
		Map<String, List<String>> studentsInGroup = prepareGroups(groups);

		for (String student : students) {
			int currRandom = random.nextInt(11);
			while (fullGroups.contains(currRandom)) {
				currRandom = random.nextInt(11);
			}

			List<String> currentStudents = studentsInGroup.get(groups.get(currRandom));
			if (currentStudents.size() >= MAX_GROUP_SIZE) {
				fullGroups.add(currRandom);
				while (fullGroups.contains(currRandom)) {
					currRandom = random.nextInt(11);
					currentStudents = studentsInGroup.get(groups.get(currRandom));
				}
			}
			currentStudents.add(student);
			studentsInGroup.put(groups.get(currRandom), currentStudents);
		}

		checkForSmallGroup(studentsInGroup);
		return studentsInGroup;
	}

	private void checkForSmallGroup(Map<String, List<String>> studentsInGroup) {
		for (String group : studentsInGroup.keySet()) {
			if (studentsInGroup.get(group).size() < 10) {
				studentsInGroup.get(group).stream().forEach(s -> studentsInGroup.get(GROUPLESS).add(s));
				studentsInGroup.put(group, new ArrayList<>());
			}
		}
	}

	private Map<String, List<String>> prepareGroups(List<String> groups) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		groups.stream().forEach(g -> map.put(g, new ArrayList<>()));
		return map;
	}
}
