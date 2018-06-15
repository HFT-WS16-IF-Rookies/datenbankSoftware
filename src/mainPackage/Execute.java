package mainPackage;

import java.util.Scanner;

import databaseUtil.DatabaseConsoleAccess;

public class Execute {

	static int FocusedKnr = 0;

	public static void main(String[] args) {
		// DatabaseConsoleAccess.createUeberweisung("Max", "Felix", "Einzel", 500,
		// "Test", 1651833802);
		/*
		 * Print.printAllKonten(DatabaseConsoleAccess.getAllKonten());
		 * System.out.println();
		 * Print.printAllOBAccounts(DatabaseConsoleAccess.getAllOBAccounts());
		 * System.out.println();
		 * Print.printAllUeberweisungen(DatabaseConsoleAccess.getAllUeberweisungen());
		 */
		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < 100; i++) {
			System.out.println();
		}
		while (true) {
			System.out.println("Bankdatenbank:");
			System.out.println(">Custom<");
			System.out.println(">Kunden< um alle Kunden anzuzeigen");
			String temp = scanner.nextLine();
			if (temp.equals("Kunden")) {
				Print.printAllKunden(DatabaseConsoleAccess.getAllKunden());
				System.out.println("Um alle Konten eines Kunden anzuzeigen geben sie die Kundennummer ein");
				temp = scanner.nextLine();
				FocusedKnr = Integer.parseInt(temp);
				Print.printAllKonten(DatabaseConsoleAccess.getKontenEinesKunden(Integer.parseInt(temp)));
				System.out.println();
				System.out.println("Um einen OnlineBanking-Account für diesen Kunden zu erstellen bitte >OB< eingeben");
				System.out.println("Um eine Überweisung zu tätigen bitte >UE< eingeben");
				temp = scanner.nextLine();
				if (temp.equals("OB")) {
					System.out.println("Geben Sie bitte eine personalisierte ID ein");
					String id = scanner.nextLine();
					System.out.println("Geben Sie bitte ein Passwort ein");
					String pw = scanner.nextLine();
					DatabaseConsoleAccess.createOnlineAccount(id, pw, FocusedKnr);
					System.out.println("Account erstellt!");
					System.out.println();
					Print.printAllOBAccounts(DatabaseConsoleAccess.getKundeOBAccount(FocusedKnr));
				} else if (temp.equals("UE")) {
					System.out.println("Geben Sie bitte den Überweisungssender ein");
					String sender = scanner.nextLine();
					System.out.println("Geben Sie bitte den Empfänger ein");
					String empfaenger = scanner.nextLine();
					System.out.println("Geben Sie bitte die Art der Überweisung ein");
					String art = scanner.nextLine();
					System.out.println("Geben Sie bitte den Zweck ein");
					String zweck = scanner.nextLine();
					System.out.println("Geben Sie bitte den Betrag ein");
					String betrag = scanner.nextLine();
					DatabaseConsoleAccess.createUeberweisung(sender, empfaenger, art, Double.parseDouble(betrag), zweck,
							FocusedKnr);
				}
			}
		}
	}
}