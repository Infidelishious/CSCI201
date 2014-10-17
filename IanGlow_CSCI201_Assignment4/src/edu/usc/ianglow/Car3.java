package edu.usc.ianglow;

public class Car3 extends Car{

	boolean goEast;
	int dir;
	
	public Car3(MainFrame parent, int num, int type, int x, int y, String color, float speed) {
		super(parent, num, type, x, y, color, speed);
		
		goEast = true;
	}

	protected void move() {
		
		if(goEast && !isEaster())
			goEast = false;
		else if(!goEast && !isWester())
			goEast = true;

		if(goEast)
			dir = Land.RIGHT;
		else
			dir = Land.LEFT;
		
		if(current.travel[dir])
		{
			if(dir == Land.UP){
				moveTo(x,y-1);
				return;
			}
			else if(dir == Land.DOWN){
				moveTo(x,y+1);		
				return;
			}
			else if(dir == Land.RIGHT){
				moveTo(x+1,y);
				return;
			}
			else if(dir == Land.LEFT){
				moveTo(x-1,y);
				return;
			}
		}
		
		while(true){
			dir = (int) (Math.random() * 4.0f);
			if(current.travel[dir])
			{
				if(dir == Land.UP){
					moveTo(x,y-1);
					return;
				}
				else if(dir == Land.DOWN){
					moveTo(x,y+1);		
					return;
				}
				else if(dir == Land.RIGHT){
					moveTo(x+1,y);
					return;
				}
				else if(dir == Land.LEFT){
					moveTo(x-1,y);
					return;
				}
			}
		}
	}
	
	
	private boolean isEaster()
	{
		if(x == 8) 
			return false;
		else
		{
			for(int i = 0; i < 9; i++)
			{
				Land xd = parent.drawPanel.grid[i][x + 1];
				if(xd.travel[Land.UP] || xd.travel[Land.RIGHT] || xd.travel[Land.DOWN] || xd.travel[Land.LEFT])
				{
					return true;
				}
			}
			return false;
		}
	}
	
	private boolean isWester()
	{
		if(x == 0) 
			return false;
		else
		{
			for(int i = 0; i < 9; i++)
			{
				Land xd = parent.drawPanel.grid[i][x - 1];
				if(xd.travel[Land.UP] || xd.travel[Land.RIGHT] || xd.travel[Land.DOWN] || xd.travel[Land.LEFT])
				{
					return true;
				}
			}
			return false;
		}
	}
}
