package mainPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

	public static void main(String[] args) {
		try {
			ResultSet rs = s.executeQuery("SELECT * FROM Kontos");
			printResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void printResultSet(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 0; i < tempMetaData.getColumnCount(); i++) {
				System.out.print(tempMetaData.getColumnName(i) + "     ");
			}
			System.out.println();
			while (tempRS.next()) {
				for (int i = 0; i < tempMetaData.getColumnCount(); i++) {
					System.out.print(tempRS.getObject(i).toString() + " ");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
