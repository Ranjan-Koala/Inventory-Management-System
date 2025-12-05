package com.inventory.management.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/inventory_db?serverTimezone=UTC";
	  private static final String USER = "root";
	  private static final String PASS = "Ranjan@123";

	  public static Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(URL, USER, PASS);
	  }
}
