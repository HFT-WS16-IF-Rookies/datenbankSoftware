package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConsoleAccess {

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://62.141.46.196/hft?useLegacyDatetimeCode=false&serverTimezone=UTC";
	static String username = "hft";
	static String password = "hft100hft";

	static Connection c;
	static Statement s;

	static {
		try {
			c = DriverManager.getConnection(url, username, password);
			s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
