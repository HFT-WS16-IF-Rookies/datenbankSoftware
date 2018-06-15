
package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	protected static ResultSet getAllOBAccounts() {
		ResultSet rs = null;
		try {
			openDatabase();
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM OnlineBanking");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static ResultSet getKundeOBAccount(int kundennummer) {
		ResultSet rs = null;
		try {
			openDatabase();
			PreparedStatement stmt = c.prepareStatement(
					"SELECT ba.ID, ba.Passwort FROM OnlineBanking AS ba inner join OnlineBankingBesitzer As bb on ba.ID=bb.ID WHERE bb.Kontonummer="
							+ kundennummer);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static void createOnlineAccount(String id, String pw, int kontonummer) {
		PreparedStatement prepS = null;
		String sqlInsertOnlineBanking = "INSERT INTO OnlineBanking (ID, Passwort) VALUES(?,?)";
		String sqlInsertOnlineJoin = "INSERT INTO OnlineBankingBesitzer (Kontonummer, ID) VALUES(?, ?)";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlInsertOnlineBanking);
			char[] key = { 'a' };
			prepS.setString(1, id);
			prepS.setBytes(2, EncryptUtil.encrypt(pw.toCharArray(), key));

			prepS.execute();

			prepS = c.prepareStatement(sqlInsertOnlineJoin);
			prepS.setInt(1, kontonummer);
			prepS.setString(2, id);
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

	protected static void deleteOnlineAccount(String id, String password, int kontonummer) {
		PreparedStatement prepS = null;
		String sqlDeleteOnlineBanking = "DELETE FROM OnlineBanking WHERE ID=? AND Passwort=?";
		String sqlDeleteOnlineJoin = "DELETE FROM OnlineBankingBesitzer WHERE Kontonummer=? AND ID=?";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlDeleteOnlineBanking);
			char[] key = { 'a' };
			prepS.setString(1, id);
			prepS.setBytes(2, EncryptUtil.encrypt(password.toCharArray(), key));

			prepS.execute();

			prepS = c.prepareStatement(sqlDeleteOnlineJoin);
			prepS.setInt(1, kontonummer);
			prepS.setString(2, id);

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
