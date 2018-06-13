package mainPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Print {
	public static void printResultSet(ResultSet tempRS) {
		try {
			ResultSetMetaData tempMetaData = tempRS.getMetaData();
			for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
				System.out.print(tempMetaData.getColumnName(i) + "  ");
			}
			System.out.println();
			while (tempRS.next()) {
				for (int i = 1; i < tempMetaData.getColumnCount() + 1; i++) {
					System.out.print(tempRS.getObject(i).toString() + "      ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}