package mainPackage;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1085815654054173311L;
	private JPanel contentPane;
	private JPanel MainPanel;
	private static JTabbedPane tabbedPane;
	static JTable tableKunden;
	static JTable tableKonten;

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

	static String[] kundenHeader = { "Kundennummer", "Nachname", "Vorname", "Strasse", "Hausnummer", "PLZ", "Ort" };
	static Object[][] kundenData;

	static String[] konteHeader = { "ID", "Kontonummer", "BLZ", "Kontoname", "Kontostand" };
	static Object[][] kontenData;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 890, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 884, 21);
		contentPane.add(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnMenu.add(mntmExit);

		JMenu mnUpdate = new JMenu("Update");
		menuBar.add(mnUpdate);

		JMenuItem mntmUpdateTables = new JMenuItem("Update Tables");
		mntmUpdateTables.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		mnUpdate.add(mntmUpdateTables);

		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "Gurpal Singh, Kevin Kammleiter", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnAbout.add(mntmAbout);

		MainPanel = new JPanel();
		MainPanel.setBounds(0, 21, 884, 540);
		contentPane.add(MainPanel);
		MainPanel.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 884, 540);
		MainPanel.add(tabbedPane);

		updateTables();

	}

	protected static void updateTables() {	
		kundenData = getObjectData("Kunden", false);
		kontenData = getObjectData("Kontoinformation", true);

		JScrollPane scrollPaneKunden = new JScrollPane();
		tabbedPane.addTab("Kunden", null, scrollPaneKunden, null);

		JScrollPane scrollPaneKonten = new JScrollPane();
		tabbedPane.addTab("Konten", null, scrollPaneKonten, null);

		tableKunden = new JTable(kundenData, kundenHeader);
		tableKunden.setEnabled(false);
		scrollPaneKunden.setViewportView(tableKunden);

		tableKonten = new JTable(kontenData, konteHeader);
		tableKunden.setEnabled(false);
		scrollPaneKonten.setViewportView(tableKonten);
				
		tableKunden.repaint();
		tableKonten.repaint();
	}

	protected static Object[][] getObjectData(String table, boolean id) {
		Object[][] end = new Object[getRowCount(table)][getColCount(table)];

		int start = 2;

		if (id) {
			start = 1;
		}

		int i = 0;
		ResultSet rs;
		ResultSetMetaData rsm;
		try {
			rs = s.executeQuery("SELECT * FROM " + table);
			rsm = rs.getMetaData();

			while (rs.next()) {
				for (int j = start; j <= rsm.getColumnCount(); j++) {
					end[i][j - start] = rs.getString(j);
				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return end;
	}

	protected static int getRowCount(String table) {
		int count = 0;
		ResultSet r = null;
		try {
			r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM " + table);
			r.next();
			count = r.getInt("rowcount");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return count;
	}

	protected static int getColCount(String table) {
		int count = 0;
		ResultSet r = null;
		ResultSetMetaData rsm;
		try {
			r = s.executeQuery("SELECT * FROM " + table);
			rsm = r.getMetaData();
			count = rsm.getColumnCount();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return count;
	}
}
