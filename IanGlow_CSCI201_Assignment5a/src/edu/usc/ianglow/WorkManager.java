package edu.usc.ianglow;

import java.util.Vector;

public class WorkManager {
private static WorkManager instance;
	
	Vector<Worktable> tables;

	private MainFrame frame;
	
	protected WorkManager(){
		tables = new Vector<Worktable>();
	}

	public static WorkManager getInstance() {
		if(instance == null) {
			instance = new WorkManager();
		}
		return instance;
	}
	
	public synchronized Worktable getOpenWorktable(int type) {
		for(Worktable i : tables)
		{
			if(i.open.get() == true && i.type == type)
			{
				i.open.set(false);
				return i;
			}
		}
		
		return null;
	}
	
	public void add(Worktable a)
	{
		tables.add(a);
	}
}

