package de.hft.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class MainWindow {

	private static final MainWindow MAIN_WINDOW = new MainWindow();
	
	private Display _display = new Display();
	private Shell _shell = new Shell(_display);
	
	public static MainWindow getInstance() {
		return MAIN_WINDOW;
	}
	
	
	private MainWindow() {
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void run() {
		_shell.open();
		while (!_shell.isDisposed()) {
			if (!_display.readAndDispatch()) {
				_display.sleep();
			}
		}
		_display.dispose();
	}
	
}
