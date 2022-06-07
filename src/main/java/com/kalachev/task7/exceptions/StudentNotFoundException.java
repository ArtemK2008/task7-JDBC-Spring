package com.kalachev.task7.exceptions;

public class StudentNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public StudentNotFoundException(int id) {
    System.out.println("Student with id " + id + " was not found!");
  }

  public StudentNotFoundException(String name, String lastname) {
    System.out.println("Student " + name + " " + lastname + " was not found!");
  }

}
