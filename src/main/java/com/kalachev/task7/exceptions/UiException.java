package com.kalachev.task7.exceptions;

public class UiException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UiException() {
    super();
  }

  public UiException(String methodName, String className) {
    System.out.println("UI error " + System.lineSeparator() + "Method "
        + methodName + " in class " + className + " failed");
  }
}
