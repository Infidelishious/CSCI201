package edu.usc.ianglow;

import java.util.Vector;

public class Recipe {
	
	static int NOT_STARTED = 0,
			IN_PROGRESS = 1,
			FINISHED = 2;
	
	String name;
	int statis = NOT_STARTED;
	int wood = 0, metal = 0, plastic = 0;
	Vector<Action> actions;
	
	public Recipe() {
		actions = new Vector<Action>();
	}
	
	public Recipe(Recipe a) {
		this();
	    this.name = a.name;
	    this.wood = a.wood;
	    this.metal = a.metal;
	    this.plastic = a.plastic;
	    this.statis = a.statis;
	    
	    for(Action i : a.actions)
	    {
	    	actions.add(new Action(i));
	    }
	    
	  }
	
	public class Action{
		
		Vector<Integer> tools;
		int time, location;
		
		public Action() {
			tools = new Vector<Integer>();
		}
		
		public Action(Action i) {
			this();
			this.time = i.time;
			this.location = i.location;
			
			for(Integer j : i.tools)
			{
				tools.add(Integer.valueOf(j));
			}
		}
	}
	
	public String toString(){
		String st = "";
		
		if(statis == NOT_STARTED)
			st = "not built";
		else if(statis == IN_PROGRESS)
			st = "in progress";
		else
			st = "finished";
		
		return name + "... " + st;
	}

}
