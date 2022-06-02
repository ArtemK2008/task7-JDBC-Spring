package com.kalachev.task7.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class StudentDaoImplTest extends DBTestCase {
  Properties properties;
  IDatabaseTester databaseTester;
  String driver;
  String urlString;
  String username;
  String password;
  IDataSet beforeData;

  public StudentDaoImplTest() {
    properties = new Properties();
    URL url = ClassLoader.getSystemResource("DbProperties");
    if (url != null) {
      try (InputStream is = url.openStream()) {
        properties.load(is);
        this.driver = (String) properties.get("JDBC_DRIVER");
        this.urlString = (String) properties.get("URL_CREATED");
        this.username = (String) properties.get("USERNAME_CREATED");
        this.password = (String) properties.get("PASSWORD_CREATED");
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
          driver);
      System.setProperty(
          PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, urlString);
      System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
          username);
      System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
          password);
    }
  }

  @Override
  @Before
  public void setUp() throws Exception {
    databaseTester = new JdbcDatabaseTester(driver, urlString, username,
        password);
    String file = getClass().getClassLoader()
        .getResource("ActualStudentDataSet.xml").getFile();
    beforeData = new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    databaseTester.setDataSet(beforeData);
    databaseTester.onSetup();
  }

  @Override
  protected IDataSet getDataSet()
      throws DataSetException, FileNotFoundException {
    String file = getClass().getClassLoader()
        .getResource("ActualStudentDataSet.xml").getFile();
    return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
  }

  @Override
  protected DatabaseOperation getSetUpOperation() {
    return DatabaseOperation.REFRESH;
  }

  @Override
  protected DatabaseOperation getTearDownOperation() {
    return DatabaseOperation.DELETE_ALL;
  }

  @Test
  public void givenDataSetEmptySchema_whenDataSetCreated_thenTablesAreEqual()
      throws Exception {
    IDataSet expectedDataSet = getDataSet();
    ITable expectedTable = expectedDataSet.getTable("students");
    IDataSet databaseDataSet = getConnection().createDataSet();
    ITable actualTable = databaseDataSet.getTable("students");
    Assertion.assertEquals(expectedTable, actualTable);
  }

}
