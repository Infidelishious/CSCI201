package edu.usc.ianglow;

public class Car1 extends Car{

	int past_x, past_y;
	int dir;
	
	public Car1(MainFrame parent, int num, int type, int x, int y, String color, float speed) {
		super(parent, num, type, x, y, color, speed);
		past_x = -1;
		past_y = -1;
	}

	protected void move() {
		
		dir = Land.LEFT;
		
		while(true){
			if(current.travel[dir])
			{
				if(dir == Land.UP && !(past_x == x && past_y == y - 1)){
					past_x = x;
					past_y = y;
					moveTo(x,y-1);
					return;
				}
				else if(dir == Land.DOWN && !(past_x == x && past_y == y + 1)){
					past_x = x;
					past_y = y;
					moveTo(x,y+1);		
					return;
				}
				else if(dir == Land.RIGHT && !(past_x == x + 1 && past_y == y)){
					past_x = x;
					past_y = y;
					moveTo(x+1,y);
					return;
				}
				else if(dir == Land.LEFT && !(past_x == x - 1 && past_y == y)){
					past_x = x;
					past_y = y;
					moveTo(x-1,y);
					return;
				}
			}
	
			dir++;
			dir = dir % 4;
		}
	}

}
