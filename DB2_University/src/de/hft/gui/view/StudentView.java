package de.hft.gui.view;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
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
import de.hft.db.sqlstatements.StudentSQLStatements;

public class StudentView {

	private static Group _group;
	private static Table _table;
	private static Composite _leftComposite;
	private static Composite _rightComposite;
	private static Composite _insertComposite;
	private static Button _insertIntoButton;
	private static Label _matrNoLabel;
	private static Text _matrNoText;
	private static Label _firstNameLabel;
	private static Text _firstNameText;
	private static Label _lastNameLabel;
	private static Text _lastNameText;
	private static Label _gradeLabel;
	private static Text _gradeText;
	private static Label _courseLabel;
	private static Combo _comboCourse;

	private static Composite _getAllCourseStudentComposite;
	private static Button _getAllCourseStudentButton;
	private static Label _courseNameGetAllStudentLabel;
	private static Combo _courseNameGetAllStudentCombo;

	public static Group createStudentView(TabFolder folder) {
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
		_insertIntoButton.setText("Insert new Student");

		GridData dataInsert = new GridData(GridData.CENTER, GridData.CENTER, true, false);
		dataInsert.horizontalSpan = 2;
		_insertIntoButton.setLayoutData(dataInsert);

		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		_matrNoLabel = new Label(_insertComposite, SWT.NONE);
		_matrNoLabel.setText("Matr. Number:");
		_matrNoText = new Text(_insertComposite, SWT.BORDER);
		_matrNoText.setLayoutData(textData);

		_firstNameLabel = new Label(_insertComposite, SWT.NONE);
		_firstNameLabel.setText("First Name:");
		_firstNameText = new Text(_insertComposite, SWT.BORDER);
		_firstNameText.setLayoutData(textData);

		_lastNameLabel = new Label(_insertComposite, SWT.NONE);
		_lastNameLabel.setText("Last Name:");
		_lastNameText = new Text(_insertComposite, SWT.BORDER);
		_lastNameText.setLayoutData(textData);
		_gradeLabel = new Label(_insertComposite, SWT.NONE);
		_gradeLabel.setText("Grade:");
		_gradeText = new Text(_insertComposite, SWT.BORDER);
		_gradeText.setLayoutData(textData);

		_courseLabel = new Label(_insertComposite, SWT.NONE);
		_courseLabel.setText("Course:");
		_comboCourse = new Combo(_insertComposite, SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_comboCourse.setLayoutData(textData);

		_getAllCourseStudentComposite = new Composite(_leftComposite, SWT.BORDER);
		_getAllCourseStudentComposite.setLayout(insertLayout);
		GridData getAllCourseStudentCompositeData = new GridData(SWT.FILL, SWT.FILL, true, false);
		getAllCourseStudentCompositeData.horizontalSpan = 2;
		_getAllCourseStudentComposite.setLayoutData(insertComposite);
		_getAllCourseStudentComposite.setLayoutData(getAllCourseStudentCompositeData);

		_getAllCourseStudentButton = new Button(_getAllCourseStudentComposite, SWT.PUSH);
		_getAllCourseStudentButton.setLayoutData(dataInsert);
		_getAllCourseStudentButton.setText("Get Students from");

		_courseNameGetAllStudentLabel = new Label(_getAllCourseStudentComposite, SWT.NONE);
		_courseNameGetAllStudentLabel.setText("Course:");
		_courseNameGetAllStudentCombo = new Combo(_getAllCourseStudentComposite,
				SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_courseNameGetAllStudentCombo.setLayoutData(textData);

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
			ResultSet rsStudent = StudentSQLStatements.selectAllFromStudent();
			ResultSetMetaData rsmdStudent = rsStudent.getMetaData();

			_table.removeAll();
			removeHeader();

			if(_table.getColumnCount() == 0) {
				for (int i = 1; i <= rsmdStudent.getColumnCount(); i++) {
					TableColumn column = new TableColumn(_table, SWT.NONE);
					column.setText(rsmdStudent.getColumnLabel(i));
				}
			} else {
				int j = 0;
				for (int i = 1; i <= rsmdStudent.getColumnCount(); i++) {
					_table.getColumn(j).setText(rsmdStudent.getColumnLabel(i));
					j++;
				}
				
			}
			

			while (rsStudent.next()) {
				TableItem item = new TableItem(_table, SWT.NONE);
				item.setText(0, rsStudent.getInt(1) + "");
				item.setText(1, rsStudent.getString(2) + "");
				item.setText(2, rsStudent.getString(3) + "");
				item.setText(3, rsStudent.getInt(4) + "");
				item.setText(4, rsStudent.getDouble(5) + "");
			}
			rsStudent.close();

			for (int i = 0; i < rsmdStudent.getColumnCount(); i++) {
				_table.getColumn(i).pack();
			}

			refreshStudentComboBox();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void setListener() {
		_insertIntoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				System.out.println("WRONG 1");
				int matrNo = Integer.parseInt(_matrNoText.getText());
				int courseNo = Integer.parseInt(_comboCourse.getItem(_comboCourse.getSelectionIndex()));
				double grade = Double.parseDouble(_gradeText.getText());
				StudentSQLStatements.insertStudent(matrNo, _firstNameText.getText(), _lastNameText.getText(), courseNo,
						grade);
				insertAllSQLDataIntoTableData();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("WRONG 2");
				int matrNo = Integer.parseInt(_matrNoText.getText());
				int courseNo = Integer.parseInt(_comboCourse.getItem(_comboCourse.getSelectionIndex()).split(",")[0]);
				double grade = Double.parseDouble(_gradeText.getText());
				StudentSQLStatements.insertStudent(matrNo, _firstNameText.getText(), _lastNameText.getText(), courseNo,
						grade);
				insertAllSQLDataIntoTableData();
			}

		});

		_getAllCourseStudentButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				addJoinStudentCourseToTable();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addJoinStudentCourseToTable();
			}

