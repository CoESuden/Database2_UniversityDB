package de.hft.gui.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private static Combo _matrNoLCombo;
	private static Label _subjectNoLabel;
	private static Combo _subjectNoCombo;
	private static Label _gradeLabel;
	private static Text _gradeText;

	private static Button _insertIntoButton;

	private static Composite _updateComposite;
	private static Button _updateButton;
	private static Label _updateGradeLabel;
	private static Text _updateGradeText;
	private static Label _updateTupleLabel;
	private static Combo _updateCombo;

	private static Composite _delete;
	private static Button _deleteButton;
	private static Label _deleteLabel;
	private static Combo _deleteCombo;

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

		_updateComposite = new Composite(_leftComposite, SWT.BORDER);
		_updateComposite.setLayout(insertLayout);
		GridData updateCompositeData = new GridData(SWT.FILL, SWT.FILL, true, false);
		updateCompositeData.horizontalSpan = 2;
		_updateComposite.setLayoutData(insertComposite);
		_updateComposite.setLayoutData(updateCompositeData);

		_updateButton = new Button(_updateComposite, SWT.PUSH);
		_updateButton.setLayoutData(dataInsert);
		_updateButton.setText("Update");
		_updateGradeLabel = new Label(_updateComposite, SWT.NONE);
		_updateGradeLabel.setText("Grade:");
		_updateGradeText = new Text(_updateComposite, SWT.BORDER);
		_updateGradeText.setLayoutData(textData);

		_updateTupleLabel = new Label(_updateComposite, SWT.NONE);
		_updateTupleLabel.setText("Tuple:");
		_updateCombo = new Combo(_updateComposite, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_updateCombo.setLayoutData(textData);

		_delete = new Composite(_leftComposite, SWT.BORDER);
		GridData deleteCompositeData = new GridData(SWT.FILL, SWT.FILL, true, false);
		deleteCompositeData.horizontalSpan = 2;
		_delete.setLayout(insertLayout);
		_delete.setLayoutData(insertComposite);
		_delete.setLayoutData(deleteCompositeData);
		_deleteButton = new Button(_delete, SWT.PUSH);
		_deleteButton.setLayoutData(dataInsert);
		_deleteButton.setText("Delete");

		_deleteLabel = new Label(_delete, SWT.NONE);
		_deleteLabel.setText("Tuple:");
		_deleteCombo = new Combo(_delete, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_deleteCombo.setLayoutData(textData);

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

	public static void insertAllSQLDataIntoTableData() {
		try {
			ResultSet rsGrades = GradesSQLStatements.selectAllFromGrades();
			ResultSetMetaData rsmd = rsGrades.getMetaData();

			_table.removeAll();

			if (_table.getColumnCount() == 0) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					TableColumn column = new TableColumn(_table, SWT.NONE);
					column.setText(rsmd.getColumnLabel(i));
				}
			} else {
				int j = 0;
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					_table.getColumn(j).setText(rsmd.getColumnLabel(i));
					j++;
				}

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
			refreshGradesComboBox();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void setListener() {
		_insertIntoButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				int matrNo = Integer.parseInt(_matrNoLCombo.getItem(_matrNoLCombo.getSelectionIndex()).split(",")[0]);
				int subjectNo = Integer
						.parseInt(_subjectNoCombo.getItem(_subjectNoCombo.getSelectionIndex()).split(",")[0]);
				double grade = Double.parseDouble(_gradeText.getText());
				GradesSQLStatements.insertGrades(matrNo, subjectNo, grade);
				insertAllSQLDataIntoTableData();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int matrNo = Integer.parseInt(_matrNoLCombo.getItem(_matrNoLCombo.getSelectionIndex()).split(",")[0]);
				int subjectNo = Integer
						.parseInt(_subjectNoCombo.getItem(_subjectNoCombo.getSelectionIndex()).split(",")[0]);
				double grade = Double.parseDouble(_gradeText.getText());
				GradesSQLStatements.insertGrades(matrNo, subjectNo, grade);
				insertAllSQLDataIntoTableData();
			}
		});
		
		_updateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				updateGrade();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateGrade();
			}

			private void updateGrade() {
				int matrNo = Integer.parseInt(_updateCombo.getItem(_updateCombo.getSelectionIndex()).split(",")[0]);
				int subjectNo = Integer
						.parseInt(_updateCombo.getItem(_updateCombo.getSelectionIndex()).split(",")[3]);
				double grade = Double.parseDouble(_updateGradeText.getText());
				GradesSQLStatements.updateGrades(matrNo, subjectNo, grade);
				insertAllSQLDataIntoTableData();
			}
		});
		
		_deleteButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				deleteGrade();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				deleteGrade();
			}

			private void deleteGrade() {
				int matrNo = Integer.parseInt(_deleteCombo.getItem(_deleteCombo.getSelectionIndex()).split(",")[0]);
				int subjectNo = Integer
						.parseInt(_deleteCombo.getItem(_deleteCombo.getSelectionIndex()).split(",")[3]);
				GradesSQLStatements.deleteGrades(matrNo, subjectNo);
				insertAllSQLDataIntoTableData();
			}
		});
		
		
		

	}

	public static void refreshGradesComboBox() {
		_matrNoLCombo.removeAll();
		_subjectNoCombo.removeAll();
		_updateCombo.removeAll();
		_deleteCombo.removeAll();

		ArrayList<String> studentNames = new ArrayList<>();
		ArrayList<String> subjectNames = new ArrayList<>();

		try (ResultSet rsStudent = StudentSQLStatements.selectAllFromStudent(); //
				ResultSet rsSubject = SubjectSQLStatements.selectAllFromSubject();
				ResultSet rsGrades = GradesSQLStatements.selectAllFromGrades();) {
			while (rsStudent.next()) {
				String student = (rsStudent.getInt(1) + "," + rsStudent.getString(2) + "," + rsStudent.getString(3))
						.replace("  ", "");
				_matrNoLCombo.add(student);
				studentNames.add(student);
			}
			while (rsSubject.next()) {
				String subject = (rsSubject.getInt(1) + "," + rsSubject.getString(2)).replace("  ", "");
				_subjectNoCombo.add(subject);
				subjectNames.add(subject);
			}


			while (rsGrades.next()) {
				String tuple = "";
				int matrNo = rsGrades.getInt(1);
				int subjectNo = rsGrades.getInt(2);
				for (int i = 0; i < studentNames.size(); i++) {
					if(studentNames.get(i).split(",")[0].contains(matrNo + "")) {
						tuple += studentNames.get(i) + ",";
						break;
					}
				}
				
				for (int i = 0; i < subjectNames.size(); i++) {
					if(subjectNames.get(i).split(",")[0].contains(subjectNo + "")) {
						tuple += subjectNames.get(i);
						break;
					}
				}
				_updateCombo.add(tuple);
				_deleteCombo.add(tuple);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
