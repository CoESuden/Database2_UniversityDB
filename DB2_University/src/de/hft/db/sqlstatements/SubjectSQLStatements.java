package de.hft.db.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hft.db.ConnectionHandler;
import de.hft.db.constant.DatabaseNames;

public class SubjectSQLStatements {

	/**
	 * @return The ResultSet of the Statement: Select * from STUDENT
	 */
	public static ResultSet selectAllFromSubject() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			return statement.executeQuery("SELECT * FROM " + DatabaseNames.TABLE_SUBJECT_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void insertSubject(int subjectNo, String subjectName, int creditPoints, int courseNo, int professorNo) {
		String insertInto = "INSERT INTO " + DatabaseNames.TABLE_SUBJECT_NAME +  //
				"("+DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NO_PRIMARY_KEY + "," //
				+DatabaseNames.TABLE_SUBJECT_COLUMN_SUBJECT_NAME + "," //
				+ DatabaseNames.TABLE_SUBJECT_COLUMN_CREDIT_POINTS + "," //
				+ DatabaseNames.TABLE_SUBJECT_COLUMN_COURSE_NO_FOREIGN_KEY + "," //
				+ DatabaseNames.TABLE_SUBJECT_COLUMN_PROFESSOR_NO_FOREIGN_KEY + ") " //
				+ "VALUES(?,?,?,?,?)";
		
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(insertInto)) {
			statement.setInt(1, subjectNo);
			statement.setString(2, subjectName);
			statement.setInt(3, creditPoints);
			statement.setInt(4, courseNo);
			statement.setInt(5, professorNo);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
