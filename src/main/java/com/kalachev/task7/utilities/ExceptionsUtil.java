package com.kalachev.task7.utilities;

public class ExceptionsUtil {

  private ExceptionsUtil() {
    super();
  }

  public static String getCurrentMethodName() {
    return Thread.currentThread().getStackTrace()[2].getMethodName();
  }

  public static String getCurrentClassName() {
    return Thread.currentThread().getStackTrace()[2].getClassName();
  }
}
