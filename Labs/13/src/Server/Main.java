package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	NumberServer ns;
	ArrayList<Integer> list;
	List<Integer> syncList;
	
	public Main()
	{
		list = new ArrayList<Integer>();
		syncList = Collections.synchronizedList(list);
		ns = new NumberServer();
		
		for(int i = 0; i < 10; i++)
		{
			Thread th = new Thread(new Connection(ns, syncList));
			th.start();
		}
		
		while(syncList.size() < 10)
		{
			
		}
		
		for(Integer i :syncList)
		{
			System.out.println(i);
		}
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}
