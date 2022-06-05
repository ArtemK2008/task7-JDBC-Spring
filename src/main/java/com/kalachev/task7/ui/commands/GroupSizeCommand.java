package com.kalachev.task7.ui.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
    try {
      int size = Integer.parseInt(scanner.next());
      if (size < 0) {
        System.out.println("Max size cant be negative");
        return;
      }
      List<String> groups = findGroups(size);
      groups.forEach(System.out::println);
    } catch (NumberFormatException e) {
      System.out.println(BAD_INPUT);
    } catch (Exception e) {
      e.printStackTrace();
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

}
