package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import mainPackage.EncryptUtil;

public class DatabaseOnlineBanking {

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

	protected static boolean createOnlineAccount(String id, String pw, int kundennummer) {
		PreparedStatement prepS = null;
		String sqlInsertOnlineBanking = "INSERT INTO OnlineBanking (ID, Passwort) VALUES('?','?')";
		String sqlInsertOnlineJoin = "INSERT INTO OnlineBankingVerknuepfung (Kundennummer, ID) VALUES('?', '?')";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlInsertOnlineBanking);
			prepS.setBytes(1, EncryptUtil.encrypt(id.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(pw.toCharArray()));

			success = prepS.execute();

			prepS = c.prepareStatement(sqlInsertOnlineJoin);
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

	protected static boolean deleteOnlineAccount(String id, String password, int kundennummer) {
		PreparedStatement prepS = null;
		String sqlDeleteOnlineBanking = "DELETE FROM OnlineBanking WHERE ID='?' AND Passwort='?'";
		String sqlDeleteOnlineJoin = "DELETE FROM OnlineBanking WHERE Kundennumer='?' AND ID='?'";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlDeleteOnlineBanking);
			prepS.setBytes(1, EncryptUtil.encrypt(id.toCharArray()));
			prepS.setBytes(2, EncryptUtil.encrypt(password.toCharArray()));

			success = prepS.execute();

			prepS = c.prepareStatement(sqlDeleteOnlineJoin);
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
