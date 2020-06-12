package de.hft.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Thanh Luong Giang, Furqan Iqbal, Kishan Lokappa
 * @SQLStatementsUtil Util-class which provides various SQL-Statements
 */
public class SQLStatementsUtil {

	private static final String STUDENT_TABLE_NAME = "Student";

	private static final String MATRICULATION_NUMBER = "MatriculationNo";
	private static final String FIRST_NAME = "FirstName";
	private static final String LAST_NAME = "LastName";
	private static final String STUDY_COURSE = "StudyCourse";
	private static final String AVARAGE_GRADE = "Grade";

	private static final String CREATE_TABLE = " CREATE TABLE ";
	private static final String INSERT_INTO = " INSERT INTO ";
	private static final String VALUES = " VALUES ";
	private static final String INTEGER_NOT_NULL = " INTEGER NOT NULL ";
	private static final String PRIMARY_KEY = " PRIMARY KEY ";
	private static final String OPEN_BRACKETS = " ( ";
	private static final String CLOSE_BRACKETS = " ) ";
	private static final String COMMA = " , ";

	private SQLStatementsUtil() {
		// empty util class
	}

	public static void createTestDataForStudent() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			statement.executeUpdate(INSERT_INTO + STUDENT_TABLE_NAME + OPEN_BRACKETS + MATRICULATION_NUMBER + COMMA //
					+ FIRST_NAME + COMMA //
					+ LAST_NAME + COMMA //
					+ STUDY_COURSE + COMMA //
					+ AVARAGE_GRADE + CLOSE_BRACKETS //
					+ VALUES + OPEN_BRACKETS //
					+ "(11111, 'Kishan Lokappa', 'Lokappa','Software Technology',1.0)" + COMMA
					+ "(22222, 'Furqan Iqbal', 'Iqbal','Software Technology',2.0)" + COMMA
					+ "(33333, 'Thanh Luong', 'Giang','Software Technology',3.0)" + COMMA
					+ "(44444, 'Kenan', 'Ercan','Art',2.5)" + COMMA + "(55555, 'Frank', 'Tlien','Computer Science',4.0)"
					+ CLOSE_BRACKETS);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ConnectionHandler.getInstance().commit();
	}

	/**
	 * @return The ResultSet of the Statement: Select * from STUDENT
	 */
	public static ResultSet selectAllFromStudent() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			ResultSet rs = statement.executeQuery("SELECT * FROM " + STUDENT_TABLE_NAME);
			while (rs.next()) {
				System.out.println("MatrNo: " + rs.getInt(1) + " First Name: " + rs.getString(2) + " Last Name: "
						+ rs.getString(3) + " Study Course: " + rs.getString(4) + " Avarage Grade: " + rs.getDouble(5));
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void addStudent(int matrNo, String firstName, String lastName, String studyCourse, double avarageGrade) {
		String preparedStatementQuery = INSERT_INTO + STUDENT_TABLE_NAME + OPEN_BRACKETS //
				+ MATRICULATION_NUMBER + COMMA 
				+ FIRST_NAME + COMMA //
				+ LAST_NAME + COMMA //
				+ STUDY_COURSE + COMMA //
				+ AVARAGE_GRADE + CLOSE_BRACKETS //
				+ VALUES + OPEN_BRACKETS //
				+ "?,?,?,?,?" + CLOSE_BRACKETS;
		try (PreparedStatement preparedStatement = ConnectionHandler.getInstance().getCurrerntConnection().prepareStatement(preparedStatementQuery)) {
			preparedStatement.setInt(1, matrNo);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, studyCourse);
			preparedStatement.setDouble(5, avarageGrade);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void dropTableStudent() {
		try (Statement statement = ConnectionHandler.getInstance().getCurrerntConnection().createStatement()) {
			statement.executeUpdate("DROP TABLE " + STUDENT_TABLE_NAME);
			ConnectionHandler.getInstance().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
