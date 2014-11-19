package Server;

import java.util.List;

public class Connection implements Runnable{

	NumberServer ns;
	List<Integer> syncList;
	
	public Connection(NumberServer ns, List<Integer> syncList)
	{
		this.ns = ns;
		this.syncList = syncList;
	}
	
	@Override
	public void run() {
		syncList.add(ns.getNumber().intValue());
	}

}
