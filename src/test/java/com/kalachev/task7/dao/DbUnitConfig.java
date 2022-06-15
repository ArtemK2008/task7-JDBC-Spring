package com.kalachev.task7.dao;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kalachev.task7.configuration.ConsoleAppConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConsoleAppConfig.class)
@PropertySource("classpath:DbProperties")
public class DbUnitConfig extends DBTestCase {

  Properties properties;
  IDatabaseTester databaseTester;
  @Value("${JDBC_DRIVER}")
  String driver;
  @Value("${URL}")
  String urlString;
  @Value("${NAME}")
  String username;
  @Value("${PASSWORD}")
  String password;
  IDataSet beforeData;
  @Autowired
  BasicDataSource dataSource;
  @Autowired
  JdbcTemplate template;

  public DbUnitConfig() {

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
