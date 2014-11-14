package edu.usc.ianglow;

import java.util.ArrayList;

public class RecipeManager {
	
	private static RecipeManager instance;
	
	ArrayList<Recipe> recipes;

	private MainFrame frame;
	
	protected RecipeManager(){
		recipes = new ArrayList<Recipe>();
	}

	public static RecipeManager getInstance() {
		if(instance == null) {
			instance = new RecipeManager();
		}
		return instance;
	}
	
	public synchronized Recipe getRecipe() {
		for(Recipe i : recipes)
		{
			if(i.statis == Recipe.NOT_STARTED)
			{
				i.statis = Recipe.IN_PROGRESS;
				update();
				return i;
			}
		}
		
		System.out.println("Finished");
		return null;
	}
	
	public void init(MainFrame f)
	{
		this.frame = f;
	}
	
	public synchronized void returnRecipe(Recipe a) {
		a.statis = Recipe.FINISHED;
		update();
	}
	
	public void addRecipe(Recipe a){
		recipes.add(a);
	}
	
	public void update(){
		ArrayList<String> toPass = new ArrayList<String>();
		for(Recipe i : recipes)
		{
			toPass.add(i.toString());
		}
		frame.board.build(toPass);
	}
	
}
