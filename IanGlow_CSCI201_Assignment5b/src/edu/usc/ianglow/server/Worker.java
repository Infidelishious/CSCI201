package edu.usc.ianglow.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.usc.ianglow.server.Recipe.Action;

public class Worker extends JPanel implements Runnable{

	private static int HIGH_Y = 140,
			LEFT_X = 100,
			RIGHT_X = 500;

	String label, top;
	Image img;
	OutPanel panel; 
	Object lock = new Object(),
			fancylock = new Object();
		
	boolean actionCompleted;
	int x = 0,
			y = 0;
	int num_screw = 0, num_hammer = 0, num_pb = 0, num_pliers = 0, num_scissors = 0, num_wood = 0, num_metal = 0, num_plastic = 0;

	Recipe cRcp;
	Action cAct;
	Thread th;
	Object larpLock = new Object();
	AtomicBoolean thinking = new AtomicBoolean(false);

	private int fin;

	protected int jim;

	private int jimmy;

	public Worker(OutPanel panel)
	{
		this.panel = panel;
		this.setPreferredSize(new Dimension(80,80));
		this.setOpaque(false);
		this.setSize(80, 60);
		this.setBounds(x, y, getWidth(), getHeight());
		try {
			this.img = ImageIO.read(new File("img/worker.png"));
		} catch (IOException e) {
			e.printStackTrace();
		};
		actionCompleted = false;
	}

	public Worker(OutPanel panel, int x, int y)
	{
		this(panel);
		this.x = x;
		this.y = y;
		this.setBounds(x, y, getWidth(), getHeight());
		panel.revalidate();
		th = new Thread(this);
		th.start();
	}

	private void think() {
		if(thinking.get())
		{
			think2();
			return;
		}
		
		thinking.set(true);
		
		final Worker thiss = this;

		if(cRcp == null)
		{
			facneyLarp(new LarpListener(){

				@Override
				public void reachedLocation() {
					cRcp = RecipeManager.getInstance().getRecipe();
					think2();
				}}, panel.frame.tasks);
			return;
		}

		if(cRcp.wood > num_wood)
		{
			facneyLarp(new LarpListener(){

				@Override
				public void reachedLocation() {
					panel.frame.resPile.getRes(ResourcePile.WOOD);
					num_wood++;
					think2();
				}}, panel.frame.wood);
			return;
		}
		else if(cRcp.metal > num_metal)
		{
			facneyLarp(new LarpListener(){

				@Override
				public void reachedLocation() {
					panel.frame.resPile.getRes(ResourcePile.METAL);
					num_metal++;
					think2();
				}}, panel.frame.metal);
			return;
		}
		else if(cRcp.plastic > num_plastic)
		{
			facneyLarp(new LarpListener(){

				@Override
				public void reachedLocation() {
					panel.frame.resPile.getRes(ResourcePile.PLASTIC);
					num_plastic++;
					think2();
				}}, panel.frame.plastic);
			return;
		}
		
		if(cRcp.actions.size() == 0)
		{
			if(num_screw == 0 && num_hammer == 0 && num_pb == 0 && num_pliers == 0 && num_scissors == 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						RecipeManager.getInstance().returnRecipe(cRcp);
						cRcp = null;
						num_wood = 0;
						num_metal = 0;
						num_plastic = 0;
						think2();
					}}, panel.frame.tasks);
				return;
			}
			else if(num_screw > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.SCREWDRIVER);
					}}, panel.frame.screwdriver);
				return;
			}
			else if(num_hammer > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.HAMMER);
					}}, panel.frame.hammer);
				return;
			}
			else if(num_pb > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.PAINTBRUSH);
					}}, panel.frame.paintbrush);
				return;
			}
			else if(num_pliers > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.PLIERS);
					}}, panel.frame.pliers);
				return;
			}
			else if(num_scissors > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.SCISSORS);
					}}, panel.frame.scissors);
				return;
			}
		}

		if(actionCompleted)
		{
			if(num_screw == 0 && num_hammer == 0 && num_pb == 0 && num_pliers == 0 && num_scissors == 0)
			{
				cRcp.actions.remove(0);
				actionCompleted = false;
				think2();
			}
			else if(num_screw > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.SCREWDRIVER);
					}}, panel.frame.screwdriver);
				return;
			}
			else if(num_hammer > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.HAMMER);
					}}, panel.frame.hammer);
				return;
			}
			else if(num_pb > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.PAINTBRUSH);
					}}, panel.frame.paintbrush);
				return;
			}
			else if(num_pliers > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.PLIERS);
					}}, panel.frame.pliers);
				return;
			}
			else if(num_scissors > 0)
			{
				facneyLarp(new LarpListener(){

					@Override
					public void reachedLocation() {
						panel.frame.toolshead.returnTool(thiss, new LarpListener(){

							@Override
							public void reachedLocation() {
								think2();

							}}, ToolShead.SCISSORS);
					}}, panel.frame.scissors);
				return;
			}
		}

		int tool = -1;
		
		if(cRcp.actions.size() > 0)
			tool = toolNeeded(cRcp.actions.get(0).tools);
		
		//Completing an action
