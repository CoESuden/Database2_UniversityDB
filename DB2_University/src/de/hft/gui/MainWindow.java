package de.hft.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.hft.gui.view.CourseView;
import de.hft.gui.view.GradesView;
import de.hft.gui.view.ProfessorView;
import de.hft.gui.view.StudentView;
import de.hft.gui.view.SubjectView;

public class MainWindow {

	private static final MainWindow MAIN_WINDOW = new MainWindow();

	private Display _display = new Display();
	private Shell _shell = new Shell(_display);

	private TabFolder _tabFolder = new TabFolder(_shell, SWT.NONE);
	private TabItem _tabStudent = new TabItem(_tabFolder, SWT.NONE);
	private TabItem _tabProfessor = new TabItem(_tabFolder, SWT.NONE);
	private TabItem _tabCourse = new TabItem(_tabFolder, SWT.NONE);
	private TabItem _tabGrades = new TabItem(_tabFolder, SWT.NONE);
	private TabItem _tabSubject = new TabItem(_tabFolder, SWT.NONE);

	public static MainWindow getInstance() {
		return MAIN_WINDOW;
	}

	private MainWindow() {

		_shell.setText("Database II Pre-Exam");
		_shell.setLayout(new FillLayout());

		_tabStudent.setText("Student");
		_tabProfessor.setText("Professor");
		_tabCourse.setText("Course");
		_tabSubject.setText("Subject");
		_tabGrades.setText("Grades");

		_tabCourse.setControl(CourseView.createCourseView(_tabFolder));
		_tabStudent.setControl(StudentView.createStudentView(_tabFolder));
		_tabProfessor.setControl(ProfessorView.createProfessorView(_tabFolder));
		_tabSubject.setControl(SubjectView.createSubjectView(_tabFolder));
		_tabGrades.setControl(GradesView.createGradesView(_tabFolder));

		setListeners();
	}

	private void setListeners() {
		_tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if(event.item.toString().contains("Subject")) {
					SubjectView.refreshSubjectComboBox();
					SubjectView.insertAllSQLDataIntoTableData();
				} else if (event.item.toString().contains("Student")) {
					StudentView.refreshStudentComboBox();
					StudentView.insertAllSQLDataIntoTableData();
				} else if (event.item.toString().contains("Professor")) {
					ProfessorView.insertAllSQLDataIntoTableData();
				} else if (event.item.toString().contains("Course")) {
					CourseView.insertAllSQLDataIntoTableData();
					CourseView.refreshCourseComboBox();
				} else if (event.item.toString().contains("Grades")) {
					GradesView.refreshGradesComboBox();
					GradesView.insertAllSQLDataIntoTableData();
				}
			}
		});
	}


	public void run() {
		_shell.open();
		while (!_shell.isDisposed()) {
			if (!_display.readAndDispatch()) {
				_display.sleep();
			}
		}
		_display.dispose();
	}

}
