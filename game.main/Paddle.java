package game.main;

public class Paddle extends Entity {
	public Paddle(int x, int y, ID id){
		super(x,y,16,96,7,id);
		ifGravity = false;
		bottomBorder = true;
		topBorder = true;
		yVel = -2;
		speed = 30;
	}
	
	public Paddle(int x, int y, boolean type, ID id) {
		super(x,y,96,16,7,id);
		
		leftBorder = true;
		rightBorder = true;
	}
	
	@Override
	public void move(int direction) {
		if(direction == 1 && getTop() > Window.borderTop + 1)
			yVel = -speed;
		else if(direction == 2 && getBottom() < Window.borderBottom - 1)
			yVel = speed;
		else
			yVel = 0;
		if(direction == 3 && getLeft() > Window.borderLeft + 1)
			xVel = -speed;
		else if(direction == 4 && getRight() < Window.borderRight - 1)
			xVel = speed;
	}
}
