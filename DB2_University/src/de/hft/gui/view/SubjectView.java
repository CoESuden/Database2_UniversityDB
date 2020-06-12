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
import org.eclipse.swt.widgets.Combo;
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
import de.hft.db.sqlstatements.SubjectSQLStatements;

public class SubjectView {

	private static Group _group;
	private static Table _table;
	private static Composite _leftComposite;
	private static Composite _rightComposite;
	private static Composite _insertComposite;

	private static Label _subjectNoLabel;
	private static Text _subjectNoText;
	private static Label _subjectNameLabel;
	private static Text _subjectNameText;
	private static Label _creditPointsLabel;
	private static Text _creditPointsText;
	private static Label _courseNoLabel;
	public static Combo _courseNoCombo;
	private static Label _professorNoLabel;
	public static Combo _professorNoCombo;

	private static Button _insertIntoButton;

	public static Group createSubjectView(TabFolder folder) {
		_group = new Group(folder, SWT.NONE);
		_group.setLayout(new GridLayout(4, true));

		_leftComposite = new Composite(_group, SWT.BORDER);
		_leftComposite.setLayout(new GridLayout(2, true));

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
		_insertIntoButton.setText("Insert new Subject");
		GridData dataInsert = new GridData(GridData.CENTER, GridData.CENTER, true, false);
		dataInsert.horizontalSpan = 2;
		_insertIntoButton.setLayoutData(dataInsert);

		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		_subjectNoLabel = new Label(_insertComposite, SWT.NONE);
		_subjectNoLabel.setText("Subject Number:");
		_subjectNoText = new Text(_insertComposite, SWT.BORDER);
		_subjectNoText.setLayoutData(textData);

		_subjectNameLabel = new Label(_insertComposite, SWT.NONE);
		_subjectNameLabel.setText("Subject Name:");
		_subjectNameText = new Text(_insertComposite, SWT.BORDER);
		_subjectNameText.setLayoutData(textData);

		_creditPointsLabel = new Label(_insertComposite, SWT.NONE);
		_creditPointsLabel.setText("Credit Points:");
		_creditPointsText = new Text(_insertComposite, SWT.BORDER);
		_creditPointsText.setLayoutData(textData);

		_courseNoLabel = new Label(_insertComposite, SWT.NONE);
		_courseNoLabel.setText("Course:");
		_courseNoCombo = new Combo(_insertComposite, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_courseNoCombo.setLayoutData(textData);

		_professorNoLabel = new Label(_insertComposite, SWT.NONE);
		_professorNoLabel.setText("Professor:");
		_professorNoCombo = new Combo(_insertComposite, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_professorNoCombo.setLayoutData(textData);

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
			ResultSet rsSubject = SubjectSQLStatements.selectAllFromSubject();
			ResultSet rsCourse = CourseSQLStatements.selectAllFromCourses();
			ResultSet rsProf = ProfessorSQLStatements.selectAllFromProfessor();
			ResultSetMetaData rsmd = rsSubject.getMetaData();

			_table.removeAll();
			_courseNoCombo.removeAll();
			_professorNoCombo.removeAll();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				TableColumn column = new TableColumn(_table, SWT.NONE);
				column.setText(rsmd.getColumnLabel(i));
			}

			while (rsSubject.next()) {
				TableItem item = new TableItem(_table, SWT.NONE);
				item.setText(0, rsSubject.getInt(1) + "");
				item.setText(1, rsSubject.getString(2) + "");
				item.setText(2, rsSubject.getInt(3) + "");
				item.setText(3, rsSubject.getInt(4) + "");
				item.setText(4, rsSubject.getInt(5) + "");
			}
			rsSubject.close();

			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				_table.getColumn(i).pack();
			}

			while (rsCourse.next()) {
				_courseNoCombo.add((rsCourse.getInt(1) + "," + rsCourse.getString(2)).replace("  ", ""));
			}
			
			while (rsProf.next()) {
				_professorNoCombo.add((rsProf.getInt(1) + "," + rsProf.getString(2)+ "," + rsProf.getString(3)).replace("  ", ""));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void setListener() {
		_insertIntoButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				int subjectNo = Integer.parseInt(_subjectNoText.getText());
				int creditPoints = Integer.parseInt(_creditPointsText.getText());
				int courseNo = Integer
						.parseInt(_courseNoCombo.getItem(_courseNoCombo.getSelectionIndex()).split(",")[0]);
				int profNo = Integer
						.parseInt(_professorNoCombo.getItem(_professorNoCombo.getSelectionIndex()).split(",")[0]);
				SubjectSQLStatements.insertSubject(subjectNo, _subjectNameText.getText(), creditPoints, courseNo,
						profNo);
				insertAllSQLDataIntoTableData();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int subjectNo = Integer.parseInt(_subjectNoText.getText());
				int creditPoints = Integer.parseInt(_creditPointsText.getText());
				int courseNo = Integer
						.parseInt(_courseNoCombo.getItem(_courseNoCombo.getSelectionIndex()).split(",")[0]);
				int profNo = Integer
						.parseInt(_professorNoCombo.getItem(_professorNoCombo.getSelectionIndex()).split(",")[0]);
				SubjectSQLStatements.insertSubject(subjectNo, _subjectNameText.getText(), creditPoints, courseNo,
						profNo);
				insertAllSQLDataIntoTableData();
			}

		});
		
		_courseNoCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				insertAllSQLDataIntoTableData();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				insertAllSQLDataIntoTableData();
			}
		});

	}
}
