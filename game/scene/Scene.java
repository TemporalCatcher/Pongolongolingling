package game.scene;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import game.main.Ball;
import game.main.GameObject;
import game.main.Handler;
import game.main.ID;
import game.main.Paddle;
import game.main.VisualEntity;
import game.main.Window;

import javax.swing.AbstractAction;

import java.util.Random;

public class Scene extends JPanel implements Runnable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1550691097823471818L;
	
	//private Thread thread;
	public static final Handler handler = new Handler();
	protected Window myWindow;
	protected static boolean isRunning = true;

	public static boolean game = false;
	static int fps = 60;
	protected static final double DELTA = 1.0/fps;
	
	protected KeyInput input1, input2, input3, input4;
	
	protected int players;
	
	private static int[] score = new int[4];
	
	public Scene(String title, int players) {
		super();
		/*this.getInputMap().put(KeyStroke.getKeyStroke('k'),"hello");
		this.getActionMap().put("hello", new Input());
		System.out.println(getActionMap().get("hello"));*/
		this.players = players;
		score = new int[players];
		
		for(int i = 0; i < players; i++)
			score[i] = 0;
		
		myWindow = new Window(title, this, handler);
		
		/*for(int i = 0; i<100; i++) 
			handler.addObject(new VisualEntity());
		
		handler.sortList();*/
		/*double angle = new Random().nextDouble()*2*Math.PI;
		handler.addObject(new Ball((Window.borderRight - 32 - Window.borderLeft)/2,
				(Window.borderBottom - 32 - Window.borderTop)/2, 10,
				angle, ID.Slave));
		handler.addObject(new Paddle(Window.borderLeft + 64,
				(Window.borderBottom - 192 - Window.borderTop)/2, ID.LeftPlayer));
		if(players >= 2)
			handler.addObject(new Paddle(Window.borderRight - 32 - 64,
					(Window.borderBottom - 192 - Window.borderTop)/2, ID.RightPlayer));
		else
			handler.addObject(new Paddle(Window.borderRight - 32 - 64,
				(Window.borderBottom - 192 - Window.borderTop)/2, ID.Enemy));
		
		input1 = new KeyInput(myWindow.getHandler().getPlayers().get(0));
		if(players >= 2)
			input2 = new KeyInput(myWindow.getHandler().getPlayers().get(1));
		myWindow.addKeyListener(input1);
		myWindow.addKeyListener(input2);
		*/
	}
	
	public Scene(String title) {
		super();
		players = 1;
		/*this.getInputMap().put(KeyStroke.getKeyStroke('k'),"hello");
		this.getActionMap().put("hello", new Input());
		System.out.println(getActionMap().get("hello"));*/
		score[0] = 0;
		score[1] = 0;
		score[2] = 0;
		score[3] = 0;
		
		myWindow = new Window(title, this, handler);
		
		for(int i = 0; i<300; i++) 
			handler.addObject(new VisualEntity());
		
		handler.sortList();
		double angle = new Random().nextDouble()*2*Math.PI;
		handler.addObject(new Ball((Window.borderRight - Window.borderLeft)/2,
				(Window.borderBottom - Window.borderTop)/2, 10,
				angle, ID.Slave));
		input1 = new KeyInput(null);
		myWindow.addKeyListener(input1);
		if(players >= 2) {
			input2 = new KeyInput(null);
			myWindow.addKeyListener(input2);
			if(players >= 3) {
				input3 = new KeyInput(null);
				myWindow.addKeyListener(input3);
			}
			if(players == 4) {
				input2 = new KeyInput(null);
				myWindow.addKeyListener(input4);
			}
		}
		run();
	}
	
	public void run() {
		while(isRunning) {
			long time = System.currentTimeMillis();
			tick();
			render();
			
			time = (1000 / fps) - (System.currentTimeMillis()- time);
			if(time > 0) {
				try{
					Thread.sleep(time);
				}
				catch(Exception e) {}
			}
		}
		if(game){
			isRunning = true;
			double angle = new Random().nextDouble()*2*Math.PI;
			handler.removeObject(handler.getSlaves().get(0));
			handler.addObject(new Ball((Window.borderRight - Window.borderLeft)/2,
					(Window.borderBottom - Window.borderTop)/2, 10,
					angle, ID.Slave));
			handler.addObject(new Paddle(Window.borderLeft + 16 + 16,
					(Window.borderBottom - Window.borderTop)/2, ID.LeftPlayer));
			if(players >= 2)
				handler.addObject(new Paddle(Window.borderRight - 16 - 16,
						(Window.borderBottom - Window.borderTop)/2, ID.RightPlayer));
			else
				handler.addObject(new Paddle(Window.borderRight - 16 - 16,
					(Window.borderBottom - Window.borderTop)/2, ID.RightEnemy));
			input1.player = handler.getPlayers().get(0);
			if(players >= 2)
				input2.player = handler.getPlayers().get(1);
			run();
		}
		myWindow.setVisible(false);
	}
	
	/*public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now; while(delta >=1) {
				tick(); delta--;
				} 
			if(isRunning)
					render();
				frames++;
				if(System.currentTimeMillis() - timer > 1000) {
					//timer += 1000; System.out.println("FPS: "+ frames);
					frames = 0;
				}
				
			} 
		if(game){
			isRunning = true;
			double angle = new Random().nextDouble()*2*Math.PI;
			handler.removeObject(handler.getSlaves().get(0));
			handler.addObject(new Ball((Window.borderRight - 32 - Window.borderLeft)/2,
					(Window.borderBottom - 32 - Window.borderTop)/2, 10,
					angle, ID.Slave));
			handler.addObject(new Paddle(Window.borderLeft + 64,
					(Window.borderBottom - 192 - Window.borderTop)/2, ID.LeftPlayer));
			if(players >= 2)
				handler.addObject(new Paddle(Window.borderRight - 32 - 64,
						(Window.borderBottom - 192 - Window.borderTop)/2, ID.RightPlayer));
			else
				handler.addObject(new Paddle(Window.borderRight - 32 - 64,
					(Window.borderBottom - 192 - Window.borderTop)/2, ID.Enemy));
			input1.player = handler.getPlayers().get(0);
			input2.player = handler.getPlayers().get(1);
			run();
		}
		myWindow.setVisible(false);
	}*/
	
	private void tick() {
		if(input1.player != null)
			tickControl();
		handler.tick();
		
	}
	
	public void tickControl() {
		if(!input1.moveUp && !input1.moveDown)
			input1.player.stop();
		else if(input1.moveUp && input1.switching) {
			input1.player.move(1);
		}
		else if(input1.moveDown && !input1.switching)
			input1.player.move(2);
		if(players >= 2){
			if(!input2.moveUp && !input2.moveDown)
				input2.player.stop();
			else if(input2.moveUp && input2.switching) {
				input2.player.move(1);
			}
			else if(input2.moveDown && !input2.switching)
				input2.player.move(2);
		
			if(players>=3) {
				if(!input3.moveUp && !input3.moveDown)
					input3.player.stop();
				else if(input3.moveUp && input3.switching) {
				input3.player.move(3);
				}
				else if(input3.moveDown && !input3.switching)
					input3.player.move(4);
				
				if(players == 4) {
					if(!input4.moveUp && !input4.moveDown)
						input4.player.stop();
					else if(input4.moveUp && input4.switching) {
						input4.player.move(3);
					}
					else if(input4.moveDown && !input4.switching)
						input4.player.move(4);
					}
			}
		}
	}
	
	private void render() {
		myWindow.render();
	}
	
	public static int[] getScore() {
		return score;
	}
	
	public static void incrementScore(int index) {
		score[index]++;
	}
	
	/*private class Input extends AbstractAction {

		s
		private static final long serialVersionUID = 1L;
		
		public Input() {
			super();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			}
		}*/
	
	public class KeyInput extends KeyAdapter{
		
		boolean moveUp = false, moveDown = false, switching = false;
		GameObject player;
		
		KeyInput(GameObject object){
			player = object;
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(player != null) {
				if((e.getKeyCode() == KeyEvent.VK_W && player.getID() == ID.LeftPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_UP && player.getID() == ID.RightPlayer)) {
					moveUp = true;
					switching = true;
				}
				if((e.getKeyCode() == KeyEvent.VK_S && player.getID() == ID.LeftPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_DOWN && player.getID() == ID.RightPlayer)) {
					moveDown = true;
					switching = false;
				}
			
				if((e.getKeyCode() == KeyEvent.VK_A && player.getID() == ID.TopPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_LEFT && player.getID() == ID.BottomPlayer)) {
					moveUp = true;
					switching = true;
				}
				if((e.getKeyCode() == KeyEvent.VK_D && player.getID() == ID.TopPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getID() == ID.BottomPlayer)) {
					moveDown = true;
					switching = false;
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				game = false;
				isRunning = false;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if(player != null) {
				if((e.getKeyCode() == KeyEvent.VK_W && player.getID() == ID.LeftPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_UP && player.getID() == ID.RightPlayer)) {
					moveUp = false;
				}
				if((e.getKeyCode() == KeyEvent.VK_S && player.getID() == ID.LeftPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_DOWN && player.getID() == ID.RightPlayer)) {
					moveDown = false;
				}
				if((e.getKeyCode() == KeyEvent.VK_A && player.getID() == ID.TopPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_LEFT && player.getID() == ID.BottomPlayer)) {
					moveUp = false;
				}
				if((e.getKeyCode() == KeyEvent.VK_D && player.getID() == ID.TopPlayer)
						|| (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getID() == ID.BottomPlayer)) {
					moveDown = false;
				}
				if(moveUp && !moveDown)
					switching = true;
				else if(!moveUp && moveDown)
					switching = false;
			}
			
			if(e.getKeyCode() == KeyEvent.VK_SPACE && !game) {
				isRunning = false;
				game = true;
				score[0] = 0;
				score[1] = 0;
			}
		}
		
		public void keyTyped(KeyEvent e) {
			
		}
	}
}

