package de.hft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Thanh Luong Giang
 * @ConnectionHandler Creates an embedded database or connects to one which is
 *                    located in "yourWorkspace/database/" when getInstance is
 *                    called.
 */
public class ConnectionHandler {

	private static final ConnectionHandler CONNECTION_HANDLER = new ConnectionHandler();

	private Connection _connection;

	private ConnectionHandler() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver" );
			_connection = DriverManager.getConnection("jdbc:hsqldb:file:/" + System.getProperty("user.dir")
					+ "/database/HSQLDB;shutdown=true", "SA", "");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return The instance of ConnectionHandler
	 * @see ConnectionHandler
	 */
	public static ConnectionHandler getInstance() {
		return CONNECTION_HANDLER;
	}

	/**
	 * @return The main connection which was used to connect or to create the
	 *         embedded database. If needed further connection can be created via
	 *         createAnotherConnection
	 */
	public Connection getCurrerntConnection() {
		return _connection;
	}

	/**
	 * Creates a further connection if needed or parallel work is intended. This
	 * method uses try-with-resources and therefore closes itself. Commits etc. must
	 * be manually called with this connection!
	 * 
	 * @return A new created connection
	 */
	public Connection createAnotherConnection() {
		try (Connection connection = DriverManager.getConnection(
				"jdbc:hsqldb:file:/" + System.getProperty("user.dir") + "/database/HSQLDB;shutdown=true;ifexists=true",
				"SA", "")) {
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Commits all transaction which were done with the first connection. It does
	 * not commit transaction which were done by other connections that were created
	 * with createANotherConnection
	 */
	public void commit() {
		try {
			_connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes the first connection
	 */
	public void closeConnection() {
		try {
			_connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
