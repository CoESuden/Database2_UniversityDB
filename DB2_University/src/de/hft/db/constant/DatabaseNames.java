package de.hft.db.constant;

public enum DatabaseNames {

	
	/**
	 * Table STUDENT columns names
	 */
	TABLE_STUDENT_NAME(" STUDENT "), //
	TABLE_STUDENT_COLUMN_MATRICULATION_NO_PRIMARY_KEY("MatriculationNo"),
	TABLE_STUDENT_COLUMN_COURSE_NO_FORGEIN_KEY("CourseNo"),
	TABLE_STUDENT_COLUMN_FIRST_NAME("FirstName"),
	TABLE_STUDENT_COLUMN_LAST_NAME("LastName"),
	TABLE_STUDENT_COLUMN_AVARAGE_GRADE("AvarageGrade"),
	
	/**
	 * Table GRADES columns names
	 */
	TABLE_GRADES_NAME(" GRADES "),
	TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY("SubjectNo"),
	TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY("MatriculationNo"),
	TABLE_GRADES_COLUMN_GRADE("Grade"),
	
	/**
	 * Table COURSE columns names
	 */
	TABLE_COURSE_NAME(" COURSE "),
	TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY("CourseNo"),
	TABLE_COURSE_COLUMN_COURSE_NAME("CourseName"),
	
	/**
	 * Table SUBJECT columns names
	 */
	TABLE_SUBJECT_NAME(" SUBJECT "),
	TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY("SubjectNo"),
	TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY("ProfessorNo"),
	TABLE_SUBJECT_COLUMN_COURSE_NO_FOREIGN_KEY("CourseNo"),
	TABLE_SUBJECT_COLUMN_SUBJECT_NAME("SubjectName"),
	TABLE_SUBJECT_COLUMN_CREDIT_POINTS("CreditPoints"),
	
	
	/**
	 * Table PROFESSOR columns names
	 */
	TABLE_PROFESSOR_NAME(" PROFESSOR "),
	TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY("ProfessorNo"),
	TABLE_PROFESSOR_COLUMN_FIRST_NAME("FirstName"),
	TABLE_PROFESSOR_COLUMN_LAST_NAME("LastName");
	
	String _string = "";

	DatabaseNames(String string) {
		_string = string;
	}

	@Override
	public String toString() {
		return _string;
	}

}
