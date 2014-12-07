package edu.usc.ianglow.server;

import java.io.Serializable;
import java.util.Vector;

public class Action implements Serializable{
	
	public Vector<Integer> tools;
	public int time, location;
	
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