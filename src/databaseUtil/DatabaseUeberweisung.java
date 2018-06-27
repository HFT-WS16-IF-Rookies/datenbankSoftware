package databaseUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUeberweisung {

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

	protected static ResultSet getAllUeberweisungen() {
		ResultSet rs = null;
		try {
			openDatabase();
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM Ueberweisung");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static ResultSet getUeberweisungenEinesKontos(int kundennummer) {
		ResultSet rs = null;
		try {
			openDatabase();
			Statement stmt = c.createStatement();
			rs = stmt.executeQuery(
					"SELECT ba.* FROM Ueberweisung AS ba inner join Konto As ko on ko.kontonummer=ba.kontonummer WHERE ko.kontonummer="
							+ kundennummer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static void createUeberweisung(String sender, String empfaenger, String art, double betrag, String zweck,
			int kontonummer) {
		PreparedStatement prepS = null;
		String sqlCreateUeberweisung = "INSERT INTO Ueberweisung (kontonummer, sender, empfaenger, art, betrag, verwendungszweck, datum) VALUES(?,?,?,?,?,?,?)";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlCreateUeberweisung);
			prepS.setInt(1, kontonummer);
			prepS.setString(2, sender);
			prepS.setString(3, empfaenger);
			prepS.setString(4, art);
			prepS.setDouble(5, betrag);
			prepS.setString(6, zweck);
			prepS.setDate(7, new Date(System.currentTimeMillis()));

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
