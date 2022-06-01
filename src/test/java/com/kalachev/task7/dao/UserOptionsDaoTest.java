package com.kalachev.task7.dao;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Test;

class UserOptionsDaoTest extends DBTestCase {
  

  public UserOptionsDaoTest() {
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver");
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost/comkalachevtasksqljdbc");
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "kalachevartemsql");
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "1234");
    
  }

  @Test
  void testFindGroupsBySize() {
    fail();
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    String file = classLoader.getResource("actualDataSet.xml").getFile();
    return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
  }
  
  

}
