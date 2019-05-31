package game.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.TreeMap;

import game.scene.Scene;

import java.util.Map;
import java.awt.Graphics;
import java.awt.Color;

public class Handler{
	List<GameObject> object = new LinkedList<GameObject>();
	List<GameObject> players = new LinkedList<GameObject>();
	List<GameObject> enemies = new LinkedList<GameObject>();
	List<GameObject> slaves = new LinkedList<GameObject>();
	List<GameObject> aux = new LinkedList<GameObject>();
	List<GameObject> check = new LinkedList<GameObject>();
	Map<Integer, List<GameObject> > map = new TreeMap<>();
	
	int[] background = new int[]{63,0,0};
	int backSpeed = 2;
	boolean visualBack = true;
	
	public void tick() {
		List<GameObject> tempAll = new LinkedList<>();
		
		if(!Scene.game) {
			aux.addAll(players);
			aux.addAll(enemies);
		}
		tempAll.addAll(object);
		tempAll.addAll(players);
		tempAll.addAll(enemies);
		tempAll.addAll(slaves);
		for(int i = 0; i<tempAll.size(); i++) {
			GameObject tempObject = tempAll.get(i);
			/*GameObject tempObject = getObject(i);
			List<GameObject> temp = map.get(tempObject.getArea());
			if(tempObject.isStill() && temp.size() > 4) {
				temp.remove(tempObject);
				for(int j = 0; j < temp.size(); j++) {
					GameObject tempObj = temp.get(j);
						if(tempObject.comparePos(tempObj)) {
							aux.add(tempObj);
					}
				}
			}*/
			tempObject.tick();
			if(enemies.contains(tempObject)) {
				moveAI(tempObject, slaves.get(0));
			}
		}
		for(int i = 0; i<tempAll.size(); i++) {
			GameObject tempObject = tempAll.get(i);
			if(tempObject.getID() == ID.Slave)
				tempObject.borders(Window.borderLeft - 50, Window.borderRight + 50,
						Window.borderTop, Window.borderBottom);
			else
				tempObject.borders(Window.borderLeft, Window.borderRight,
					Window.borderTop, Window.borderBottom);
		}
		for(int i = 0; i<tempAll.size(); i++) {
			GameObject tempObject = tempAll.get(i);
			tempObject.collision();
		}
		for(int i = 0; i < slaves.size(); i++) {
			GameObject tempObject = slaves.get(i);
			for(int j = 0; j < players.size();j++) {
				tempObject.collide(players.get(j));
			}
			for(int j = 0; j < enemies.size();j++)
				tempObject.collide(enemies.get(j));
		}
		for(int i = 0; i<tempAll.size(); i++) {
			GameObject tempObject = tempAll.get(i);
			tempObject.collision();
		}
		for(int i = 0; i<tempAll.size(); i++) {
			GameObject tempObject = tempAll.get(i);
			ob(tempObject, Window.borderLeft, Window.borderRight,
					Window.borderTop, Window.borderBottom);
		}
		if(!getAux().isEmpty())
			simplify();
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < getSize(); i++) {
			getObject(i).render(g);
		}
		if(visualBack)
			backColor(g);
		int j = 0;
		g.setColor(Color.WHITE);
		while(j < Window.borderBottom) {
			g.fillRect((Window.borderRight-Window.borderLeft)/2-1,j,2,10);
			j+=30;
		}
			
