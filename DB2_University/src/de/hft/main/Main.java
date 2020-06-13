package de.hft.main;

import de.hft.db.ConnectionHandler;
import de.hft.db.DatabaseSchema;
import de.hft.gui.MainWindow;

public class Main {

	public static void main(String[] args) {
	 DatabaseSchema.runDBSchema();
//		SQLStatementsUtil.selectAllFromStudent();
//		ConnectionHandler.getInstance().closeConnection();
		
		MainWindow.getInstance().run();
		
		
		
	}
}
