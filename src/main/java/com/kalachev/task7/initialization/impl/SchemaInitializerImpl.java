package com.kalachev.task7.initialization.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.kalachev.task7.initialization.SchemaInitializer;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.JdbcUtil;

@Component
public class SchemaInitializerImpl implements SchemaInitializer {

  @Override
  public void createSchema() {
    Connection connection = null;
    Statement statement = null;
    try {
      java.net.URL url = SchemaInitializerImpl.class.getClassLoader()
          .getResource("StartupSqlData.sql");
      List<String> tableData = Files.readAllLines(Paths.get(url.toURI()));
      String sql = tableData.stream().collect(Collectors.joining());
      connection = ConnectionManager.openDbConnection();
      statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (Exception e) {
      System.out.println("Error while creating schema");
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }
}
