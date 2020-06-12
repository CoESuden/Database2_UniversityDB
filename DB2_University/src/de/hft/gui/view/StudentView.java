package de.hft.gui.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.hft.db.sqlstatements.StudentSQLStatements;

public class StudentView {

	private static Group _group;
	private static Table _table;
	private static Composite _leftComposite;
	private static Composite _rightComposite;

	private static Button _insertIntoButton;
	
	public static Group createStudentView(TabFolder folder) {
		_group = new Group(folder, SWT.NONE);

		_group.setLayout(new GridLayout(3,true));

		_leftComposite = new Composite(_group, SWT.BORDER);

		GridData dataComposite = new GridData(GridData.FILL, SWT.FILL, false, true);
		dataComposite.horizontalSpan = 1;
		_leftComposite.setLayoutData(dataComposite);

		_rightComposite = new Composite(_group, SWT.NONE);
		GridData dataComposite2 = new GridData(GridData.FILL, SWT.FILL, true, true);
		dataComposite2.horizontalSpan = 2;
		_rightComposite.setLayoutData(dataComposite2);

		_table = new Table(_rightComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);
		_table.setVisible(true);
		 insertAllSQLDataIntoTableData();
		_table.pack();
		return _group;
	}
	
	private static void insertAllSQLDataIntoTableData() {
		
		try {
			ResultSet rs = StudentSQLStatements.selectAllFromStudent();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			for(int i = 1; i <=rsmd.getColumnCount(); i++) {
				TableColumn column = new TableColumn(_table, SWT.NONE);
				column.setText(rsmd.getColumnLabel(i));
			}
			
			while(rs.next()) {
				TableItem item = new TableItem(_table, SWT.NONE);
				item.setText(0,rs.getInt(1) + "");
				item.setText(1,rs.getString(2) + "");
				item.setText(2,rs.getString(3) + "");
				item.setText(3,rs.getInt(4) + "");
				item.setText(4,rs.getDouble(5) + "");
			}
			rs.close();
			
			 for (int i = 0; i <rsmd.getColumnCount(); i++) {
			      _table.getColumn(i).pack();
			    }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
