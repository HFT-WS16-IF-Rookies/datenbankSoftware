package databaseUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DatabaseKonto {

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

	private static int generateKontonummer() {
		return (int) (Math.random() * 1000000000) + 1000000000;
	}

	protected static void setGuthaben(double betrag, int kontonummer) {
		PreparedStatement prepS = null;
		String sqlUpdateGuthaben = "UPDATE Konto SET Guthaben=? WHERE Kontonummer=?";
		String sqlGuthaben = "SELECT Guthaben FROM Konto WHERE Kontonummer=?";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlGuthaben);
			prepS.setInt(1, kontonummer);
			ResultSet temp = prepS.executeQuery();
			double guthaben = 0d;
			if (temp.next()) {
				guthaben = temp.getDouble(1);
			}

			prepS = c.prepareStatement(sqlUpdateGuthaben);
			prepS.setDouble(1, guthaben + betrag);
			prepS.setInt(2, kontonummer);
			prepS.execute();

			if (prepS != null) {
				prepS.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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

	protected static ResultSet getAllKonten() {
		ResultSet rs = null;
		try {
			openDatabase();
			PreparedStatement stmt = c.prepareStatement("SELECT * FROM Konto");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static ResultSet getKontenEinesKunden(int kundennummer) {
		ResultSet rs = null;
		try {
			openDatabase();
			PreparedStatement stmt = c.prepareStatement(
					"SELECT ko.kontonummer, blz, iban, bic, guthaben, geheimzahl, typ, eroeffnung FROM Konto AS ko inner join KontoZugreifer As ku on ko.kontonummer=ku.kontonummer WHERE ku.kundennummer="
							+ kundennummer);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static void createKonto(int kundennummer, String typ) {
		ResultSet kontoNummern = null;
		PreparedStatement prepS = null;
		String sqlInsertKunde = "INSERT INTO Konto (kontonummer, blz, iban, bic, guthaben, geheimzahl, typ, eroeffnung) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		String sqlInsertJoin = "INSERT INTO KontoZugreifer (kundennummer, kontonummer) VALUES(?, ?)";
		String sqlGetKontoNr = "SELECT Kontonummer FROM Konto";
		try {
			openDatabase();
			s = c.createStatement();
			kontoNummern = s.executeQuery(sqlGetKontoNr);
			LinkedList<Integer> list = new LinkedList<>();
			int i = 1;
			int temp = generateKontonummer();
			while (kontoNummern.next()) {
				list.add(kontoNummern.getInt(i));
			}
			int blz = 61150020;
			String bic = "ESSLDE66XXX";
			String iban = "DE" + ((int) (Math.random() * 89) + 10) + blz + temp;

			prepS = c.prepareStatement(sqlInsertKunde);
			prepS.setInt(1, temp);
			prepS.setInt(2, blz);
			prepS.setString(3, iban);
			prepS.setString(4, bic);
			prepS.setDouble(5, 0.0d);
			prepS.setInt(6, (int) (Math.random() * 9000) + 1000);
			prepS.setString(7, typ);
			prepS.setDate(8, new Date(System.currentTimeMillis()));

			prepS.execute();

			prepS = c.prepareStatement(sqlInsertJoin);
			prepS.setInt(1, kundennummer);
			prepS.setInt(2, temp);

			prepS.execute();
			if (prepS != null) {
				prepS.close();
			}
			if (s != null) {
				s.close();
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

	protected static void deleteKonto(int kontonummer) {
		PreparedStatement prepS = null;
		String sqlDeleteKonto = "DELETE FROM Konto WHERE Kontonummer=?";
		try {
			openDatabase();
			prepS = c.prepareStatement(sqlDeleteKonto);
			prepS.setInt(1, kontonummer);
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