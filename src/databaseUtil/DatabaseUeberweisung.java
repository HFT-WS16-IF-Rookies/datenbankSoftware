package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import mainPackage.EncryptUtil;

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

	protected static boolean createUeberweisung(String sender, String empfaenger, String art, double betrag,
			String zweck, int kundennummer) {
		PreparedStatement prepS = null;
		String sqlCreateUeberweisung = "INSERT INTO Ueberweisung (Sender, Empfaenger, Art, Betrag, Verwendungszweck, Datum) VALUES('?','?','?','?','?','?')";
		String sqlCreateUeberweisungJoin = "INSERT INTO UeberweisungsVerknuepfung (Kundennummer, Uerberweisungsummer) VALUES('?', '?')";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlCreateUeberweisung);
			prepS.setBytes(1, EncryptUtil.encrypt(sender.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(empfaenger.toCharArray()));
			prepS.setString(3, art);
			prepS.setBytes(4, EncryptUtil.encrypt(Double.toString(betrag).toCharArray()));
			prepS.setBytes(5, EncryptUtil.encrypt(zweck.toCharArray()));
			prepS.setString(6, EncryptUtil.encrypt(todaysDate()));

			success = prepS.execute();

			prepS = c.prepareStatement(sqlCreateUeberweisungJoin);
			prepS.setInt(1, kundennummer);
			prepS.setBytes(2, EncryptUtil.encrypt(id.toCharArray()));

			success = prepS.execute();

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
		return success;
	}
}
