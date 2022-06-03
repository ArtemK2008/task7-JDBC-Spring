package com.kalachev.task7.ui.commands;

import java.util.Scanner;

import com.kalachev.task7.dao.implementations.StudentsDaoImpl;
import com.kalachev.task7.dao.interfaces.StudentsDao;
import com.kalachev.task7.exceptions.UiException;
import com.kalachev.task7.service.options.StudentOptions;
import com.kalachev.task7.service.validations.Validator;

public class DeleteByIdCommand implements Command {

  static final String BAD_INPUT = "Your Input was not correct";

  Scanner scanner;
  StudentOptions options;

  public DeleteByIdCommand(Scanner scanner,StudentOptions options) {
    super();
    this.scanner = scanner;
    this.options = options;
  }

  @Override
  public void execute() {
    System.out.println("Enter ID of a student you want to delete");
    try {
      int id = Integer.parseInt(scanner.next());
      if (id < 1) {
        System.out.println("Wrong student id");
        return;
      }
      if (!Validator.checkIfStudentIdExists(id)) {
        System.out.println("no such student");
        return;
      }
      options.deleteStudentById(id);
      System.out.println("student with id " + id + " deleted");
    } catch (UiException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println(BAD_INPUT);
    }
  }

}
