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

	protected static int getKundenNummer(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sqlGetKNr = "SELECT Kundennummer FROM Kunde WHERE Vorname='?' AND Nachname='?'";
		int toReturn = 0;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlGetKNr);
			prepS.setBytes(1, EncryptUtil.encrypt(vorname.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(nachname.toCharArray()));

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

	protected static boolean createKunde(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sqlInsertKunde = "INSERT INTO Kunde (Vorname, Nachname) VALUES('?','?')";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlInsertKunde);
			prepS.setBytes(1, EncryptUtil.encrypt(vorname.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(nachname.toCharArray()));

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

	protected static boolean updateKunde(String vornameAlt, String nachnameAlt, String vornameNeu, String nachnameNeu) {
		PreparedStatement prepS = null;
		String sql = "UPDATE Kunde SET Vorname='?', Nachname='?' WHERE Vorname='?' AND Nachname='?'";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sql);
			prepS.setBytes(1, EncryptUtil.encrypt(vornameNeu.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(nachnameNeu.toCharArray()));
			prepS.setBytes(3, EncryptUtil.encrypt(vornameAlt.toCharArray()));
			prepS.setBytes(4, EncryptUtil.encrypt(nachnameAlt.toCharArray()));

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

	protected static boolean deleteKunde(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sql = "DELETE FROM Kunde WHERE Vorname='?' AND Nachname='?'";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sql);
			prepS.setBytes(1, EncryptUtil.encrypt(vorname.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(nachname.toCharArray()));

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
