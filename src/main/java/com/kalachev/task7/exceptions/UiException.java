package com.kalachev.task7.exceptions;

public class UiException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UiException() {
    super();
  }

  public UiException(String message) {
    System.out.println(message);
  }
}
