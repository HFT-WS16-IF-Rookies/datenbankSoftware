package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mainPackage.EncryptUtil;

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
		String sqlGetKNr = "SELECT Kundennummer FROM Kunde WHERE Vorname=? AND Nachname=?";
		int toReturn = 0;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlGetKNr);
			char[] key = { 'a' };
			prepS.setBytes(1, EncryptUtil.encrypt(vorname.toCharArray(), key));
			prepS.setBytes(2, EncryptUtil.encrypt(nachname.toCharArray(), key));

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
		String sqlInsertKunde = "INSERT INTO Kunde (Vorname, Nachname) VALUES(?,?)";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlInsertKunde);
			char[] key = { 'a' };
			prepS.setBytes(1, EncryptUtil.encrypt(vorname.toCharArray(), key));
			prepS.setBytes(2, EncryptUtil.encrypt(nachname.toCharArray(), key));

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

	protected static void updateKunde(String vornameAlt, String nachnameAlt, String vornameNeu, String nachnameNeu) {
		PreparedStatement prepS = null;
		String sql = "UPDATE Kunde SET Vorname=?, Nachname=? WHERE Vorname=? AND Nachname=?";
		try {
			openDatabase();
			prepS = c.prepareStatement(sql);
			char[] key = { 'a' };
			prepS.setBytes(1, EncryptUtil.encrypt(vornameNeu.toCharArray(), key));
			prepS.setBytes(2, EncryptUtil.encrypt(nachnameNeu.toCharArray(), key));
			prepS.setBytes(3, EncryptUtil.encrypt(vornameAlt.toCharArray(), key));
			prepS.setBytes(4, EncryptUtil.encrypt(nachnameAlt.toCharArray(), key));

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
		String sql = "DELETE FROM Kunde WHERE Vorname=? AND Nachname=?";
		try {
			openDatabase();
			prepS = c.prepareStatement(sql);
			char[] key = { 'a' };
			prepS.setBytes(1, EncryptUtil.encrypt(vorname.toCharArray(), key));
			prepS.setBytes(2, EncryptUtil.encrypt(nachname.toCharArray(), key));

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
