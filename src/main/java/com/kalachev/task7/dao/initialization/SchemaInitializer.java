package com.kalachev.task7.dao.initialization;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionMaker;
import com.kalachev.task7.utilities.JdbcCloser;

public class SchemaInitializer {

  public void createSchema() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      java.net.URL url = SchemaInitializer.class.getClassLoader()
          .getResource("StartupSqlData.sql");
      List<String> tableData = Files.readAllLines(Paths.get(url.toURI()));
      String sql = tableData.stream().collect(Collectors.joining());
      connection = ConnectionMaker.getDbConnectionForNewUser();
      statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
      throw new DaoException();
    } finally {
      JdbcCloser.closeAll(statement, connection);
    }
  }
}
