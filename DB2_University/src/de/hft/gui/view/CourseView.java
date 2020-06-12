package de.hft.gui.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.hft.db.sqlstatements.CourseSQLStatements;
import de.hft.db.sqlstatements.ProfessorSQLStatements;

public class CourseView {
	
	private static Group _group;
	private static Table _table;
	private static Composite _leftComposite;
	private static Composite _rightComposite;

	private static Composite _insertComposite;
	private static Label _courseNoLabel;
	private static Text _courseNoText;
	private static Label _courseNameLabel;
	private static Text _courseNameText;

	private static Button _insertIntoButton;
	
	public static Group createCourseView (TabFolder folder) {
		_group = new Group(folder, SWT.NONE);
		_group.setLayout(new GridLayout(4,true));

		_leftComposite = new Composite(_group, SWT.BORDER);
		_leftComposite.setLayout(new GridLayout(2,true));

		GridData dataComposite = new GridData(GridData.FILL, SWT.FILL, false, true);
		dataComposite.horizontalSpan = 1;
		_leftComposite.setLayoutData(dataComposite);
		
		_insertComposite = new Composite(_leftComposite, SWT.BORDER);
		GridLayout insertLayout = new GridLayout(2, true);
		_insertComposite.setLayout(insertLayout);
		GridData insertComposite = new GridData(SWT.FILL, SWT.FILL, true, false);
		insertComposite.horizontalSpan = 2;
		_insertComposite.setLayoutData(insertComposite);
		_insertIntoButton = new Button(_insertComposite, SWT.PUSH);
		_insertIntoButton.setText("Insert new Course");
		GridData dataInsert = new GridData(GridData.CENTER, GridData.CENTER, true, false);
		dataInsert.horizontalSpan = 2;
		_insertIntoButton.setLayoutData(dataInsert);

		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		_courseNoLabel = new Label(_insertComposite, SWT.NONE);
		_courseNoLabel.setText("Course Number:");
		_courseNoText = new Text(_insertComposite, SWT.BORDER);
		_courseNoText.setLayoutData(textData);
		
		_courseNameLabel = new Label(_insertComposite, SWT.NONE);
		_courseNameLabel.setText("Course Name:");
		_courseNameText = new Text(_insertComposite, SWT.BORDER);
		_courseNameText.setLayoutData(textData);

		_rightComposite = new Composite(_group, SWT.NONE);
		_rightComposite.setLayout(new FillLayout());
		GridData dataComposite2 = new GridData(GridData.FILL, SWT.FILL, true, true);
		dataComposite2.horizontalSpan = 3;
		_rightComposite.setLayoutData(dataComposite2);

		_table = new Table(_rightComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);
		_table.setVisible(true);
		 insertAllSQLDataIntoTableData();
		_table.pack();
		
		setListener();
		return _group;
	}
	
	private static void insertAllSQLDataIntoTableData() {
		
		try {
			ResultSet rs = CourseSQLStatements.selectAllFromCourses();
			ResultSetMetaData rsmd = rs.getMetaData();
			_table.removeAll();
			for(int i = 1; i <=rsmd.getColumnCount(); i++) {
				TableColumn column = new TableColumn(_table, SWT.NONE);
				column.setText(rsmd.getColumnLabel(i));
			}
			
			while(rs.next()) {
				TableItem item = new TableItem(_table, SWT.NONE);
				item.setText(0,rs.getInt(1) + "");
				item.setText(1,rs.getString(2) + "");
			}
			rs.close();
			
			 for (int i = 0; i <rsmd.getColumnCount(); i++) {
			      _table.getColumn(i).pack();
			    }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private static void setListener() {
		_insertIntoButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				int courseNo = Integer.parseInt(_courseNoText.getText());
				CourseSQLStatements.insertCourse(courseNo, _courseNameText.getText());
				insertAllSQLDataIntoTableData();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int courseNo = Integer.parseInt(_courseNoText.getText());
				CourseSQLStatements.insertCourse(courseNo, _courseNameText.getText());
				insertAllSQLDataIntoTableData();
			}

		});
		
	}
}
