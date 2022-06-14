package com.kalachev.task7.dao.spring;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbUnitConfigSpring extends DBTestCase {

  Properties properties;
  IDatabaseTester databaseTester;
  String driver;
  String urlString;
  String username;
  String password;
  IDataSet beforeData;
  BasicDataSource dataSource;
  JdbcTemplate template;

  public DbUnitConfigSpring() {
    properties = new Properties();
    URL url = ClassLoader.getSystemResource("DbProperties");
    if (url != null) {
      try (InputStream is = url.openStream()) {
        properties.load(is);
        this.driver = (String) properties.get("JDBC_DRIVER");
        this.urlString = (String) properties.get("URL");
        this.username = (String) properties.get("NAME");
        this.password = (String) properties.get("PASSWORD");
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

      dataSource = new BasicDataSource();
      dataSource.setDriverClassName(driver);
      dataSource.setUrl(urlString);
      dataSource.setUsername(username);
      dataSource.setPassword(password);
      dataSource.setInitialSize(5);
      dataSource.setMaxTotal(10);
      template = new JdbcTemplate(dataSource);
    }
  }

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    databaseTester = new JdbcDatabaseTester(driver, urlString, username,
        password);
  }

  @Override
  protected IDataSet getDataSet() throws Exception {
    return beforeData;
  }

  @Override
  protected DatabaseOperation getSetUpOperation() {
    return DatabaseOperation.CLEAN_INSERT;
  }

  @Override
  protected DatabaseOperation getTearDownOperation() {
    return DatabaseOperation.DELETE_ALL;
  }

}
