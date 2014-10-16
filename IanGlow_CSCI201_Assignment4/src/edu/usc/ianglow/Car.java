package edu.usc.ianglow;

import java.awt.Color;
import java.lang.reflect.Field;

public abstract class Car implements Runnable{

	int x,y, num;
	float speed;
	
	Color color;
	
	public Car(int num, int x, int y, String color, float speed){
		this.num = num;
		this.x = x;
		this.y = y;
		this.color = stringToColor(color);
		this.speed = speed;
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

}
