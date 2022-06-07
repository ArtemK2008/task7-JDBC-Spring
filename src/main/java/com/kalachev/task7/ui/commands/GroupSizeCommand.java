package com.kalachev.task7.ui.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.GroupOptions;

public class GroupSizeCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  GroupOptions options;

  public GroupSizeCommand(Scanner scanner, GroupOptions options) {
    super();
    this.scanner = scanner;
    this.options = options;
  }

  @Override
  public void execute() {
    System.out.println("Choose maximal group size");
    String inputedGroupSize = scanner.next();
    if (!validateSize(inputedGroupSize)) {
      return;
    }
    int size = Integer.parseInt(inputedGroupSize);
    List<String> groups = findGroups(size);
    if (!groups.isEmpty()) {
      groups.forEach(System.out::println);
    }
  }

  private List<String> findGroups(int size) {
    List<String> groupNames = new LinkedList<>();
    try {
      groupNames = options.findBySize(size);
    } catch (UiException e) {
      System.out.println("no such groups");
    }
    return groupNames;
  }

  private boolean validateSize(String size) {
    boolean isValid = true;
    if (!NumberUtils.isParsable(size)) {
      System.out.println(BAD_INPUT);
      return false;
    }
    if (Integer.parseInt(size) < 0) {
      System.out.println("Max size cant be negative");
      isValid = false;
    }
    return isValid;
  }

}
