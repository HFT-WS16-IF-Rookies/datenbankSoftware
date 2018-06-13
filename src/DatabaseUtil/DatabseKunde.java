package DatabaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabseKunde {

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

	protected static boolean createKunde(String vorname, String nachname) {
		PreparedStatement prepS = null;
		String sql = "INSERT INTO ? (Vorname, Nachname) VALUES('?','?')";
		boolean success = false;
		try {
			openDatabase();
			prepS = c.prepareStatement(sql);
			prepS.setString(1, vorname);
			prepS.setString(2, nachname);

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
			prepS.setString(1, vornameNeu);
			prepS.setString(2, nachnameNeu);
			prepS.setString(3, vornameAlt);
			prepS.setString(4, nachnameAlt);

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
