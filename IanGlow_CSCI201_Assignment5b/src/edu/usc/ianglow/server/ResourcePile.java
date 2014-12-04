package edu.usc.ianglow.server;

import java.util.ArrayList;

import edu.usc.ianglow.server.Recipe.Action;

public class ResourcePile {

	public static int WOOD = 0,
			PLASTIC = 1,
			METAL = 2;
	
	MainFrame frame;
	
	Object resLcok;
	
	int num_wood, num_plastic, num_metal;
	
	public ResourcePile(MainFrame frame)
	{
		this.frame = frame;
	}
	
	public void init() {
		num_wood = 0;
		num_plastic = 0;
		num_metal = 0;
		update();
	}

	public void update() {
		frame.wood.label = "" + num_wood;
		frame.plastic.label = "" + num_plastic;
		frame.metal.label = "" + num_metal;
		
		frame.repaint();
	}
	
	public Square getAssociatedSqure(int res)
	{
		if(res == WOOD)
			return frame.wood;
		if(res == PLASTIC)
			return frame.plastic;

		return frame.metal;
	}
	
	public synchronized void getRes(int res)
	{
		
		if(res == WOOD)
		{
			while(num_wood <= 0)
			{
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			num_wood--;
		}
		else if(res == PLASTIC)
		{
			while(num_plastic <= 0)
			{
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			num_plastic--;
		}
		else 
		{
			while(num_metal <= 0)
			{
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			num_metal--;
		}
		
		update();
	}
	
	public synchronized  void addRes(int res, int amount)
	{
		if(res == WOOD)
			num_wood += amount;
		else if(res == PLASTIC)
			num_plastic += amount;
		else 
			num_metal += amount;
		
		update();
		
		notifyAll();
	}
	
}
