package game.main;

import game.scene.Scene;

public class Ball extends Entity {
	
	private double vEffect = 0.1;
	
	public Ball(int x, int y, int s, double a, ID id){
		super(x,y,16,16,7,id);
		shape = 0;
		speed = s;
		angle = a;
		xVel = speed * Math.cos(angle);
		yVel = speed * Math.sin(angle);
		
		accel = .1;
		
		yVelG = Math.sqrt(yVel*yVel+2*gravity*(Window.borderBottom-(y+ySize/2)));
		
		ifGravity = false;
		bounce = true;
		
		leftBorder = false;
		rightBorder = false;
		
		topBorder = true;
		bottomBorder = true;
		vConst = true;
	}
	
	@Override
	public void collide(GameObject o) {
		if((o.getLeft() < getLeft() && o.getRight() > getLeft())
				&& o.getTop() < getTop() && o.getBottom() > getTop()) {
			leftTouch = true;
			topTouch = true;
			prevLeft = false;
			prevTop = false;
		}
		if((o.getLeft() < getRight() && o.getRight() > getRight())
				&& o.getTop() < getTop() && o.getBottom() > getTop()) {
			rightTouch = true;
			topTouch = true;
			prevRight = false;
			prevTop = false;
		}
		if((o.getLeft() < getLeft() && o.getRight() > getLeft())
				&& o.getTop() < getBottom() && o.getBottom() > getBottom()) {
			leftTouch = true;
			bottomTouch = true;
			prevLeft = false;
			prevBottom = false;
		}
		if((o.getLeft() < getRight() && o.getRight() > getRight())
				&& o.getTop() < getBottom() && o.getBottom() > getBottom()) {
			rightTouch = true;
			bottomTouch = true;
			prevRight = false;
			prevBottom = false;
		}
		if(leftTouch && rightTouch) {
			leftTouch = false;
			rightTouch = false;
		}
		if(topTouch && bottomTouch) {
			topTouch = false;
			bottomTouch = false;
		}
		if((leftTouch && !prevLeft) 
				|| (rightTouch && !prevRight) 
				|| (topTouch && !prevTop) 
				|| (bottomTouch && !prevBottom)) {
			xVel += o.getXVel()*vEffect;
			yVel += o.getYVel()*vEffect;
		}
		if((leftTouch && !prevLeft) && (topTouch && !prevTop)) {
			topLeft(o);
		}
		if((rightTouch && !prevRight) && (topTouch && !prevTop)) {
			topRight(o);
		}
		if((leftTouch && !prevLeft) && (bottomTouch && !prevBottom)) {
			bottomLeft(o);
		}
		if((rightTouch && !prevRight) && (bottomTouch && !prevBottom)) {
			bottomRight(o);
		}
		if(xVel > 0)
			leftTouch = false;
		if(xVel < 0)
			rightTouch = false;
		if(yVel > 0)
			topTouch = false;
		if(yVel < 0)
			bottomTouch = false;
		if(leftTouch) {
			if(!prevLeft) {
				left = o.getRight();
				prevLeft = true;
			}
		}
		else {
			left = 0;
			prevLeft = false;
		}
		if(rightTouch) {
			if(!prevRight) {
				right = o.getLeft();
				prevRight = true;
			}
		}
		else {
			right = 0;
			prevRight = false;
		}
		if(topTouch) {
			if(!prevTop) {
				top = o.getBottom();
				prevTop = true;
			}
		}
		else {
			top = 0;
			prevTop = false;
		}
		if(bottomTouch) {
			if(!prevBottom) {
				bottom = o.getTop();
				prevBottom = true;
			}
		}
		else {
			bottom = 0;
			prevBottom = false;
		}
	}
	
	public void tick() {
		super.tick();
		if(Main.DEBUG) {
			leftBorder = true;
			rightBorder = true;
		}
		else {
			if(Scene.game) {
				if(x+xSize < Window.borderLeft - 50)  {
					Scene.incrementScore(1);
					x = (Window.borderRight - 32 - Window.borderLeft)/2;
				}
				else if(x > Window.borderRight + 50) {
					Scene.incrementScore(0);
					x = (Window.borderRight - 32 - Window.borderLeft)/2;
				}
				if(Scene.getScore()[1] > 10 || Scene.getScore()[0] > 10)
					Scene.game = false;
				leftBorder = false;
				rightBorder = false;
			}
			else if (!Scene.game) {
				leftBorder = true;
				rightBorder = true;
			}
		}
	}
	
	private void topLeft(GameObject o) {
		if(getLeft() - o.getRight() > o.getBottom() - getTop()) {
			topTouch = false;
		}
		else if(getLeft() - o.getRight() < o.getBottom() - getTop()) {
			leftTouch = false;
		}
	}
	
	private void topRight(GameObject o) {
		if(o.getLeft() - getRight() > o.getBottom() - getTop()) {
			topTouch = false;
		}
		else if(o.getLeft() - getRight() < o.getBottom() - getTop()) {
			rightTouch = false;
		}
	}
	
	private void bottomLeft(GameObject o) {
		if(getLeft() - o.getRight() < getBottom() - o.getTop()) {
			bottomTouch = false;
		}
		else if(getLeft() - o.getRight() > getBottom() - o.getTop()) {
			leftTouch = false;
		}
	}
	
	private void bottomRight(GameObject o) {
		if(o.getLeft() - getRight() > getBottom() - o.getTop()) {
			bottomTouch = false;
		}
		else if(o.getLeft() - getRight() < getBottom() - o.getTop()) {
			rightTouch = false;
		}
	}
	
	private class Tail {
		Tail tail;
		int size;
		
		Tail(int after){
			tail = new Tail(after-1);
			size = after*4;
		}
		
		public void tick() {
			
		}
	}
}
