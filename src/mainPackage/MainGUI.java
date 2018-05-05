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
import javax.swing.JButton;
import javax.swing.JTextField;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1085815654054173311L;
	private JPanel contentPane;
	private JPanel MainPanel;
	private static JTabbedPane tabbedPane;
	static JTable tableKunden;
	static JTable tableKonten;
	static OwnTableModel OwnModelKunden;
	static OwnTableModel OwnModelKonten;

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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

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
				updateTables();
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

		startTables();

		JPanel jp = new JPanel();
		tabbedPane.addTab("Create", jp);
		jp.setLayout(null);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					s.execute("INSERT INTO Kontoinformation (kontonr, blz, kontoname, kontostand) VALUES ('"
							+ textField.getText() + "','" + textField_1.getText() + "','" + textField_2.getText()
							+ "','" + textField_3.getText() + "')");
					System.out.println("worked");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(780, 478, 89, 23);
		jp.add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(10, 11, 86, 20);
		jp.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 42, 86, 20);
		jp.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 73, 86, 20);
		jp.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(10, 104, 86, 20);
		jp.add(textField_3);
		textField_3.setColumns(10);

	}

	protected static void startTables() {

		OwnModelKunden = new OwnTableModel(getObjectData("Kunden", false), kundenHeader);
		tableKunden = new JTable(OwnModelKunden);

		JScrollPane scrollPaneKunden = new JScrollPane(tableKunden);
		tabbedPane.addTab("Kunden", null, scrollPaneKunden, null);

		OwnModelKonten = new OwnTableModel(getObjectData("Kontoinformation", true), konteHeader);
		tableKonten = new JTable(OwnModelKonten);

		JScrollPane scrollPaneKonten = new JScrollPane(tableKonten);
		tabbedPane.addTab("Kontoinformation", null, scrollPaneKonten, null);

	}

	protected static void updateTables() {
		OwnModelKunden.updateData(getObjectData("Kunden", false));
		OwnModelKonten.updateData(getObjectData("Kontoinformation", true));
	}

	protected static Object[][] getObjectData(String table, boolean id) {
		Object[][] end = new Object[getRowCount(table)][getColCount(table) - 1];

		int start = 2;

		if (id) {
			end = new Object[getRowCount(table)][getColCount(table)];
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
