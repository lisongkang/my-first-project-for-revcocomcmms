package com.maywide.tool.entityfactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils
{
  public static Connection getConnection(String dbURL, String username, String password)
  {
    Connection conn = null;
    try {
      String driver = "com.mysql.jdbc.Driver";
      Class.forName(driver);
      conn = DriverManager.getConnection(dbURL, username, password);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public static void closeAll(Connection conn, Statement stmt, ResultSet result)
  {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (stmt != null) {
      try {
        stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (result != null)
      try {
        result.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }

  public static String getIndexStr(String tableName, String dbURL, String username, String password)
  {
    Statement stmt2 = null;
    Statement stmt3 = null;
    ResultSet indexrs = null;
    ResultSet indexrs2 = null;
    String indexStr = "";
    Connection conn = getConnection(dbURL, username, password);
    try {
      stmt2 = conn.createStatement();
      indexrs = stmt2
        .executeQuery("show index from " + 
        tableName.toUpperCase() + 
        " where non_unique='0'");
      while (indexrs.next()) {
        String innerIndexStr = "";
        String indexName = indexrs.getString("key_name");
        stmt3 = conn.createStatement();
        indexrs2 = stmt3
          .executeQuery("show index from " + tableName.toUpperCase() + " where key_name='" +
          indexName + "'");
        int indexCount = 0;
        while (indexrs2.next()) {
          innerIndexStr = indexrs2.getString("column_name");
          indexCount++;
        }
        if (indexCount > 1) {
          innerIndexStr = "";
        }
        indexStr = indexStr + innerIndexStr + ";";
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeAll(conn, stmt2, indexrs);
      closeAll(null, stmt3, indexrs2);
    }
    return indexStr;
  }

  public static String getPkStr(String tableName, String dbURL, String username, String password)
  {
    String pkStr = "";
    Connection conn = getConnection(dbURL, username, password);
    ResultSet pkrs = null;
    try {
      DatabaseMetaData dbMetaData = conn.getMetaData();
      pkrs = dbMetaData.getPrimaryKeys(null, null, 
        tableName.toUpperCase());
      while (pkrs.next()) {
        pkStr = pkStr + (String)pkrs.getObject(4);
        pkStr = pkStr + ";";
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeAll(conn, null, pkrs);
    }
    return pkStr;
  }
}