package com.kalachev.task7.dao;

import static org.junit.Assert.fail;

import java.io.FileInputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserOptionsDaoTestWithAnotations  {
  
  private static final String JDBC_DRIVER = org.postgresql.Driver.class.getName();
  private static final String JDBC_URL = "jdbc:postgresql://localhost/comkalachevtasksqljdbc";
  private static final String USER = "kalachevartemsql";
  private static final String PASSWORD = "1234";
  private IDatabaseTester databaseTester;
  


  @BeforeEach
  public void setUp() throws Exception {
    cleanInsert(readDataSet());
  }
  
  @Test
  void test() {
    fail();
    
  }

  private IDataSet readDataSet() throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    String file = classLoader.getResource("actualDataSet.xml").getFile();
    return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
  }
  
  private void cleanInsert(IDataSet dataSet) throws Exception {
    databaseTester = new JdbcDatabaseTester(JDBC_DRIVER,JDBC_URL,USER,PASSWORD);
    databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
  }

  
}
