package mainPackage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1085815654054173311L;
	private JPanel contentPane;
	private JTable table1;
	private JTable table2;

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://62.141.46.196/hft?useLegacyDatetimeCode=false&serverTimezone=UTC";
	static String username = "hft";
	static String password = "hft100hft";

	static Connection c;
	static Statement s;

	static {
		try {
			c = DriverManager.getConnection(url, username, password);
			s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainGUI() {
		setTitle("Bankdatenbank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] columnNames1 = { "ID", "Kundennummer", "Nachname", "Vorname", "Strasse", "Hausnummer", "PLZ", "Ort" };
		Object[][] data1 = new Object[rowCount("Kunden")][8];

		int i = 0;

		ResultSet rs;
		ResultSetMetaData rsm;
		try {
			rs = s.executeQuery("SELECT * FROM Kunden");
			rsm = rs.getMetaData();

			while (rs.next()) {
				for (int j = 1; j <= rsm.getColumnCount(); j++) {
					data1[i][j - 1] = rs.getString(j);
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(29, 41, 1200, 600);
		contentPane.add(tabbedPane);

		table1 = new JTable(data1, columnNames1);
		tabbedPane.addTab("Kunden", null, table1, "Kundeninformationen");

		String[] columnNames2 = { "ID", "Kontonummer", "BLZ", "Kontoname", "Kontostand" };
		Object[][] data2 = new Object[rowCount("Kontoinformation")][5];

		i = 0;

		try {
			rs = s.executeQuery("SELECT * FROM Kontoinformation");
			rsm = rs.getMetaData();

			while (rs.next()) {
				for (int j = 1; j <= rsm.getColumnCount(); j++) {
					data2[i][j - 1] = rs.getString(j);
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		table2 = new JTable(data2, columnNames2);
		tabbedPane.addTab("Konten", null, table2, "Kontoinformation");

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 2, 119, 26);
		contentPane.add(menuBar);

		JMenu mnManu = new JMenu("Menu");
		menuBar.add(mnManu);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnManu.add(mntmExit);

		JMenu mnUpdate = new JMenu("Update");
		menuBar.add(mnUpdate);

		JMenuItem mntmUpdateTables = new JMenuItem("Update Tables");
		mnUpdate.add(mntmUpdateTables);

		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Gurpal Sing, Kevin Kammleiter", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAbout.add(mntmAbout);
	}

	protected static int rowCount(String table) {
		ResultSet r;
		int count = 0;
		try {
			r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM Kunden");
			r.next();
			count = r.getInt("rowcount");
			r.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}