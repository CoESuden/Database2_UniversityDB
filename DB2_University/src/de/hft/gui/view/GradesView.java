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

import de.hft.db.sqlstatements.GradesSQLStatements;
import de.hft.db.sqlstatements.StudentSQLStatements;
import de.hft.db.sqlstatements.SubjectSQLStatements;

public class GradesView {

	private static Group _group;
	private static Table _table;
	private static Composite _leftComposite;
	private static Composite _rightComposite;

	private static Composite _insertComposite;
	private static Label _matrNoLabel;
	public static Combo _matrNoLCombo;
	private static Label _subjectNoLabel;
	public static Combo _subjectNoCombo;
	private static Label _gradeLabel;
	private static Text _gradeText;

	private static Button _insertIntoButton;

	public static Group createGradesView(TabFolder folder) {
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
		_insertIntoButton.setText("Insert new Grade");
		GridData dataInsert = new GridData(GridData.CENTER, GridData.CENTER, true, false);
		dataInsert.horizontalSpan = 2;
		_insertIntoButton.setLayoutData(dataInsert);

		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		_gradeLabel = new Label(_insertComposite, SWT.NONE);
		_gradeLabel.setText("Grade:");
		_gradeText = new Text(_insertComposite, SWT.BORDER);
		_gradeText.setLayoutData(textData);

		_matrNoLabel = new Label(_insertComposite, SWT.NONE);
		_matrNoLabel.setText("Matr. Number:");
		_matrNoLCombo = new Combo(_insertComposite, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_matrNoLCombo.setLayoutData(textData);

		_subjectNoLabel = new Label(_insertComposite, SWT.NONE);
		_subjectNoLabel.setText("Subject Number:");
		_subjectNoCombo = new Combo(_insertComposite, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_subjectNoCombo.setLayoutData(textData);

	
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
			ResultSet rsGrades = GradesSQLStatements.selectAllFromGrades();
			ResultSet rsStudent = StudentSQLStatements.selectAllFromStudent();
			ResultSet rsSubject = SubjectSQLStatements.selectAllFromSubject();
			ResultSetMetaData rsmd = rsGrades.getMetaData();

			_table.removeAll();
			_matrNoLCombo.removeAll();
			_subjectNoCombo.removeAll();
			
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				TableColumn column = new TableColumn(_table, SWT.NONE);
				column.setText(rsmd.getColumnLabel(i));
			}

			while (rsGrades.next()) {
				TableItem item = new TableItem(_table, SWT.NONE);
				item.setText(0, rsGrades.getInt(1) + "");
				item.setText(1, rsGrades.getInt(2) + "");
				item.setText(2, rsGrades.getDouble(3) + "");
			}

			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				_table.getColumn(i).pack();
			}

			while (rsStudent.next()) {
				_matrNoLCombo.add((rsStudent.getInt(1) + "," + rsStudent.getString(2) + "," + rsStudent.getString(3)).replace("  ", ""));
			}
			while (rsSubject.next()) {
				_subjectNoCombo.add((rsSubject.getInt(1) + "," + rsSubject.getString(2)).replace("  ", ""));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private static void setListener() {
		_insertIntoButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				int matrNo = Integer.parseInt(_matrNoLCombo.getItem(_matrNoLCombo.getSelectionIndex()).split(",")[0]);
				int subjectNo = Integer.parseInt(_subjectNoCombo.getItem(_subjectNoCombo.getSelectionIndex()).split(",")[0]);
				double grade = Double.parseDouble(_gradeText.getText());
				GradesSQLStatements.insertGrades(matrNo, subjectNo, grade);
				insertAllSQLDataIntoTableData();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int matrNo = Integer.parseInt(_matrNoLCombo.getItem(_matrNoLCombo.getSelectionIndex()).split(",")[0]);
				int subjectNo = Integer.parseInt(_subjectNoCombo.getItem(_subjectNoCombo.getSelectionIndex()).split(",")[0]);
				double grade = Double.parseDouble(_gradeText.getText());
				GradesSQLStatements.insertGrades(matrNo, subjectNo, grade);
				insertAllSQLDataIntoTableData();
			}
		});
		
	}
}
