package databaseUtil;

public class DatabaseConsoleAccess {

	public static boolean createKunde(String vorname, String nachname) {
		return createKunde(vorname, nachname);
	}

	public static boolean updateKunde(String vornameAlt, String nachnameAlt, String vornameNeu, String nachnameNeu) {
		return updateKunde(vornameAlt, nachnameAlt, vornameNeu, nachnameNeu);
	}

	public static boolean deleteKunde(String vorname, String nachname) {
		return deleteKunde(vorname, nachname);
	}

	public static int getKundenNummer(String vorname, String nachname) {
		return getKundenNummer(vorname, nachname);
	}

	public static boolean createOnlineAccount(String id, String password, int kundennummer) {
		return createOnlineAccount(id, password, kundennummer);
	}

	public static boolean deleteOnlineAccount(String id, String password, int kundennummer) {
		return deleteOnlineAccount(id, password, kundennummer);
	}
}