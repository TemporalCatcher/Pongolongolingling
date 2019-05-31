package game.main;

import java.awt.Color;
import java.awt.Graphics;

import game.scene.Scene;

public class HUD {
	
	private int thick = 8, width = 32, height = 64;
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		shape(g, Scene.getScore()[0], Window.borderRight/4,
				Window.borderTop + 10, 0, true);
		shape(g, Scene.getScore()[1], 3*Window.borderRight/4,
				Window.borderTop + 10, 0, true);
	}
	
	public int shape(Graphics g, int score, int x, int y, int space, boolean leftAlign) {
		if(score / 10 > 0) {
			if(leftAlign)
				space = shape(g, score / 10, x, y, space, leftAlign);
			else
				shape(g, score / 10, x, y, space-width-thick-thick/2, leftAlign);
				
		}
		int ones = score % 10;
		if(ones == 0) shape0(g, x+space, y);
		else if(ones == 1) shape1(g, x+space, y);
		else if(ones == 2) shape2(g, x+space, y);
		else if(ones == 3) shape3(g, x+space, y);
		else if(ones == 4) shape4(g, x+space, y);
		else if(ones == 5) shape5(g, x+space, y);
		else if(ones == 6) shape6(g, x+space, y);
		else if(ones == 7) shape7(g, x+space, y);
		else if(ones == 8) shape8(g, x+space, y);
		else if(ones == 9) shape9(g, x+space, y);
		
		return space+width+thick+thick/2;
	}
	
	private void shape0(Graphics g, int x, int y) {
		g.fillRect(x,y,thick,height);
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y,thick,height);
		g.fillRect(x,y+height-thick,width,thick);
	}
	
	private void shape1(Graphics g, int x, int y) {
		g.fillRect(x+width-thick,y,thick,height);
	}
	
	private void shape2(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y,thick,height/2);
		g.fillRect(x,y+height/2-thick/2,thick,height/2);
		g.fillRect(x,y+height/2-thick/2,width,thick);
		g.fillRect(x,y+height-thick,width,thick);
	}
	
	private void shape3(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+height/2-thick,y,thick,height);
		g.fillRect(x,y+height/2-thick/2,width,thick);
		g.fillRect(x,y+height-thick,width,thick);
	}
	
	private void shape4(Graphics g, int x, int y) {
		g.fillRect(x+width-thick,y,thick,height);
		g.fillRect(x,y,thick,height/2);
		g.fillRect(x,y+height/2-thick/2,width,thick);
	}
	
	private void shape5(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y+height/2,thick,height/2);
		g.fillRect(x,y,thick,height/2);
		g.fillRect(x,y+height/2-thick/2,width,thick);
		g.fillRect(x,y+height-thick,width,thick);
	}
	
	private void shape6(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y+height/2,thick,height/2);
		g.fillRect(x,y,thick,height);
		g.fillRect(x,y+height/2-thick/2,width,thick);
		g.fillRect(x,y+height-thick,width,thick);
	}
	
	private void shape7(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y,thick,height);
	}
	
	private void shape8(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y,thick,height);
		g.fillRect(x,y,thick,height);
		g.fillRect(x,y+height/2-thick/2,width,thick);
		g.fillRect(x,y+height-thick,width,thick);
	}
	
	private void shape9(Graphics g, int x, int y) {
		g.fillRect(x,y,width,thick);
		g.fillRect(x+width-thick,y,thick,height);
		g.fillRect(x,y,thick,height/2);
		g.fillRect(x,y+height/2-thick/2,width,thick);
		g.fillRect(x,y+height-thick,width,thick);
	}
}
