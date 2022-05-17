package com.kalachev.task7.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupCreator {
	Random random;
	private static final String GROUPLESS = "students without groups";

	public GroupCreator() {
		super();
		random = new Random();
	}

	public GroupCreator(int seed) {
		super();
		this.random = new Random(seed);
	}

	public List<String> generateGroups() {
		List<String> groups = new ArrayList<>();
		List<String> groupsUsed = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String tempString = generateGroupName();
			while (groupsUsed.contains(tempString)) {
				tempString = generateGroupName();
			}
			groupsUsed.add(tempString);
			groups.add(tempString);
		}
		groups.add(GROUPLESS);
		return groups;
	}

	private String generateCharPart() {
		int leftLimit = 97;
		int rightLimit = 122;
		int targetStringLength = 2;
		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	private String generateNumberPart() {
		int leftLimit = 48;
		int rightLimit = 57;
		int targetStringLength = 2;
		return random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	private String generateGroupName() {
		return generateCharPart() + "-" + generateNumberPart();
	}

}
