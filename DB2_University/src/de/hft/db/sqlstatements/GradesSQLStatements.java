package de.hft.db.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hft.db.ConnectionHandler;
import de.hft.db.constant.DatabaseNames;

public class GradesSQLStatements {

	/**
	 * @return The ResultSet of the Statement: Select * from STUDENT
	 */
	public static ResultSet selectAllFromGrades() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			return statement.executeQuery("SELECT * FROM " + DatabaseNames.TABLE_GRADES_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void insertGrades(int matrNo, int subjectNo, double grade) {
		String insertInto = "INSERT INTO " + DatabaseNames.TABLE_GRADES_NAME+  //
				"("+DatabaseNames.TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY + "," //
				+DatabaseNames.TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY + "," //
				+ DatabaseNames.TABLE_GRADES_COLUMN_GRADE + ") " //
				+ "VALUES(?,?,?)";
		
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(insertInto)) {
			statement.setInt(1, matrNo);
			statement.setInt(2, subjectNo);
			statement.setDouble(3, grade);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void updateGrades(int matrNo, int subjectNo, double grade) {
		String update = "UPDATE " + DatabaseNames.TABLE_GRADES_NAME  //
				 + " SET " + DatabaseNames.TABLE_GRADES_COLUMN_GRADE + " = " + "?"
				 + " WHERE " + DatabaseNames.TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY + " = ? "
				 + " AND " + DatabaseNames.TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY + " = ?";
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(update)) {
			statement.setDouble(1, grade);
			statement.setInt(2, matrNo);
			statement.setInt(3, subjectNo);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void deleteGrades(int matrNo, int subjectNo) {
		String update = "DELETE FROM " + DatabaseNames.TABLE_GRADES_NAME  //
				 + " WHERE " + DatabaseNames.TABLE_GRADES_COLUMN_MATRICULATION_NO_FORFORGEIN_KEY + " = ? "
				 + " AND " + DatabaseNames.TABLE_GRADES_COLUMN_SUBJECT_NO_FORGEIN_KEY + " = ?";
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(update)) {
			statement.setInt(1, matrNo);
			statement.setInt(2, subjectNo);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
