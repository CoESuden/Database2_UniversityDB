package de.hft.db.sqlstatements;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hft.db.ConnectionHandler;
import de.hft.db.constant.DatabaseNames;

public class CourseSQLStatements {

	/**
	 * @return The ResultSet of the Statement: Select * from STUDENT
	 */
	public static ResultSet selectAllFromCourses() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			return statement.executeQuery("SELECT * FROM " + DatabaseNames.TABLE_COURSE_NAME);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void insertCourse(int matrNo, String courseName) {
		String insertInto = "INSERT INTO " + DatabaseNames.TABLE_COURSE_NAME+  //
				"("+DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NO_PRIMARY_KEY + "," //
				+DatabaseNames.TABLE_COURSE_COLUMN_COURSE_NAME + ") " //
				+ "VALUES(?,?)";
		try (PreparedStatement statement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(insertInto)) {
			statement.setInt(1, matrNo);
			statement.setString(2, courseName);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
