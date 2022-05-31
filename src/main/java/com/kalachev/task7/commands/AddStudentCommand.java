package com.kalachev.task7.commands;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.management.OperationsException;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.menu.UserOptions;

public class AddStudentCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;

  public AddStudentCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    try {
      System.out.println("Enter student name");
      String name = scanner.next();
      System.out.println("Enter student last name");
      String lastname = scanner.next();
      System.out.println("Enter group id to add this student");
      int groupId = scanner.nextInt();
      if (groupId < 1 || groupId > 11) {
        System.out.println("Wrong groupd id");
        return;
      }
      addStudent(name, lastname, groupId);
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    }
  }

  private void addStudent(String name, String lastname, int groupId) {
    UserOptions options = new UserOptions();
    try {
      options.addNewStudent(name, lastname, groupId);
      System.out.println(
          "Student " + name + " " + lastname + " added to group " + groupId);
    } catch (OperationsException e) {
      System.out.println("User Already exists");
    } catch (UiException e) {
      System.out.println(BAD_INPUT);
    }
  }

}