		for(int i = 0; i < players.size(); i++) {
			players.get(i).render(g);
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);
		}
		for(int i = 0; i < slaves.size(); i++) {
			slaves.get(i).render(g);
		}
	}
	
	public void addObject(GameObject object) {
		if(object.getID() == ID.LeftPlayer || object.getID() == ID.RightPlayer
				|| object.getID() == ID.TopPlayer || object.getID() == ID.BottomPlayer)
			players.add(object);
		else if(object.getID() == ID.RightEnemy)
			enemies.add(object);
		else if(object.getID() == ID.Slave)
			slaves.add(object);
		else {
			this.object.add(object);
			mapOut();
			sortList();
		}
	}
	
	public void moveAI(GameObject o, GameObject s) {
		if(o.getID() == ID.RightEnemy){
			if(o.getX() - s.getX() <= 60) {
				if(s.getY() + s.getYVel() > o.getBottom())
					o.move(2);
				else if(s.getY() + s.getYVel() < o.getTop())
					o.move(1);
				else
					o.stop();
			}
			else
				o.stop();
		}
	}
	
	public void removeObject(GameObject object) {
		if(object.getID() == ID.LeftPlayer || object.getID() == ID.RightPlayer)
			players.remove(object);
		else if(object.getID() == ID.RightEnemy)
			enemies.remove(object);
		else if(object.getID() == ID.Slave)
			slaves.remove(object);
		else {
			this.object.remove(object);
			sortList();
		}
	}
	
	public void sortList() {
		object.sort(null);
	}
	
	public GameObject getObject(int i) {
		return object.get(i);
	}
	
	public int getSize() {
		return object.size();
	}
	
	public void mapOut() {
		Map<Integer, List<GameObject> > tempMap = new TreeMap<>();
		for(int i = 0; i < getSize(); i++) {
			int temp = getObject(i).getArea();
			if(!tempMap.containsKey(temp))
				tempMap.put(temp, new LinkedList<GameObject>());
			tempMap.get(temp).add(getObject(i));
			Collections.sort(tempMap.get(temp));
		}
		map = tempMap;
	}
	
	public void simplify() {
		object.removeAll(aux);
		players.removeAll(aux);
		enemies.removeAll(aux);
		aux.clear();
		//sortList();
	}
	
	public List<GameObject> getAux() {
		return aux;
	}
	
	@Override
	public String toString() {
		String string = "(" + Window.borderLeft + ", " + Window.borderTop + ")\n";
		for(int i = 0; i < check.size(); i++)
			string += check.get(i).toString() + " ";
		return string;
	}
	
	public void ob(GameObject object, int left, int right, int top, int bottom) {
		if(object.getRight() < left - 100
				|| object.getLeft() > right + 100
				|| object.getBottom() < top - 100
				|| object.getTop() > bottom + 100)
			aux.add(object);
	}
	
	public List<GameObject> getPlayers() {
		return players;
	}
	
	public List<GameObject> getEnemies() {
		return enemies;
	}
	
	public List<GameObject> getSlaves() {
		return slaves;
	}
	
	private void backColor(Graphics g) {
		if(background[0] > 63)
			background[0] = 63;
		if(background[1] > 63)
			background[1] = 63;
		if(background[2] > 63)
			background[2] = 63;
		if(background[0] < 0)
			background[0] = 0;
		if(background[1] < 0)
			background[1] = 0;
		if(background[2] < 0)
			background[2] = 0;
		g.setColor(new Color(background[0],background[1],background[2],223));
		/*if(background[0] < 63 && background[1] == 0 && background[2] == 0) {
			background[0]+=backSpeed;
		}*/
		if(background[0] >= 63 && background[1] < 63 && background[2] == 0) {
			background[1]+=backSpeed;
		}
		else if(background[0] > 0 && background[1] >= 63  && background[2] == 0) {
			background[0]-=backSpeed;
		}
		else if(background[0] == 0 && background[1] >= 63 && background[2] < 63) {
			background[2]+=backSpeed;
		}
		else if(background[0] == 0 && background[1] > 0 && background[2] >= 63) {
			background[1]-=backSpeed;
		}
		else if(background[0] < 63 && background[1] == 0 && background[2] >= 63) {
			background[0]+=backSpeed;
		}
		else if(background[0] >= 63 && background[1] == 0 && background[2] > 0) {
			background[2]-=backSpeed;
		}
		g.fillRect(0,0,Window.borderRight,Window.borderBottom);
	}
}
