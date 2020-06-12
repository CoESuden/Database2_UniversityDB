package de.hft.db.sqlstatements;

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
			ResultSet rs = statement.executeQuery("SELECT * FROM " + DatabaseNames.TABLE_STUDENT_NAME);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
