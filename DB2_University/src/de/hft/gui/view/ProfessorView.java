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
import de.hft.db.sqlstatements.StudentSQLStatements;

public class ProfessorView {
	private static Group _group;
	private static Table _table;
	private static Composite _leftComposite;
	private static Composite _rightComposite;
	private static Button _insertIntoButton;
	
	private static Composite _insertComposite;
	private static Label _professorNoLabel;
	private static Text _professorNoText;
	private static Label _professorFirstNameLabel;
	private static Text _professorFirsNameText;
	private static Label _professorLastNameLabel;
	private static Text _professorLastNameText;
	
	private static Composite _getAllSubjectComposite;
	private static Button _getAllProfessorSubjectButton;
	private static Label _getAllSubjectLabel;
	private static Combo _getAllSubjectCombo;
	
	public static Group createProfessorView(TabFolder folder) {
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
		_insertIntoButton.setText("Insert new Professor");
		GridData dataInsert = new GridData(GridData.CENTER, GridData.CENTER, true, false);
		dataInsert.horizontalSpan = 2;
		_insertIntoButton.setLayoutData(dataInsert);

		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false);
		
		_professorNoLabel = new Label(_insertComposite, SWT.NONE);
		_professorNoLabel.setText("Professor Number:");
		_professorNoText = new Text(_insertComposite, SWT.BORDER);
		_professorNoText.setLayoutData(textData);
		
		_professorFirstNameLabel = new Label(_insertComposite, SWT.NONE);
		_professorFirstNameLabel.setText("First Name:");
		_professorFirsNameText = new Text(_insertComposite, SWT.BORDER);
		_professorFirsNameText.setLayoutData(textData);

		_professorLastNameLabel = new Label(_insertComposite, SWT.NONE);
		_professorLastNameLabel.setText("Last Name:");
		_professorLastNameText = new Text(_insertComposite, SWT.BORDER);
		_professorLastNameText.setLayoutData(textData);
		
		
		_getAllSubjectComposite = new Composite(_leftComposite, SWT.BORDER);
		_getAllSubjectComposite.setLayout(insertLayout);
		GridData getAllProfessorSubjectCompositeData = new GridData(SWT.FILL, SWT.FILL, true, false);
		getAllProfessorSubjectCompositeData.horizontalSpan = 2;
		_getAllSubjectComposite.setLayoutData(insertComposite);
		_getAllSubjectComposite.setLayoutData(getAllProfessorSubjectCompositeData);

		_getAllProfessorSubjectButton = new Button(_getAllSubjectComposite, SWT.PUSH);
		_getAllProfessorSubjectButton.setLayoutData(dataInsert);
		_getAllProfessorSubjectButton.setText("Get Subjects from");

		_getAllSubjectLabel = new Label(_getAllSubjectComposite, SWT.NONE);
		_getAllSubjectLabel.setText("Professor:");
		_getAllSubjectCombo = new Combo(_getAllSubjectComposite,
				SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		_getAllSubjectCombo.setLayoutData(textData);


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
			ResultSet rs = ProfessorSQLStatements.selectAllFromProfessor();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			_table.removeAll();
			removeHeader();
			
			if(_table.getColumnCount() == 0) {
				
				for (int i = 1; i <= 6; i++) {
					TableColumn column = new TableColumn(_table, SWT.NONE);
				}
				
				int j = 0;
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					_table.getColumn(j).setText(rsmd.getColumnLabel(i));
					j++;
				}
				
			} else {
				int j = 0;
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					_table.getColumn(j).setText(rsmd.getColumnLabel(i));
					j++;
				}
				
			}
			
			
			while(rs.next()) {
				TableItem item = new TableItem(_table, SWT.NONE);
				item.setText(0,rs.getInt(1) + "");
				item.setText(1,rs.getString(2) + "");
				item.setText(2,rs.getString(3) + "");
			}
			rs.close();
			
			refreshCourseComboBox();
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
				int profNo = Integer.parseInt(_professorNoText.getText());
				ProfessorSQLStatements.insertProfessor(profNo, _professorFirsNameText.getText(), _professorLastNameText.getText());
				insertAllSQLDataIntoTableData();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int profNo = Integer.parseInt(_professorNoText.getText());
				ProfessorSQLStatements.insertProfessor(profNo, _professorFirsNameText.getText(), _professorLastNameText.getText());
				insertAllSQLDataIntoTableData();
			}

		});
		
		_getAllProfessorSubjectButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				addJoinSubjectCourseToTable();
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addJoinSubjectCourseToTable();
			}

			private void addJoinSubjectCourseToTable() {
				_table.removeAll();
				removeHeader();
				int courseNo = Integer.parseInt(
						_getAllSubjectCombo.getItem(_getAllSubjectCombo.getSelectionIndex()).split(",")[0]);
				try (ResultSet rs = ProfessorSQLStatements.getAllSubjectsFromProfessor(courseNo)) {
				
					_table.getColumn(0).setText("PROFESSORNO");
					_table.getColumn(1).setText("FIRSTNAME");
					_table.getColumn(2).setText("LASTNAME");
					_table.getColumn(3).setText("SUBJECTNO");
					_table.getColumn(4).setText("SUBJECTNAME");
					_table.getColumn(5).setText("CREDITPOINTS");
					
					while (rs.next()) {
						TableItem item = new TableItem(_table, SWT.NONE);
						item.setText(0, rs.getInt(1) + "");
						item.setText(1, rs.getString(2) + "");
						item.setText(2, rs.getString(3) + "");
						item.setText(3, rs.getInt(4) + "");
						item.setText(4, rs.getString(5) + "");
						item.setText(5, rs.getInt(6) + "");
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
	
	public static void refreshCourseComboBox() {
		_getAllSubjectCombo.removeAll();
		try (ResultSet rsProfessor = ProfessorSQLStatements.selectAllFromProfessor();) {
			while (rsProfessor.next()) {
				_getAllSubjectCombo.add((rsProfessor.getInt(1) + "," + rsProfessor.getString(2) + "," + rsProfessor.getString(3)).replace("  ", ""));
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
