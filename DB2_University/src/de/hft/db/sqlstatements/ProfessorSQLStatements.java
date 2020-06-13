package de.hft.db.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hft.db.ConnectionHandler;
import de.hft.db.constant.DatabaseNames;

public class ProfessorSQLStatements {

	/**
	 * @return The ResultSet of the Statement: Select * from STUDENT
	 */
	public static ResultSet selectAllFromProfessor() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			return statement.executeQuery("SELECT * FROM " + DatabaseNames.TABLE_PROFESSOR_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void insertProfessor(int profNo, String firstName, String lastName) {
		String insertInto = "INSERT INTO " + DatabaseNames.TABLE_PROFESSOR_NAME+  //
				"("+DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY + "," //
				+DatabaseNames.TABLE_PROFESSOR_COLUMN_FIRST_NAME + "," //
				+ DatabaseNames.TABLE_PROFESSOR_COLUMN_LAST_NAME + ") " //
				+ "VALUES(?,?,?)";
		
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(insertInto)) {
			statement.setInt(1, profNo);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet getAllSubjectsFromProfessor(int courseNo) {
		String getAllStudentFromCourse = "SELECT " + DatabaseNames.TABLE_PROFESSOR_NAME + "."
				+ DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY + "," //
				+ DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_PROFESSOR_COLUMN_FIRST_NAME + "," //
				+ DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_PROFESSOR_COLUMN_LAST_NAME + ","//
				+ "jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY + ","//
				+ "jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NAME + "," //
				+ "jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_CREDIT_POINTS  //
				+ " FROM " + DatabaseNames.TABLE_PROFESSOR_NAME //
				+ " JOIN " + DatabaseNames.TABLE_SUBJECT_NAME + " jointable ON " //
				+ DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY
				+ " = " //
				+ "jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY//
				+ " WHERE jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY + " = ? ";
				
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(getAllStudentFromCourse)) {
			statement.setInt(1, courseNo);
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static ResultSet calculateCreditPointsSum(int courseNo) {
		String getAllStudentFromCourse = "SELECT " + DatabaseNames.TABLE_PROFESSOR_NAME + "."
				+ DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY + "," //
				+ DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_PROFESSOR_COLUMN_FIRST_NAME + "," //
				+ DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_PROFESSOR_COLUMN_LAST_NAME + ","//
				+ "SUM(jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_CREDIT_POINTS +") AS CREDITPOINTSSUM"
				+ " FROM " + DatabaseNames.TABLE_PROFESSOR_NAME //
				+ " INNER JOIN " + DatabaseNames.TABLE_SUBJECT_NAME + " jointable ON " //
				+ DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY
				+ " = " //
				+ "jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY//
				+ " WHERE jointable." + DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY + " = ?"
				+ " GROUP BY " + DatabaseNames.TABLE_PROFESSOR_NAME + "." + DatabaseNames.TABLE_PROFESSOR_COLUMN_PROFRESSOR_NO_PRIMARY_KEY; //; //
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(getAllStudentFromCourse)) {
			statement.setInt(1, courseNo);
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
}
