package mainPackage;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Temp {

	public static void main(String[] args) {
		try {
			getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://62.141.46.196/hft?useLegacyDatetimeCode=false&serverTimezone=UTC";
			String username = "hft";
			String password = "hft100hft";

			Connection c = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Kunden");
			ResultSetMetaData rsm = rs.getMetaData();

			while (rs.next()) {
				for (int j = 1; j < rsm.getColumnCount(); j++) {
					System.out.println(rs.getObject(j));
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
