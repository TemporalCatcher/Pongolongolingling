package game.main;

import game.scene.*;
import javax.swing.JPanel;

public class Main {
	
	public static final boolean DEBUG = false;
	
	public static void main(String[] args) {
		if(DEBUG)
			new Debug();
		else
			new Scene("Pong");
		
		System.exit(0);
	}
}
