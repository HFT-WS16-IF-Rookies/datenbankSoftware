package mainPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Print {

	public static void printAllKunden(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
				System.out.print(tempMetaData.getColumnName(i) + "    ");
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(tempRS.getInt(1) + "               ");
				for (int i = 2; i < tempMetaData.getColumnCount(); i++) {
					System.out.print(tempRS.getString(i) + "        ");
				}
				System.out.print(tempRS.getDate(tempMetaData.getColumnCount()));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAllKonten(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
				System.out.print(tempMetaData.getColumnName(i) + "            ");
				if (i == 3) {
					System.out.print("          ");
				}
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(tempRS.getInt(1) + "             ");
				System.out.print(tempRS.getString(2) + "       ");
				System.out.print(tempRS.getString(3) + "    ");
				System.out.print(tempRS.getString(4) + "    ");
				System.out.print(tempRS.getDouble(5) + "               ");
				System.out.print(tempRS.getInt(6) + "             ");
				System.out.print(tempRS.getString(7) + "           ");
				System.out.print(tempRS.getString(8) + "      ");
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAllOBAccounts(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
				System.out.print(tempMetaData.getColumnName(i) + "                         ");
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(new String(tempRS.getString(1)) + "  |       ");
				for (int i = 2; i < tempMetaData.getColumnCount() + 1; i++) {
					System.out.print(tempRS.getString(i));
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAllUeberweisungen(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
				System.out.print(tempMetaData.getColumnName(i) + "  ");
				if (i == 3)
					System.out.print("      ");
				if (i == 4)
					System.out.print("         ");
				if (i == 5)
					System.out.print("      ");
				if (i == 6)
					System.out.print("      ");
				if (i == 7)
					System.out.print("                ");
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(tempRS.getInt(1) + "                    ");
				System.out.print(tempRS.getInt(2) + "   ");
				System.out.print(tempRS.getString(3) + "    ");
				System.out.print(tempRS.getString(4) + "    ");
				System.out.print(tempRS.getString(5) + "       ");
				System.out.print(tempRS.getDouble(6) + "        ");
				System.out.print(tempRS.getString(7) + "        ");
				System.out.print(tempRS.getString(8) + "      ");
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}