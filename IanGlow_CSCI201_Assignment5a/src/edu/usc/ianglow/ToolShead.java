package edu.usc.ianglow;

import java.util.ArrayList;

import edu.usc.ianglow.Recipe.Action;

public class ToolShead {
	
	public static int SCREWDRIVER = 0,
			HAMMER = 1,
			PAINTBRUSH = 2,
			PLIERS = 3,
			SCISSORS = 4;
	
	Object inputLock = new Object();
	Object outputLock = new Object();
	Object waitLock = new Object();
	
	MainFrame frame;
	
	int num_screw, num_hammer, num_pb, num_pliers, num_scissors,
		num_screwt, num_hammert, num_pbt, num_plierst, num_scissorst;
	
	public ToolShead(MainFrame frame)
	{
		this.frame = frame;
	}
	
	public void init() {
		num_screw = num_screwt;
		num_hammer = num_hammert;
		num_pb = num_pbt;
		num_pliers = num_plierst;
		num_scissors = num_scissorst;
		update();
	}

	public void update() {
		frame.screwdriver.label = "" + num_screw + "/" + num_screwt;
		frame.hammer.label = "" + num_hammer + "/" + num_hammert;
		frame.paintbrush.label = "" + num_pb + "/" + num_pbt;
		frame.pliers.label = "" + num_pliers + "/" + num_plierst;
		frame.scissors.label = "" + num_scissors + "/" + num_scissorst;
	}
	
	public void getTools(Worker w, LarpListener l, Action a){
		synchronized(outputLock){
			
			while(!enoughTools(a.tools))
			{
				synchronized(waitLock)
				{
					try {
						waitLock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			w.getTools();
				
			synchronized(inputLock)//before actual take
			{
				for(Integer i : a.tools)
				{
					if(i == SCREWDRIVER)
						num_screw++;
					else if(i == HAMMER)
						 num_hammer++;
					else if(i == PLIERS)
						num_pliers++;
					else if(i == SCISSORS)
						num_scissors++;
					else
						num_pb++;
				}
			}
			
		}
	}
	
	public void returnTool(final Worker w, final LarpListener l, final int tool){
		w.facneyLarp(new LarpListener(){
			@Override
			public void reachedLocation() {
				synchronized(inputLock)
				{
					addTool(tool);
					w.removeTool(tool);
					l.reachedLocation();
					
					synchronized(waitLock)
					{
						waitLock.notifyAll();
					}
				}
				
			}}, getAssociatedSqure(tool));
	}
	
	public Square getAssociatedSqure(int tool)
	{
		if(tool == SCREWDRIVER)
			return frame.screwdriver;
		if(tool == HAMMER)
			return frame.hammer;
		if(tool == PLIERS)
			return frame.pliers;
		if(tool == SCISSORS)
			return frame.scissors;
		
		return frame.paintbrush;
	}
	
	public void addTool(int tool)
	{
		if(tool == SCREWDRIVER)
			num_screw++;
		else if(tool == HAMMER)
			 num_hammer++;
		else if(tool == PLIERS)
			num_pliers++;
		else if(tool == SCISSORS)
			num_scissors++;
		else
			num_pb++;
	}
	
	public void removeTool(int tool)
	{
		if(tool == SCREWDRIVER)
			num_screw--;
		else if(tool == HAMMER)
			 num_hammer--;
		else if(tool == PLIERS)
			num_pliers--;
		else if(tool == SCISSORS)
			num_scissors--;
		else
			num_pb--;
	}
	
	public boolean enoughTools(ArrayList<Integer> tools)
	{
		int num_screw = 0, num_hammer = 0, num_pb = 0, num_pliers = 0, num_scissors = 0;
		
		for(Integer i : tools)
		{
			if(i == SCREWDRIVER)
				num_screw++;
			else if(i == HAMMER)
				 num_hammer++;
			else if(i == PLIERS)
				num_pliers++;
			else if(i == SCISSORS)
				num_scissors++;
			else
				num_pb++;
		}
		
		if(num_screw > this.num_screw)
			return false;
		if(num_hammer > this.num_hammer)
			return false;
		if(num_pliers > this.num_pliers)
			return false;
		if(num_scissors > this.num_scissors)
			return false;
		if(num_pb > this.num_pb)
			return false;
		
		return true;
	}
	

}