			private void addJoinStudentCourseToTable() {
				_table.removeAll();
				removeHeader();
				int courseNo = Integer.parseInt(
						_courseNameGetAllStudentCombo.getItem(_courseNameGetAllStudentCombo.getSelectionIndex()).split(",")[0]);
				try (ResultSet rs = StudentSQLStatements.getAllStudentFromCourse(courseNo)) {
				
					_table.getColumn(0).setText("MATRICULATIONNO");
					_table.getColumn(1).setText("MATRICULATIONNO");
					_table.getColumn(2).setText("MATRICULATIONNO");
					_table.getColumn(3).setText("MATRICULATIONNO");
					_table.getColumn(4).setText("MATRICULATIONNO");
					
					while (rs.next()) {
						TableItem item = new TableItem(_table, SWT.NONE);
						item.setText(0, rs.getInt(1) + "");
						item.setText(1, rs.getString(2) + "");
						item.setText(2, rs.getString(3) + "");
						item.setText(3, rs.getString(5) + "");
						item.setText(4, rs.getDouble(4) + "");
					}

					for (int i = 0; i < _table.getColumnCount(); i++) {
						_table.getColumn(i).pack();
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		});

	}

	public static void refreshStudentComboBox() {
		_comboCourse.removeAll();
		_courseNameGetAllStudentCombo.removeAll();
		try (ResultSet rsCourse = CourseSQLStatements.selectAllFromCourses();) {
			while (rsCourse.next()) {
				_comboCourse.add((rsCourse.getInt(1) + "," + rsCourse.getString(2)).replace("  ", ""));
				_courseNameGetAllStudentCombo.add((rsCourse.getInt(1) + "," + rsCourse.getString(2)).replace("  ", ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void removeHeader() {
		TableColumn[] columns = _table.getColumns();
        for (int i = 0; i < columns.length; i++) {
            columns[i].setText("");
        }
	}
}
