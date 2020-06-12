package de.hft.db.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hft.db.ConnectionHandler;
import de.hft.db.constant.DatabaseNames;

public class StudentSQLStatements {
	
	/**
	 * @return The ResultSet of the Statement: Select * from STUDENT
	 */
	public static ResultSet selectAllFromStudent() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			return statement.executeQuery("SELECT * FROM " + DatabaseNames.TABLE_STUDENT_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void insertStudent(int matrNo, String firstName, String lastName, int courseNo, double avgGrade) {
		String insertInto = "INSERT INTO " + DatabaseNames.TABLE_STUDENT_NAME +  //
				"("+DatabaseNames.TABLE_STUDENT_COLUMN_MATRICULATION_NO_PRIMARY_KEY + "," //
				+DatabaseNames.TABLE_STUDENT_COLUMN_FIRST_NAME + "," //
				+ DatabaseNames.TABLE_STUDENT_COLUMN_LAST_NAME + "," //
				+ DatabaseNames.TABLE_STUDENT_COLUMN_COURSE_NO_FORGEIN_KEY + "," //
				+ DatabaseNames.TABLE_STUDENT_COLUMN_AVARAGE_GRADE + ") " //
				+ "VALUES(?,?,?,?,?)";
		
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(insertInto)) {
			statement.setInt(1, matrNo);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setInt(4, courseNo);
			statement.setDouble(5, avgGrade);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
