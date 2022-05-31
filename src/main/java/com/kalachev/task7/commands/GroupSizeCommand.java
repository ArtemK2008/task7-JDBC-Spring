package com.kalachev.task7.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.menu.UserOptions;

public class GroupSizeCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;

  public GroupSizeCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("Choose maximal group size ");
    try {
      int size = scanner.nextInt();
      if (size < 0) {
        System.out.println("Max size cant be negative");
        return;
      }
      List<String> groups = findGroups(size);
      groups.forEach(System.out::println);
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<String> findGroups(int size) {
    UserOptions userOptions = new UserOptions();
    List<String> groupNames = new ArrayList<>();
    try {
      groupNames = userOptions.findGroupsBySize(size);
    } catch (UiException e) {
      System.out.println("no such groups");
    }
    return groupNames;
  }

}