//		System.out.println("Tool needed: " + tool);
		if(tool != -1)
		{
			blockingLarp(panel.frame.waitingArea);
			panel.frame.toolshead.getTools(this, new LarpListener(){

				@Override
				public void reachedLocation() {
					think2();
				}}, cRcp.actions.get(0));
			return;
		}
		else
		{
			//Go Places, do things
			
			
			if(cRcp.actions.size() == 0) //wait for table
			{
				think2();
				return;
			}
			
			final Worktable target = WorkManager.getInstance().getOpenWorktable(cRcp.actions.get(0).location);		
			
			
			if(target == null) //wait for table
			{
				think2();
				return;
			}
			
			System.out.println("Location: " + target.type + " wanted " + cRcp.actions.get(0).location);
			
			if(cRcp != null && cRcp.actions != null && cRcp.actions.size() > 0)
				target.work(thiss, cRcp.actions.get(0).time);
			actionCompleted = true;
			think2();
	
		}
	}

	protected void think2() {
		thinking.set(false);
	}

	private int toolNeeded(Vector<Integer> tools) {
		int num_screw = 0;
		int num_hammer = 0;
		int num_pb = 0;
		int num_pliers = 0;
		int num_scissors = 0;

		for(Integer tool : tools)
		{
			//			System.out.println("Bloop " + tool.intValue());
			if(tool.intValue() == ToolShead.SCREWDRIVER)
				num_screw++;
			else if(tool.intValue()  == ToolShead.HAMMER)
				num_hammer++;
			else if(tool.intValue()  == ToolShead.PLIERS)
				num_pliers++;
			else if(tool.intValue()  == ToolShead.SCISSORS)
				num_scissors++;
			else
				num_pb++;
		}

		if(num_screw > this.num_screw)
			return ToolShead.SCREWDRIVER;
		else if(num_hammer > this.num_hammer)
			return ToolShead.HAMMER;
		else if(num_pliers > this.num_pliers)
			return ToolShead.PLIERS;	
		else if(num_scissors> this.num_scissors)
			return ToolShead.SCISSORS;	
		else if(num_pb > this.num_pb)
			return ToolShead.PAINTBRUSH;

		return -1;
	}

	public void move(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.setBounds(x, y, getWidth(), getHeight());
		panel.revalidate();
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(img, 20,20, 40, 40, null);
	}

	public void larp(final LarpListener a, final int x, final int y)
	{
		
//		moving = true;
//		System.out.println("LArp x,y: " + x + "," + y);
		final Worker thiss = this;
		Thread th = new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized (thiss) {
					{
						while(thiss.x != x || thiss.y != y)
						{
							try {
								thiss.wait(4);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							int dx = 0, dy = 0;
//							int ddx = Math.abs(thiss.x - x),
//									ddy = Math.abs(thiss.y - y);

//							System.out.println("LArp x,y: " + x + "," + y);
							if(thiss.x == x && thiss.x == y)
							{
								thiss.move(x, y);
								break;
							}

							if(thiss.x > x)
								dx = -1;
							else if(thiss.x < x)
								dx = 1;

							if(thiss.y > y)
								dy = -1;
							else if(thiss.y < y)
								dy = 1;

							thiss.move(thiss.x + dx, thiss.y + dy);
						}

						if(a != null)
							a.reachedLocation();
						
					}
				}}});
		th.start();
	}

	public void blockingLarp(Square s)
	{
		final Object lock = new Object();
		jimmy = 0;
		
		facneyLarp(new LarpListener(){

			@Override
			public void reachedLocation() {
				synchronized (lock) {
					jimmy = 1;
					lock.notify();
				}
				
			}},s);
		
		try {
			synchronized (lock) {
				if(jimmy == 0)
					lock.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void facneyLarp(final LarpListener a, Square s)
	{		
			final Vector<Node> larps = new Vector<Node>();
	
			boolean onY = false,
					onX = false,
					workTarget = true,
					workStart = false;
	
			int pX = x, pY = y,
					tX = s.x, tY = s.y;
	
			if(tX < LEFT_X)
			{
				workTarget = false;
			}
			else if(tX > RIGHT_X)
			{
				workTarget = false;
			}
	
			if(x < LEFT_X)
			{
				larps.add(new Node(LEFT_X, y));
				pX = LEFT_X;
				pY = y;
				onY = true;
			}
			else if(x > RIGHT_X)
			{
				larps.add(new Node(RIGHT_X, y));
				pX = RIGHT_X;
				pY = y;
				onY = true;
			}
			else if(x == RIGHT_X || x == LEFT_X)
			{
				onY = true;
			}
			else
			{
				larps.add(new Node(x,y - 60));
				pY = y - 60;
				workStart = true;
			}
	
			if(workTarget)
			{
				//			System.out.println("WorkTG");
	
				//			larps.add(new Node(pX, pY));
				int diff = Math.abs(tY - y);
	
				if(diff < 10) // Go to if on same level
				{
	
					larps.add(new Node(tX, pY));
					larps.add(new Node(tX, tY));
				}
				else
				{
					if(Math.abs(pX - LEFT_X) > Math.abs(pX - RIGHT_X))
					{
						larps.add(new Node(RIGHT_X, pY));
						pX = RIGHT_X;
					}
					else
					{
						larps.add(new Node(LEFT_X, pY));
						pX = LEFT_X;
					}
	
					larps.add(new Node(pX, tY - 60));
					larps.add(new Node(tX, tY - 60));
					larps.add(new Node(tX, tY));
				}
			}
			else if(workStart)
			{
				if(Math.abs(tX - LEFT_X) > Math.abs(tX - RIGHT_X))
				{
					larps.add(new Node(RIGHT_X, pY));
					pX = RIGHT_X;
				}
				else
				{
					larps.add(new Node(LEFT_X, pY));
					pX = LEFT_X;
				}
				
				larps.add(new Node(pX, tY));
				larps.add(new Node(tX, tY));
			}
			else
			{
				int ppx;
	
				if(Math.abs(pX - LEFT_X) > Math.abs(pX - RIGHT_X))
				{
					larps.add(new Node(RIGHT_X, pY));
					pX = RIGHT_X;
				}
				else
				{
					larps.add(new Node(LEFT_X, pY));
					pX = LEFT_X;
				}
	
				if(Math.abs(tX - LEFT_X) > Math.abs(tX - RIGHT_X))
				{
					ppx = RIGHT_X;
				}
				else
				{
					ppx = LEFT_X;
				}
	
				if(ppx != pX)
				{
					pY = HIGH_Y;
					larps.add(new Node(pX, pY));
					larps.add(new Node(ppx, pY));
					pX = ppx;
				}
	
				larps.add(new Node(pX, tY));
				larps.add(new Node(tX, tY));
			}
	
			final Worker thiss = this;
			Thread th = new Thread(new Runnable(){
				@Override
				public void run() {
					synchronized(fancylock)
					{
						final Object monitor = new Object();
						while(larps.size() > 0)
						{
							jim = 0;
							thiss.larp( new LarpListener(){
		
								@Override
								public void reachedLocation() {
									synchronized (monitor) {
										jim = 1;
										monitor.notifyAll();
									}	
								}}, larps.get(0).x, larps.get(0).y);
							larps.remove(0);
							try {
								synchronized (monitor) {
									if(jim == 0)
										monitor.wait();
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						if(a != null)
							a.reachedLocation();
						
					}
				}
			});
			th.start();
	}

	public void addTool(int tool)
	{
		if(tool == ToolShead.SCREWDRIVER)
			num_screw++;
		else if(tool == ToolShead.HAMMER)
			num_hammer++;
		else if(tool == ToolShead.PLIERS)
			num_pliers++;
		else if(tool == ToolShead.SCISSORS)
			num_scissors++;
		else
			num_pb++;
	}

	public void removeTool(int tool)
	{
		if(tool == ToolShead.SCREWDRIVER)
			num_screw--;
		else if(tool == ToolShead.HAMMER)
			num_hammer--;
		else if(tool == ToolShead.PLIERS)
			num_pliers--;
		else if(tool == ToolShead.SCISSORS)
			num_scissors--;
		else
			num_pb--;
	}

	public void getTools() { //Returns after tools have been collected
		
		final Object lock = new Object();
		final Worker thiss = this;

		for(final Integer tool : cRcp.actions.get(0).tools)
		{
			fin = 0;
			facneyLarp(new LarpListener(){
				@Override
				public void reachedLocation() {
					panel.frame.toolshead.takeTool(tool);
					thiss.addTool(tool.intValue());
					synchronized (lock) 
					{
						lock.notifyAll();
						fin = 1;
					}

				}}, panel.frame.toolshead.getAssociatedSqure(tool.intValue()));

			synchronized(lock){
				try {
					if(fin == 0)
						lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		fin = 0;
		larp(new LarpListener(){
			@Override
			public void reachedLocation() {
				synchronized (lock) 
				{
					lock.notifyAll();
					fin = 1;
				}

			}}, x + 60, y);

		synchronized(lock){
			try {
				if(fin == 0)
					lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return;
	}

	private class Node
	{
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		int x;
		int y;
	}

	@Override
	public void run() {
		while(true)
		{
			try {
				synchronized(this)
				{
					wait(40);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(!thinking.get())
			{
				try
				{
					think();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			
		}

	}




}
