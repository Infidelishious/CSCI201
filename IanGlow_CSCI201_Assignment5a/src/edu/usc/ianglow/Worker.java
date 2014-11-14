package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.usc.ianglow.Recipe.Action;

public class Worker extends JPanel{
	
	private static int HIGH_Y = 140,
			LEFT_X = 100,
			RIGHT_X = 500;
	
	String label, top;
	Image img;
	OutPanel panel;
	boolean moving;
	int x = 0,
		y = 0;
	int num_screw = 0, num_hammer = 0, num_pb = 0, num_pliers = 0, num_scissors = 0, num_wood = 0, num_metal = 0, num_plastic = 0;
	
	Recipe cRcp;
	Action cAct;
//	Thread th;

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
		moving = false;
	}

	public Worker(OutPanel panel, int x, int y)
	{
		this(panel);
		this.x = x;
		this.y = y;
		this.setBounds(x, y, getWidth(), getHeight());
		panel.revalidate();
//		th = new Thread(this);
//		th.start();
		think();
	}
	
	private void think() {
		if(cRcp == null)
		{
			facneyLarp(new LarpListener(){

				@Override
				public void reachedLocation() {
					cRcp = RecipeManager.getInstance().getRecipe();
					think();
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
					think();
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
					think();
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
					think();
				}}, panel.frame.plastic);
			return;
		}
		
		if(cRcp.actions.isEmpty())
		{
			facneyLarp(new LarpListener(){

				@Override
				public void reachedLocation() {
					RecipeManager.getInstance().returnRecipe(cRcp);
					cRcp = null;
					num_wood = 0;
					num_metal = 0;
					num_plastic = 0;
					think();
				}}, panel.frame.tasks);
			return;
		}
		
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
		if(moving) 
			return;
		
		moving = true;
		final Worker thiss = this;
		Thread th = new Thread(new Runnable(){
			@Override
			public void run() {
				synchronized (this) {
				{
					while(thiss.x != x || thiss.y != y)
					{
						try {
							wait(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						int dx = 0, dy = 0;
						int ddx = Math.abs(thiss.x - x),
								ddy = Math.abs(thiss.y - y);
						
						if(ddx < 6 && ddy < 6)
						{
							thiss.move(x, y);
							break;
						}
						
						if(thiss.x > x)
							dx = -5;
						else if(thiss.x < x)
							dx = 5;
						
						if(thiss.y > y)
							dy = -5;
						else if(thiss.y < y)
							dy = 5;
						
						thiss.move(thiss.x + dx, thiss.y + dy);
					}
					 
					thiss.moving = false;
					if(a != null)
					 a.reachedLocation();
				}
			}}});
		th.start();
	}
	
	public void facneyLarp(final LarpListener a, Square s)
	{
		final ArrayList<Node> larps = new ArrayList<Node>();

		boolean onY = false,
				onX = false,
				workTarget = true;
				
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
		else if(x == RIGHT_X)
		{
			onY = true;
		}
		else
		{
			larps.add(new Node(x,y - 60));
			pY = y - 60;
		}
		
		if(workTarget)
		{
//			System.out.println("WorkTG");
			
//			larps.add(new Node(pX, pY));
			
			
			if(tY == y) // Go to if on same level
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
		else
		{
			int ppx;
//			pY = y;
			
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
				final Object monitor = new Object();
					while(!larps.isEmpty())
					{
						thiss.larp( new LarpListener(){

							@Override
							public void reachedLocation() {
								synchronized (monitor) {
									monitor.notifyAll();
								}	
							}}, larps.get(0).x, larps.get(0).y);
							larps.remove(0);
						try {
							synchronized (monitor) {
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
	
	public void getTools() { //Returns after tools have been colected
		// TODO Auto-generated method stub
		
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


	
	
}
