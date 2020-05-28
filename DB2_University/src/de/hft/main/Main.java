package de.hft.main;

import de.hft.db.ConnectionHandler;
import de.hft.db.SQLStatementsUtil;

public class Main {
	
	public static void main(String[] args) {
		SQLStatementsUtil.runDatabaseSchema();
		SQLStatementsUtil.createTestDataForStudent();
		SQLStatementsUtil.addStudent(99999, "TheOneWho", "GotAdded", "Something", 5.0);
		SQLStatementsUtil.selectAllFromStudent();
		SQLStatementsUtil.dropTableStudent();
		ConnectionHandler.getInstance().closeConnection();
	}
}
