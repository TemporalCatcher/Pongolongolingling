package game.main;

import java.awt.Graphics;
//import java.util.Random;

public class Entity extends GameObject {
	
	public Entity(int x, int y, int xSize, int ySize, int color, ID id) {
		super(x, y, xSize, ySize, color, id);
		yVelG = Math.sqrt(yVelI*yVelI+2*gravity*(Window.borderBottom-getBottom()));
		yVel = yVelI;
		
		shape = 0;
		
		bounce = false;
		ifGravity = false;
		isStill = false;
		
		leftBorder = false;
		rightBorder = false;
		topBorder = false;
		bottomBorder = false;
		
	}

	@Override
	public void tick() {
		if(ifGravity) {
			gravity();
		}
		else{
			if(!leftTouch && !rightTouch)
				x += xVel;
			else
				xVel = 0;
			if(!bottomTouch && !topTouch)
				y += yVel;
			else
				yVel = 0;
		}
		
		if(xVel == 0 && yVel == 0)
			isStill = true;
		else
			isStill = false;
	}
	
	public void gravity() {
		yVelI = yVel;
		if(!bottomTouch || bounce) 
			yVel += gravity;
		double yTemp = (yVelI + yVel) / 2;
		if(yVel*yVel > 0.01)
			y += yTemp;
		else
			yVel = 0;
		if(yVel*yVel <= 0.01 && bottomTouch) {
			bounce = false;
			yVel = 0;
		}
		if(!leftTouch && !rightTouch && !bottomTouch)
			x += xVel;
		else if(!bounce && bottomTouch) {
			if (xVel*xVel <= 0.25)
				xVel = 0;
			else if(xVel < 0)
				xVel += 10;
			else if(xVel > 0)
				xVel -= 10;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(getColor());
		g.fillRect(x-xSize/2, y-ySize/2, xSize, ySize);
	}
	
	public void move(int direction) {
		if(direction == 1) {
			yVel = -1;
		}
		else if(direction == 2)
			yVel = 1;
	}
	
	public void stop() {
		yVel = 0;
		xVel = 0;
	}
	
	public void collide(GameObject object) {
	
	}
}
