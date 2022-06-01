package com.kalachev.task7.exceptions;

public class DaoException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public DaoException(String methodName, String className) {
    System.out.println("Dao error " + System.lineSeparator() + "Method "
        + methodName + " in class " + className + " failed");
  }

}
