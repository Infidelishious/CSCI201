package edu.usc.ianglow.server;

import java.io.Serializable;
import java.util.Vector;

public class Recipe implements Serializable{
	
	public static int NOT_STARTED = 0,
			IN_PROGRESS = 1,
			FINISHED = 2;
	
	public String name;
	public int statis = NOT_STARTED;
	public int wood = 0, metal = 0, plastic = 0;
	public Vector<Action> actions;
	public int cost = 0;
	public ServerThread st;
	
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
