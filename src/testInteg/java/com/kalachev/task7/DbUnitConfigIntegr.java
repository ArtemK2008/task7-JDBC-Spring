package com.kalachev.task7;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;

public class DbUnitConfigIntegr extends DBTestCase {

  Properties properties;
  IDatabaseTester databaseTester;
  String driver;
  String urlString;
  String username;
  String password;
  IDataSet beforeData;

  public DbUnitConfigIntegr() {
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

  @Override
  protected void setUpDatabaseConfig(DatabaseConfig config) {
    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
        new PostgresqlDataTypeFactory());
  }
}
