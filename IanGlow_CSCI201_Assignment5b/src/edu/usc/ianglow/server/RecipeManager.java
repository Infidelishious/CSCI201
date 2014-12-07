package edu.usc.ianglow.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

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
		//		System.out.println("Getting recipe");
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
		String toRet = "http://www.kickoff.com/chops/images/resized/large/no-image-found.jpg";


		try {
			String page = "";
			URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + a.name);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); 
			
			while(reader.ready())
			{
				String line = reader.readLine();
				page += line;
			}
			
			JSONObject json = new JSONObject(page);
			System.out.println(json);
			JSONObject responseData = (JSONObject) json.get("responseData"); 
			JSONArray results = (JSONArray) responseData.get("results"); 
			JSONObject first = (JSONObject) results.get(0);
			toRet = (String) first.get("url");
			System.out.println(toRet);

		} catch (Exception e) {
			e.printStackTrace();
		}

		msg.url = toRet;
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
