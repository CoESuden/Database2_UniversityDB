package de.hft.db;

import java.sql.SQLException;
import java.sql.Statement;

import de.hft.db.constant.DatabaseNames;

public class DatabaseSchema {

	private DatabaseSchema() {
		// empty, util method
	}

	public static void runDBSchema() {

		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			String createStudentTable = "CREATE TABLE " + DatabaseNames.TABLE_STUDENT_NAME + "(" //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_MATRICULATION_NO_PRIMARY_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_FIRST_NAME + " CHAR(50)," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_LAST_NAME + " CHAR(50)," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_COURSE_NO_FORGEIN_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_AVARAGE_GRADE + " DOUBLE," //
					+ "PRIMARY KEY (" + DatabaseNames.TABLE_STUDENT_COLUMN_MATRICULATION_NO_PRIMARY_KEY + ")" //
					+ ")";

			String createGradesTable = "CREATE TABLE " + DatabaseNames.TABLE_GRADES_NAME + "(" //
					+ DatabaseNames.TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_GRADES_COLUMN_GRADE + " DOUBLE," //
					+ ")";

			String createCourseTable = "CREATE TABLE " + DatabaseNames.TABLE_COURSE_NAME + "(" //
					+ DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NAME + " CHAR(100)," //
					+ "PRIMARY KEY (" + DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY + ")" //
					+ ")";

			String createSubjectTable = "CREATE TABLE " + DatabaseNames.TABLE_SUBJECT_NAME + "(" //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NAME + " CHAR(100)," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_CREDIT_POINTS + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_COURSE_NO_FOREIGN_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY + " INTEGER NOT NULL," //
					+ "PRIMARY KEY (" + DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY + ")" //
					+ ")";

			String createProfessorTable = "CREATE TABLE " + DatabaseNames.TABLE_PROFESSOR_NAME + "(" //
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY + " INTEGER NOT NULL," //
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_FIRST_NAME + " CHAR(50)," //
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_LAST_NAME + " CHAR(50)," //
					+ "PRIMARY KEY (" + DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY + ")" //
					+ ")";

			statement.execute(createStudentTable);
			statement.execute(createGradesTable);
			statement.execute(createCourseTable);
			statement.execute(createSubjectTable);
			statement.execute(createProfessorTable);

			String addForeignKeyToStudent = "ALTER TABLE " + DatabaseNames.TABLE_STUDENT_NAME //
					+ " ADD FOREIGN KEY(" + DatabaseNames.TABLE_STUDENT_COLUMN_COURSE_NO_FORGEIN_KEY + ")" //
					+ " REFERENCES " + DatabaseNames.TABLE_COURSE_NAME + " ("
					+ DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY //
					+ ")";

			String addForeignKeyMatriculationToGrades = "ALTER TABLE " + DatabaseNames.TABLE_GRADES_NAME //
					+ " ADD FOREIGN KEY(" + DatabaseNames.TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY + ")" //
					+ " REFERENCES " + DatabaseNames.TABLE_STUDENT_NAME + " ("
					+ DatabaseNames.TABLE_STUDENT_COLUMN_MATRICULATION_NO_PRIMARY_KEY //
					+ ") ";

			String addForeignKeySubjectNoToGrades = "ALTER TABLE " + DatabaseNames.TABLE_GRADES_NAME //
					+ " ADD FOREIGN KEY(" + DatabaseNames.TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY + ")" //
					+ " REFERENCES " + DatabaseNames.TABLE_SUBJECT_NAME + " ("
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY //
					+ ") ";

			String addForeignKeyCourseNoToSubject = "ALTER TABLE " + DatabaseNames.TABLE_SUBJECT_NAME //
					+ " ADD FOREIGN KEY(" + DatabaseNames.TABLE_SUBJECT_COLUMN_COURSE_NO_FOREIGN_KEY + ")" //
					+ " REFERENCES " + DatabaseNames.TABLE_COURSE_NAME + " ("
					+ DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY//
					+ ") ";

			String addForeignKeyProfessorNoToSubject = "ALTER TABLE " + DatabaseNames.TABLE_SUBJECT_NAME //
					+ " ADD FOREIGN KEY(" + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY + ")" //
					+ " REFERENCES " + DatabaseNames.TABLE_PROFESSOR_NAME + " ("
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY //
					+ ") ";

			statement.execute(addForeignKeyToStudent);
			statement.execute(addForeignKeyMatriculationToGrades);
			statement.execute(addForeignKeySubjectNoToGrades);
			statement.execute(addForeignKeyCourseNoToSubject);
			statement.execute(addForeignKeyProfessorNoToSubject);
			
			String insertCourseTestData = "INSERT INTO " + DatabaseNames.TABLE_COURSE_NAME + "(" 
					+ DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY + "," //
					+ DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NAME //
					+ ") VALUES (" //
					+ "(1,'Master Software Technology')," //
					+ "(2,'Master Math')," //
					+ "(3,'Bachelor Arts'))"; //
			
			String insertProfessorTestData = "INSERT INTO " + DatabaseNames.TABLE_PROFESSOR_NAME + "("
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY + ","//
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_FIRST_NAME  + ","//
					+ DatabaseNames.TABLE_PROFESSOR_COLUMN_LAST_NAME //
					+ ") VALUES (" //
					+ "(100,'Robert','Langdon')," //
					+ "(200,'Hari','Seldon')," //
					+ "(300,'Bruce','Wayne'))"; //
			
			String insertIntoSubjectTestData =  "INSERT INTO " + DatabaseNames.TABLE_SUBJECT_NAME + "("
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY + "," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NAME + "," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_CREDIT_POINTS + "," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_COURSE_NO_FOREIGN_KEY + "," //
					+ DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY //
					+ ") VALUES (" //
					+ "(10,'Software Project Management',10,1,100)," //
					+ "(20,'Database',6,1,100)," //
					+ "(30,'Software Engineering',8,1,100)," //
					+ "(40,'Algebra',12,2,200)," //
					+ "(50,'Combinatorics',4,2,200)," //
					+ "(60,'Probability and Statistics',7,2,200)," //
					+ "(70,'Sport',5,3,300)," //
					+ "(80,'Medicine',10,3,300)," //
					+ "(90,'Biology',7,3,300))"; //
			
			String insertIntoStudentTestData = "INSERT INTO " + DatabaseNames.TABLE_STUDENT_NAME + "("
					+ DatabaseNames.TABLE_STUDENT_COLUMN_MATRICULATION_NO_PRIMARY_KEY + "," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_FIRST_NAME + "," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_LAST_NAME + "," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_COURSE_NO_FORGEIN_KEY + "," //
					+ DatabaseNames.TABLE_STUDENT_COLUMN_AVARAGE_GRADE //
					+ ") VALUES (" //
					+ "(11111, 'Thanh Luong','Giang',1,4.0)," //
					+ "(22222, 'Clark','Kent',1,2.7)," //
					+ "(33333, 'Diana','Prince',2,2.0)," //
					+ "(44444, 'Tim','Drake',2,1.0)," //
					+ "(55555, 'Babara','Gordon',3,3.3)," //
					+ "(66666, 'Pamela Lillian','Isley',3,1.7))"; //
			
			String insertIntoGradesTestData = "INSERT INTO " + DatabaseNames.TABLE_GRADES_NAME + "("
					+ DatabaseNames.TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY + "," //
					+ DatabaseNames.TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY + "," //
					+ DatabaseNames.TABLE_GRADES_COLUMN_GRADE //
					+ ") VALUES (" //
					+ "(11111,'10',4.0)," //
					+ "(11111,'20',4.0)," //
					+ "(22222,'10',2.7)," //
					+ "(22222,'20',2.7)," //
					+ "(33333,'40',2.0)," //
					+ "(33333,'50',2.0)," //
					+ "(44444,'40',1.0)," //
					+ "(44444,'50',1.0)," //
					+ "(55555,'70',3.3)," //
					+ "(55555,'80',3.3)," //
					+ "(66666,'70',1.7)," //
					+ "(66666,'80',1.7))"; //
			statement.execute(insertCourseTestData);
			statement.execute(insertProfessorTestData);
			statement.execute(insertIntoSubjectTestData);
			statement.execute(insertIntoStudentTestData);
			statement.execute(insertIntoGradesTestData);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionHandler.getInstance().commit();

	}

}
