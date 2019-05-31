package game.scene;

import game.main.Ball;
import game.main.GameObject;
import game.main.ID;
import game.main.Paddle;
import game.main.Window;

import java.util.Random;

public class Debug extends Scene {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Debug() {
		super("Debug", 4);
		
		game = true;
		
		/*for(int i = 0; i<100; i++) 
			handler.addObject(new VisualEntity());*/
		handler.sortList();
		double angle = new Random().nextDouble()*2*Math.PI;
		angle = Math.PI/4*0;
		handler.addObject(new Ball((Window.borderRight - Window.borderLeft)/2,
				(Window.borderBottom - Window.borderTop)/2, 10,
				angle, ID.Slave));
		handler.addObject(new Paddle(Window.borderRight/2 - 50,
				(Window.borderBottom - Window.borderTop)/2, ID.LeftPlayer));
		if(players >= 2)
			handler.addObject(new Paddle(Window.borderRight/2 + 50,
					(Window.borderBottom - Window.borderTop)/2, ID.RightPlayer));
		else
			handler.addObject(new Paddle(Window.borderRight- 62,
				(Window.borderBottom - Window.borderTop)/2, ID.RightEnemy));
		
		handler.addObject(new Paddle(Window.borderRight/2,
				(Window.borderBottom - Window.borderTop)/2 - 50, true, ID.TopPlayer));
		handler.addObject(new Paddle(Window.borderRight/2,
				(Window.borderBottom - Window.borderTop)/2 + 50, true, ID.BottomPlayer));
		for(int i = 0; i < handler.getPlayers().size(); i++) {
			GameObject temp = handler.getPlayers().get(i);
			temp.setSpeed(50);
		}
		
		
		input1 = new KeyInput(myWindow.getHandler().getPlayers().get(0));
		if(players >= 2) {
			input2 = new KeyInput(myWindow.getHandler().getPlayers().get(1));
			if(players >= 3) {
				input3 = new KeyInput(myWindow.getHandler().getPlayers().get(2));
				if(players == 4)
					input4 = new KeyInput(myWindow.getHandler().getPlayers().get(3));
			}
		}
		myWindow.addKeyListener(input1);
		myWindow.addKeyListener(input2);
		myWindow.addKeyListener(input3);
		myWindow.addKeyListener(input4);
		run();
	}
}

