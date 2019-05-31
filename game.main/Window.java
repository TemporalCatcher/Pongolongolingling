package game.main;

import java.awt.image.BufferedImage;
import java.awt.Insets;
import javax.swing.JFrame;

import game.scene.Scene;

import java.awt.Graphics;
import java.awt.Color;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Handler handler;
	
	private static int windowWidth = 800, windowHeight = windowWidth*3/4;//(int) (640 * 2.5), windowHeight = windowWidth / 8 * 5;
	Insets insets;
	BufferedImage backBuffer;
	HUD hud = new HUD();
	
	public static int borderLeft, borderRight, borderTop, borderBottom;
	
	public Window(String title, Scene scene, Handler handler) {
		super(title);
		setSize(windowWidth, windowHeight);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		add(scene);
		
		this.handler = handler;
		
		setVisible(true);
		
		insets = getInsets();
		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
		
		borderLeft = insets.left;
		borderRight = windowWidth - insets.right;
		borderTop = insets.top;
		borderBottom = windowHeight- insets.bottom;
		
	}
		
	

	public void render() {
		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		bbg.setColor(Color.BLACK);
		bbg.fillRect(0,0, borderRight, borderBottom);
		
		handler.render(bbg);
		hud.render(bbg);
		g.drawImage(backBuffer, 0, 0, this);
	}
	
	public void updateHandler(Handler handler) {
		this.handler = handler;
	}
	
	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler h) {
		handler = h;
	}
	
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	
}
