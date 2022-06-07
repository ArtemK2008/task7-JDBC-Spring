package com.kalachev.task7.exceptions;

public class GroupNotFoundException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public GroupNotFoundException(String message) {
    System.out.println(message);
  }

  public GroupNotFoundException() {
    super();
  }

}
