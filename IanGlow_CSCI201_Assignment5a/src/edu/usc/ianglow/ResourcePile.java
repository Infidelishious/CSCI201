package edu.usc.ianglow;

import java.util.ArrayList;

import edu.usc.ianglow.Recipe.Action;

public class ResourcePile {

	public static int WOOD = 0,
			PLASTIC = 1,
			METAL = 2;
	
	MainFrame frame;
	
	int num_wood, num_plastic, num_metal;
	
	public ResourcePile(MainFrame frame)
	{
		this.frame = frame;
	}
	
	public void init() {
		num_wood = 100;
		num_plastic = 100;
		num_metal = 100;
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
			num_wood--;
		else if(res == PLASTIC)
			num_plastic--;
		else 
			num_metal--;
		
		update();
	}
	
}
