package com.kalachev.task7.ui.commands;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.management.OperationsException;

import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.StudentOptions;

public class DeleteByIdCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  StudentOptions options = new StudentOptions();
  Scanner scanner;

  public DeleteByIdCommand(Scanner scanner) {
    super();
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    System.out.println("Enter ID of a student you want to delete");
    try {
      int id = scanner.nextInt();
      if (id < 1) {
        System.out.println("Wrong student id");
        return;
      }
      options.deleteStudentById(id);
      System.out.println("student with id " + id + " deleted");
    } catch (UiException e) {
      e.printStackTrace();
    } catch (InputMismatchException e) {
      System.out.println(BAD_INPUT);
    } catch (OperationsException e) {
      System.out.println("no such student");
    }
  }

}
