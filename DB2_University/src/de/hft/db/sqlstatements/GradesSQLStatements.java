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
	
}
