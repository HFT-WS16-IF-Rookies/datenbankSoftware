package mainPackage;

import javax.swing.table.AbstractTableModel;

public class OwnTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 4225429714872659566L;

	Object[][] values;
	String[] COLUMN_NAMES;

	public OwnTableModel(Object[][] values, String[] COLUMN_NAMES) {
		this.values = values;
		this.COLUMN_NAMES = COLUMN_NAMES;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	public int getRowCount() {
		return values.length;
	}

	public int getColumnCount() {
		return values[0].length;
	}

	public Object getValueAt(int row, int column) {
		return values[row][column];
	}

	public void updateData(Object[][] values) {
		this.values = values;
	}
}
