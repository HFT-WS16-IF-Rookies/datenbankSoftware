package databaseUtil;

import java.sql.ResultSet;

public class DatabaseConsoleAccess {

	public static void createKunde(String vorname, String nachname) {
		DatabaseKunde.createKunde(vorname, nachname);
	}

	public static void deleteKunde(String vorname, String nachname) {
		DatabaseKunde.deleteKunde(vorname, nachname);
	}

	public static int getKundenNummer(String vorname, String nachname) {
		return DatabaseKunde.getKundenNummer(vorname, nachname);
	}

	public static void createOnlineAccount(String id, String password, int kontonummer) {
		DatabaseOnlineBanking.createOnlineAccount(id, password, kontonummer);
	}

	public static void deleteOnlineAccount(String id, String password, int kontonummer) {
		DatabaseOnlineBanking.deleteOnlineAccount(id, password, kontonummer);
	}

	public static void createUeberweisung(String sender, String empfaenger, String art, double betrag, String zweck,
			int kontonummer) {
		DatabaseUeberweisung.createUeberweisung(sender, empfaenger, art, betrag, zweck, kontonummer);
	}

	public static void setGuthaben(double betrag, int kontonummer) {
		DatabaseKonto.setGuthaben(betrag, kontonummer);
	}

	public static void createKonto(int kundennummer, String typ) {
		DatabaseKonto.createKonto(kundennummer, typ);
	}

	public static void deleteKonto(int kontonummer) {
		DatabaseKonto.deleteKonto(kontonummer);
	}

	public static ResultSet getAllKunden() {
		return DatabaseKunde.getAllKunden();
	}

	public static ResultSet getAllKonten() {
		return DatabaseKonto.getAllKonten();
	}

	public static ResultSet getAllOBAccounts() {
		return DatabaseOnlineBanking.getAllOBAccounts();
	}

	public static ResultSet getAllUeberweisungen() {
		return DatabaseUeberweisung.getAllUeberweisungen();
	}

	public static ResultSet getKontenEinesKunden(int kundennummer) {
		return DatabaseKonto.getKontenEinesKunden(kundennummer);
	}

	public static ResultSet getKundeOBAccount(int kundennummer) {
		return DatabaseOnlineBanking.getKundeOBAccount(kundennummer);
	}

	public static ResultSet getUeberweisungenEinesKontos(int kontonummer) {
		return DatabaseUeberweisung.getUeberweisungenEinesKontos(kontonummer);
	}
}