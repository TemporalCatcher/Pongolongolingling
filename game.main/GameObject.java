package game.main;

import java.awt.Color;
import java.awt.Graphics;

public abstract class GameObject implements Comparable<GameObject> {
	
	protected int x, y, shape, xSize, ySize, color, left, right, top , bottom;
	protected double xVel, yVel, yVelI, yVelG, speed, angle,
		accel = 0.1 , gravity = 10, loss = 0;
	protected ID id;
	protected boolean rightTouch = false, leftTouch = false
			, topTouch = false , bottomTouch = false, bounce,
			ifGravity, isStill, leftBorder, rightBorder, topBorder, bottomBorder,
			isMoving, vConst = true, 
			prevLeft = false, prevRight = false,
			prevTop = false, prevBottom = false;
	
	public GameObject(int x, int y, int xSize, int ySize, int color, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.xSize = xSize;
		this.ySize = ySize;
		this.color = color;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void move(int direction);
	
	public abstract void stop();
	
	public abstract void collide(GameObject object);
	
	public int compareTo(GameObject other) {
		if(other.getArea() < getArea())
			return -1;
		else if(other.getArea() > getArea())
			return 1;
		else
			return 0;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setID(ID id) {
		this.id = id;
	}
	public ID getID() {
		return id;
	}
	public void setXVel(int xVel) {
		this.xVel = xVel;
	}
	public void setYVel(int yVel) {
		this.yVel = yVel;
	}
	public int getLeft() {
		return x - xSize/2;
	}
	public int getRight() {
		return x + xSize/2;
	}
	public int getTop() {
		return y - ySize/2;
	}
	public int getBottom() {
		return y + ySize/2;
	}
	public double getXVel() {
		return xVel;
	}
	public double getYVel() {
		return yVel;
	}
	public int getXSize() {
		return xSize;
	}
	public int getYSize() {
		return ySize;
	}
	public Color getColor() {
		if(color == 0)
			return Color.RED;
		else if(color == 1)
			return Color.GREEN;
		else if(color == 2)
			return Color.BLUE;
		else if(color == 3)
			return Color.YELLOW;
		else if(color == 4)
			return Color.MAGENTA;
		else if(color == 5)
			return Color.CYAN;
		else
			return Color.WHITE;
	}
	public void setRightTouch(boolean set) {
		rightTouch = set;
	}
	public void setLeftTouch(boolean set) {
		leftTouch = set;
	}
	public void setTopTouch(boolean set) {
		topTouch = set;
	}
	public void setBottomTouch(boolean set) {
		bottomTouch = set;
	}
	public boolean getBounce() {
		return bounce;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double s) {
		speed = s;
	}
	
	public void bounce() {
		getDirection();
		if(vConst) {
			if(bottomTouch || topTouch)
				angle = -angle;
			if(leftTouch || rightTouch)
				angle = Math.PI - angle;
			xVel = speed*Math.cos(angle);
			yVel = speed*Math.sin(angle);
		}
		else {
			if(bottomTouch || topTouch)
				yVel = -yVel;
			if(leftTouch || rightTouch)
				xVel = -xVel;
		}
		leftTouch = false;
		rightTouch = false;
		topTouch = false;
		bottomTouch = false;
		prevLeft = false;
		prevRight = false;
		prevTop = false;
		prevBottom = false;
	}
	
	public void gravBounce() {
		yVelG -= yVelG*loss;
		yVel = yVelG;
		yVel = -yVel;
	}
	
	public boolean hasGravity() {
		return ifGravity;
	}
	
	public boolean isStill() {
		return isStill;
	}
	
	public int getArea() {
		return getXSize()*getYSize();
	}
	
	public boolean comparePos(GameObject other) {
		if(getX() == other.getX() && getY() == other.getY())
			return true;
		return false;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setMoving(boolean b) {
		isMoving = b;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public void borders(int left, int right, int top, int bottom) {
		if(leftBorder && getLeft() < left) {
			leftTouch = true;
		}
		if(rightBorder && getRight() > right) {
			rightTouch= true;
		}
		if(topBorder && getTop() < top) {
			topTouch = true;
		}
		if(bottomBorder && getBottom() > bottom) {
			bottomTouch = true;
		}
		if(leftTouch && rightTouch) {
			leftTouch = false;
			rightTouch = false;
		}
		if(topTouch && bottomTouch) {
			topTouch = false;
			bottomTouch = false;
		}
		if(leftTouch) this.left = left;
		else this.left = 0;
		if(rightTouch) this.right = right;
		else this.right = 0;
		if(topTouch) this.top = top;
		else this.top = 0;
		if(bottomTouch) this.bottom = bottom;
		else this.bottom = 0;
		
	}
	
	public void collision() {
		if(leftTouch)
			leftCollision(left);
		if(rightTouch)
			rightCollision(right);
		if(topTouch)
			topCollision(top);
		if(bottomTouch)
			bottomCollision(bottom);
	}
	
	public void leftCollision(int left) {
		if(leftTouch) {
			if(!bounce) {
				x = left + xSize/2;
			}
			else {
				x += 2*(left - getLeft());
				bounce();
			}
			leftTouch = false;
		}
	}
	
	public void rightCollision(int right) {
		if(getRight() > right) {
			this.rightTouch = true;
			if(!bounce) {
				x = right - xSize/2;
			}
			else {
				x -= 2*(getRight() - right);
				bounce();
				this.rightTouch = false;
			}
		}
		else
			this.rightTouch = false;
	}
	
	public void topCollision(int top) {
		if(getTop() < top) {
			this.topTouch = true;
			if(!bounce) {
				y = top + ySize/2;
			}
			else {
				y += 2*(top - getTop());
				bounce();
				this.topTouch = false;
			}
		}
		else
			this.topTouch = false;
	}
	
	public void	bottomCollision(int bottom) {
		if(getBottom() > bottom) {
			this.bottomTouch = true;
			if(!bounce) {
				y = bottom - ySize/2;
			}
			else {
				y -= 2*(getBottom() - bottom);
				if(ifGravity)
					gravBounce();
				else	
					bounce();
				this.bottomTouch = false;
				if(yVel*yVel <= 0.1 && ifGravity)
					bounce = false;
			}
		}
		else
			this.bottomTouch = false;
	}
	
	public double getDirection() {
		angle = Math.atan(yVel/xVel);
		if(xVel < 0)
			angle += Math.PI;
		//System.out.println(angle);
		if(vConst) {
			if(angle < Math.PI/2 && angle > Math.PI/3)
				angle = Math.PI/3;
			else if(angle > Math.PI/2 && angle < 2 * Math.PI/3)
				angle = 2 * Math.PI/3;
			else if(angle < 3 * Math.PI/2 && angle > 4 * Math.PI/3)
				angle = 4 * Math.PI/3;
			else if(angle > 3 * Math.PI/2 && angle < 5 * Math.PI/3)
				angle = 5 * Math.PI/3;
		}
		return angle;
	}
	
}
