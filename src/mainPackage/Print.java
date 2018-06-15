package mainPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Print {

	public static void printAllKunden(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
				System.out.print(tempMetaData.getColumnName(i) + "  ");
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(tempRS.getInt(1) + "      ");
				for (int i = 2; i < tempMetaData.getColumnCount() + 1; i++) {
					byte[] tempByteArray = tempRS.getBytes(i);
					char[] key = { 'a' };
					char[] tempCharArray = EncryptUtil.decrypt(tempByteArray, key);
					String temp = new String(tempCharArray);
					System.out.print(temp + "      ");
				}
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
				System.out.print(tempMetaData.getColumnName(i) + "  ");
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(tempRS.getInt(1) + "      ");
				for (int i = 2; i < tempMetaData.getColumnCount() + 1; i++) {
					byte[] tempByteArray = tempRS.getBytes(i);
					char[] key = { 'a' };
					char[] tempCharArray = EncryptUtil.decrypt(tempByteArray, key);
					String temp = new String(tempCharArray);
					System.out.print(temp + "      ");
				}
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
				System.out.print(tempMetaData.getColumnName(i) + "  ");
			}
			char[] key = { 'a' };
			System.out.println();
			while (tempRS.next()) {
				System.out.print(new String(tempRS.getString(1)) + "      ");
				for (int i = 2; i < tempMetaData.getColumnCount() + 1; i++) {
					byte[] tempByteArray = tempRS.getBytes(i);
					char[] tempCharArray = EncryptUtil.decrypt(tempByteArray, key);
					String temp = new String(tempCharArray);
					System.out.print(temp + "      ");
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
			}
			System.out.println();
			while (tempRS.next()) {
				System.out.print(tempRS.getInt(1) + "      ");
				System.out.print(tempRS.getInt(2) + "      ");
				for (int i = 3; i < tempMetaData.getColumnCount() + 1; i++) {
					byte[] tempByteArray = tempRS.getBytes(i);
					char[] key = { 'a' };
					char[] tempCharArray = EncryptUtil.decrypt(tempByteArray, key);
					String temp = new String(tempCharArray);
					System.out.print(temp + "      ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}