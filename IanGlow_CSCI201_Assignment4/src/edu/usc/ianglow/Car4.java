package edu.usc.ianglow;

public class Car4 extends Car{

	int[] last;
	boolean[] tried;
	
	public Car4(MainFrame parent, int num, int type, int x, int y, String color, float speed) {
		super(parent, num, type, x, y, color, speed);
		
		last = new int[4];
		tried = new boolean[4];
		
		tried[0] = tried[1] = tried[2] = tried[3] = false;
		last[0] = last[1] = last[2] = last[3] = 0;
	}

	protected void move() {
		tried[0] = tried[1] = tried[2] = tried[3] = false;
		
		while(true){
			int dir = dirLeastTraveled();
			
			if(current.travel[dir])
			{
				if(dir == Land.UP){
					incLeastTraveled();
					last[Land.UP] = 0;
					moveTo(x,y-1);
					return;
				}
				else if(dir == Land.DOWN){
					incLeastTraveled();
					last[Land.DOWN] = 0;
					moveTo(x,y+1);		
					return;
				}
				else if(dir == Land.RIGHT){
					incLeastTraveled();
					last[Land.RIGHT] = 0;
					moveTo(x+1,y);
					return;
				}
				else if(dir == Land.LEFT){
					incLeastTraveled();
					last[Land.LEFT] = 0;
					moveTo(x-1,y);
					return;
				}
			}
		}
	}
	
	private int dirLeastTraveled()
	{
		int max = -1;
		int maxIndex = -1;
		
		for(int i = 0; i < 4; i++)
		{
			if(!tried[i])
			{
				if(last[i] > max)
				{
					maxIndex = i;
					max = last[i];
				}	
			}
		}
		
		tried[maxIndex] = true;
		
		if(maxIndex == -1){
			tried[0] = tried[1] = tried[2] = tried[3] = false;
			return dirLeastTraveled();
		}
		return maxIndex;
	}
	
	private void incLeastTraveled()
	{
		last[0]++;
		last[1]++;
		last[2]++;
		last[3]++;
	}
}
