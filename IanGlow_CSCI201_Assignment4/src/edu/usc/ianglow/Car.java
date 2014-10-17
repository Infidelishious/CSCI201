package edu.usc.ianglow;

import java.awt.Color;
import java.lang.reflect.Field;

public abstract class Car implements Runnable{

	Color color;
	Land current;
	MainFrame parent;
	
	int x, y, type, num, blinks;
	float speed;
	boolean blink;
	
	public Car(MainFrame parent, int num, int type, int x, int y, String color, float speed){
		this.parent = parent;
		this.num = num;
		this.type = type;
		this.x = x;
		this.y = y;
		current = parent.drawPanel.grid[y][x];
		this.color = stringToColor(color);
		this.speed = speed;
		blinks = 0;
		blink = false;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				synchronized (this) {
					float sec = (1.0f/speed);
					sec *= 1000;
					sec /= 3;
					this.wait((long)sec);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			blinks++;
			blinks %= 3;
			
			blink();
			
			if(blinks == 0)
				move();
		}
	}

	public String toString(){
		return "Num: " + num + "\n"
				+ "Type: " + type + "\n"
				+ "Speed: " + speed + "\n"
				+ "Color: " + color.toString() + "\n"
				+ "X: " + x + "\n"
				+ "Y: " + y + "\n";
	}
	
	public static Color stringToColor(String col){
		Color color;
		try {
		    Field field = Class.forName("java.awt.Color").getField(col); //Reflection! Fancy!
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		
		return color;
	}

	protected void blink(){
		if(blink)
			current.setCar(num,color);
		else
			current.setCar(0,color);
		
		blink = !blink;
	}
	
	protected void moveTo(int x, int y){
		current.setCar(0,color);
		
		this.x = x;
		this.y = y;
		blink = true;
		current = parent.drawPanel.grid[y][x];
		blink();
		updateTable(num, x, y);
	}
	
	protected void updateTable(int car, int x, int y){
		String[] names = {"" + car,
	            "" + (x + 1),
	            "" + new String(Character.toChars(y + 'A'))};
		
		parent.data.set(car, names);
		parent.makeTable(false);
	}

	protected abstract void move();
	
}
