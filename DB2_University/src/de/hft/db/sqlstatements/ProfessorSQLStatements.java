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
	
}