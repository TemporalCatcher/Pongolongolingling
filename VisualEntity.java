package game.main;

import java.util.Random;

public class VisualEntity extends Entity {

	public VisualEntity(){
	super(0,0,0,0,0,ID.Background);
	int size = new Random().nextInt(128)+1;
	x = new Random().nextInt(Window.borderRight-size/2)+Window.borderLeft;
	y = new Random().nextInt(Window.borderBottom-size/2)+Window.borderTop;
	xSize = size;
	ySize = size;
	color = new Random().nextInt(6);
	
	bounce = true;
	
	speed = new Random().nextDouble()+1*5;
	
	xVel = (new Random().nextInt(3)+1)*((new Random().nextInt(2)+1)*2-3);
	yVel = (new Random().nextInt(3)+1)*((new Random().nextInt(2)+1)*2-3);
	
	yVelG = Math.sqrt(yVel*yVel+2*gravity*(Window.borderBottom-(y+ySize/2)));
	
	leftBorder = true;
	rightBorder = true;
	topBorder = true;
	bottomBorder = true;
	
	ifGravity = true;
	vConst = false;
	}
}
