package mainPackage;

import java.util.Scanner;

import databaseUtil.DatabaseConsoleAccess;

public class Execute {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Alle Kunden anzeigen: aKu");
			System.out.println("Alle Konten anzeigen: aKo");
			System.out.println("Alle Ueberweisungen anzeigen: aUe");
			System.out.println("Alle OnlineBankingAccounts anzeigen: aOB");
			System.out.println("Programm beenden: exit");
			String eingabe = sc.nextLine();
			System.out.println();
			switch (eingabe) {
			case "aKu":
				selectKunden();
				break;
			case "aKo":
				selectKonten();
				break;
			case "aUe":
				selectUeberweisungen();
				break;
			case "aOB":
				selectOB();
				break;
			case "exit":
				System.exit(0);
				break;
			default:
				System.out.println("Falsche Eingabe");
				sc.close();
				break;
			}
		}
	}

	private static void selectOB() {
		Print.printAllOBAccounts(DatabaseConsoleAccess.getAllOBAccounts());
		System.out.println();
	}

	private static void selectUeberweisungen() {
		Print.printAllUeberweisungen(DatabaseConsoleAccess.getAllUeberweisungen());
		System.out.println();
	}

	private static void selectKonten() {
		Print.printAllKonten(DatabaseConsoleAccess.getAllKonten());
		System.out.println();
	}

	private static void selectKunden() {
		Print.printAllKunden(DatabaseConsoleAccess.getAllKunden());
		System.out.println("Um alle Konten eines Kunden anzuzeigen bitte >Ko< >Kundennummer> eingeben");
		System.out.println("Um alle OnlineBankingAccounts eines Kunden anzuzeigen bitte >OB< >Kundennummer> eingeben");
		Scanner sc2 = new Scanner(System.in);
		String eingabe2 = sc2.nextLine();
		System.out.println();
		String[] temp = eingabe2.split(" ");
		if (temp.length == 2 && temp[0].equals("Ko")) {
			Print.printAllKonten(DatabaseConsoleAccess.getKontenEinesKunden(Integer.parseInt(temp[1])));
			System.out.println("Um alle Ueberweisungen anzuzeigen, die ein Konto betreffen bitte Kontonummer eingeben");
			eingabe2 = sc2.nextLine();
			System.out.println();
			Print.printAllUeberweisungen(
					DatabaseConsoleAccess.getUeberweisungenEinesKontos(Integer.parseInt(eingabe2)));
			System.exit(0);
		}
		if (temp.length == 2 && temp[0].equals("OB")) {
			Print.printAllOBAccounts(DatabaseConsoleAccess.getKundeOBAccount(Integer.parseInt(temp[1])));
			System.exit(0);
		}
		if (temp.length == 1) {
			System.exit(0);
		}
		sc2.close();
		System.out.println();
	}
}