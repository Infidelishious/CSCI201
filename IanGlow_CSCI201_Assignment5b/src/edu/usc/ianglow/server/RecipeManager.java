package edu.usc.ianglow.server;

import java.util.Vector;

public class RecipeManager {
	
	private static RecipeManager instance;
	
	Vector<Recipe> recipes;

	private MainFrame frame;
	
	protected RecipeManager(){
		recipes = new Vector<Recipe>();
	}

	public static RecipeManager getInstance() {
		if(instance == null) {
			instance = new RecipeManager();
		}
		return instance;
	}
	
	public synchronized Recipe getRecipe() {
		System.out.println("Getting recipe");
		for(Recipe i : recipes)
		{
			if(i.statis == Recipe.NOT_STARTED)
			{
				i.statis = Recipe.IN_PROGRESS;
				update();
				System.out.println("Got recipe");
				return i;
			}
		}
		
		
		for(Recipe i : recipes)
		{
			if(i.statis != Recipe.FINISHED)
			{
				return null;
			}
		}
//		frame.done();
		return null;
	}
	
	public void init(MainFrame f)
	{
		this.frame = f;
	}
	
	public void returnRecipe(Recipe a) {
		synchronized(this)
		{
			if(a == null) return;
				
			recipes.remove(a);
			frame.money += a.cost;
		
		}
		
		finishedMessage(a);
		update();
	}
	
	private void finishedMessage(Recipe a) {
		Message msg = new Message();
		msg.type = msg.FINISHED;
		msg.url = "http://barringtonstageco.org/media/potato.jpg";
		a.st.SendMessage(msg);
	}

	public void addRecipe(Recipe a){
		recipes.add(a);
	}
	
	public void update(){
		Vector<String> toPass = new Vector<String>();
		for(Recipe i : recipes)
		{
			toPass.add(i.toString());
		}
		frame.board.build(toPass);
	}
	
}
