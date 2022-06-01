package com.kalachev.task7.dao.initialization;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import com.kalachev.task7.exceptions.DaoException;
import com.kalachev.task7.utilities.ConnectionManager;
import com.kalachev.task7.utilities.ExceptionsUtil;
import com.kalachev.task7.utilities.JdbcUtil;

public class SchemaInitializer {

  public void createSchema() throws DaoException {
    Connection connection = null;
    Statement statement = null;
    try {
      java.net.URL url = SchemaInitializer.class.getClassLoader()
          .getResource("StartupSqlData.sql");
      List<String> tableData = Files.readAllLines(Paths.get(url.toURI()));
      String sql = tableData.stream().collect(Collectors.joining());
      connection = ConnectionManager.openDbConnectionForNewUser();
      statement = connection.createStatement();
      statement.executeUpdate(sql);
    } catch (Exception e) {
      e.printStackTrace();
      String methodName = ExceptionsUtil.getCurrentMethodName();
      String className = ExceptionsUtil.getCurrentClassName();
      throw new DaoException(methodName, className);
    } finally {
      JdbcUtil.closeAll(statement, connection);
    }
  }
}
