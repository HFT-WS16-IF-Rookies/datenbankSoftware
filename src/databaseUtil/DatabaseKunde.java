package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class DatabaseKunde {

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://62.141.46.196/hft?useLegacyDatetimeCode=false&serverTimezone=UTC";
	static String username = "hft";
	static String password = "hft100hft";

	static Connection c;
	static Statement s;

	private static void openDatabase() {
		try {
			c = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected static ResultSet getAllKunden() {
		ResultSet rs = null;
		try {
			openDatabase();
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM Kunde");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static int getKundenNummer(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sqlGetKNr = "SELECT Kundennummer FROM Kunde WHERE vorname=? AND nachname=?";
		int toReturn = 0;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlGetKNr);
			prepS.setString(1, vorname);
			prepS.setString(2, nachname);

			ResultSet tempRS = prepS.executeQuery();
			if (tempRS.next()) {
				toReturn = tempRS.getInt(1);
			} else {
				toReturn = 0;
			}
			if (prepS != null) {
				prepS.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	protected static void createKunde(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sqlInsertKunde = "INSERT INTO Kunde (kundennummer, vorname, nachname, gelistetSeit) VALUES(?,?,?,?)";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlInsertKunde);
			prepS.setInt(1, 4);
			prepS.setString(2, vorname);
			prepS.setString(3, nachname);
			prepS.setDate(4, new Date(System.currentTimeMillis()));

			prepS.execute();
			if (prepS != null) {
				prepS.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected static void deleteKunde(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sql = "DELETE FROM Kunde WHERE vorname=? AND nachname=?";
		try {
			openDatabase();
			prepS = c.prepareStatement(sql);
			prepS.setString(1, vorname);
			prepS.setString(2, nachname);

			prepS.execute();
			if (prepS != null) {
				prepS.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
