package edu.usc.ianglow;

import java.util.ArrayList;

public class Car2 extends Car{

	ArrayList<int[]> hasVisted;
	boolean[] tried;
	
	public Car2(MainFrame parent, int num, int type, int x, int y, String color, float speed) {
		super(parent, num, type, x, y, color, speed);
		
		hasVisted = new ArrayList<int[]>();
		
		tried = new boolean[4];
		
		tried[0] = tried[1] = tried[2] = tried[3] = false;
	}

	protected void move() {
		tried[0] = tried[1] = tried[2] = tried[3] = false;
		
		while(true){
			int dir = (int) (Math.random() * 4.0f);
			
			if(tried[Land.UP] && tried[Land.RIGHT] && tried[Land.DOWN] && tried[Land.LEFT])
			{
				hasVisted = new ArrayList<int[]>();
			}
					
			if(current.travel[dir])
			{
				if(dir == Land.UP){
					if(!haveYouTraveled(x,y-1)){
						int[] a = new int[2];
						a[0] = x;
						a[1] = y;
						hasVisted.add(a);
						moveTo(x,y-1);
						return;
					}
					else
						tried[Land.UP] = true;
				}
				else if(dir == Land.DOWN){
					if(!haveYouTraveled(x,y+1)){
						int[] a = new int[2];
						a[0] = x;
						a[1] = y;
						hasVisted.add(a);
						moveTo(x,y+1);		
						return;
					}
					else
						tried[Land.DOWN] = true;
				}
				else if(dir == Land.RIGHT){
					if(!haveYouTraveled(x+1,y)){
						int[] a = new int[2];
						a[0] = x;
						a[1] = y;
						hasVisted.add(a);
						moveTo(x+1,y);
						return;
					}
					else
						tried[Land.RIGHT] = true;
				}
				else if(dir == Land.LEFT){
					if(!haveYouTraveled(x-1,y)){
						int[] a = new int[2];
						a[0] = x;
						a[1] = y;
						hasVisted.add(a);
						moveTo(x-1,y);
						return;
					}
					else
						tried[Land.LEFT] = true;
				}
			}
			else
				tried[dir] = true;
		}
	}
		

	private boolean haveYouTraveled(int x, int y)
	{
		for(int[] i : hasVisted)
		{
			if(i[0] == x && i[1] == y)
				return true;
		}
		return false;
	}
}