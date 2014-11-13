package edu.usc.ianglow;

import java.util.ArrayList;

public class Recipe {
	
	int wood, metal, plastic;
	ArrayList<Action> actions;
	
	public class Action{
		ArrayList<Integer> tools;
		int time, location;
	}

}
