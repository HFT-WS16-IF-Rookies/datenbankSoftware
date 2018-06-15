package databaseUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import mainPackage.BasicUtil;
import mainPackage.EncryptUtil;

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
			prepS.setString(1, Integer.toString(kontonummer));
			ResultSet temp = prepS.executeQuery();
			String guthaben = "";
			char[] key = { 'a' };
			if (temp.next()) {
				guthaben = new String(EncryptUtil.decrypt(temp.getBytes(1), key));
			}

			prepS = c.prepareStatement(sqlUpdateGuthaben);
			prepS.setBytes(1,
					EncryptUtil.encrypt(Double.toString(Double.parseDouble(guthaben) + betrag).toCharArray(), key));
			prepS.setString(2, Integer.toString(kontonummer));
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
					"SELECT ko.Kontonummer, BLZ, IBAN, BIC,Guthaben, Typ, Eroeffnung FROM Konto AS ko inner join KontoZugreifer As ku on ko.Kontonummer=ku.Kontonummer WHERE ku.Kundennummer="
							+ kundennummer);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	protected static void createKonto(int kundennummer, String typ, String eroefnnug) {
		ResultSet kontoNummern = null;
		PreparedStatement prepS = null;
		String sqlInsertKunde = "INSERT INTO Konto (Kontonummer, BLZ, IBAN, BIC, Guthaben, Geheimzahl, Typ, Eroeffnung) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		String sqlInsertJoin = "INSERT INTO KontoZugreifer (Kundennummer, Kontonummer) VALUES(?, ?)";
		String sqlGetKontoNr = "SELECT Kontonummer FROM Konto";
		try {
			openDatabase();
			s = c.createStatement();
			kontoNummern = s.executeQuery(sqlGetKontoNr);
			LinkedList<String> list = new LinkedList<>();
			int i = 1;
			int temp = generateKontonummer();
			while (kontoNummern.next()) {
				list.add(kontoNummern.getString(i));
			}
			for (int j = 0; j < list.size(); j++) {
				if (list.get(i).equals(Integer.toString(temp))) {
					temp = generateKontonummer();
					j = 0;
				}
			}

			String blz = "61150020";
			String bic = "ESSLDE66XXX";
			String iban = "DE" + ((int) (Math.random() * 89) + 10) + blz + temp;

			prepS = c.prepareStatement(sqlInsertKunde);
			prepS.setString(1, Integer.toString(temp));
			char[] key = { 'a' };
			prepS.setBytes(2, EncryptUtil.encrypt(blz.toCharArray(), key));
			prepS.setBytes(3, EncryptUtil.encrypt(iban.toCharArray(), key));
			prepS.setBytes(4, EncryptUtil.encrypt(bic.toCharArray(), key));
			prepS.setBytes(5, EncryptUtil.encrypt(Double.toString(0.0d).toCharArray(), key));
			prepS.setBytes(6,
					EncryptUtil.encrypt(Integer.toString((int) (Math.random() * 9000) + 1000).toCharArray(), key));
			prepS.setBytes(7, EncryptUtil.encrypt(typ.toCharArray(), key));
			prepS.setBytes(8, EncryptUtil.encrypt(BasicUtil.todaysDate().toCharArray(), key));

			prepS.execute();

			prepS = c.prepareStatement(sqlInsertJoin);
			prepS.setInt(1, kundennummer);
			prepS.setString(2, Integer.toString(temp));

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
			prepS.setString(1, Integer.toString(kontonummer));
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