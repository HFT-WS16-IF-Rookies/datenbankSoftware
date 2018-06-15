package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mainPackage.BasicUtil;
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

	protected static void createUeberweisung(String sender, String empfaenger, String art, double betrag, String zweck,
			int kontonummer) {
		PreparedStatement prepS = null;
		String sqlCreateUeberweisung = "INSERT INTO Ueberweisung (Kontonummer, Sender, Empfaenger, Art, Betrag, Verwendungszweck, Datum) VALUES(?,?,?,?,?,?,?)";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlCreateUeberweisung);
			char[] key = { 'a' };
			prepS.setInt(1, kontonummer);
			prepS.setBytes(2, EncryptUtil.encrypt(sender.toCharArray(), key));
			prepS.setBytes(3, EncryptUtil.encrypt(empfaenger.toCharArray(), key));
			prepS.setBytes(4, EncryptUtil.encrypt(art.toCharArray(), key));
			prepS.setBytes(5, EncryptUtil.encrypt(Double.toString(betrag).toCharArray(), key));
			prepS.setBytes(6, EncryptUtil.encrypt(zweck.toCharArray(), key));
			prepS.setBytes(7, EncryptUtil.encrypt(BasicUtil.todaysDate().toCharArray(), key));

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
