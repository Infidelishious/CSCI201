package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

public class Worktable extends Square {
	
	public static Color DARK_GREEN = new Color(42,180,10);
	
	static int ANVIL = 0,
			BENCH = 1,
			FURNACE = 2,
			SAW = 3,
			PAINTING = 4,
			PRESS = 5;
	
	AtomicBoolean open;
	float time = 0;
	int type;
	int bllop = 0;
	
	public Worktable(OutPanel panel, int type, int x, int y) {
		super(panel, "", "", null, x, y);
		open = new AtomicBoolean(true);
		this.type = type;
		try {
			if(type == ANVIL)
				this.img = ImageIO.read(new File("img/anvil.png"));
			else if(type == BENCH)
				this.img = ImageIO.read(new File("img/workbench.png"));
			else if(type == FURNACE)
				this.img = ImageIO.read(new File("img/furnace.png"));
			else if(type == SAW)
				this.img = ImageIO.read(new File("img/tablesaw.png"));
			else if(type == PAINTING)
				this.img = ImageIO.read(new File("img/paintingstation.png"));
			else
				this.img = ImageIO.read(new File("img/press.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public synchronized void work(Worker w, final int length)
	{
		w.blockingLarp(this);
		System.out.println("Begin: " + w.hashCode() + " work " + this.hashCode());
		bllop = 0;
		
		final Worktable thiss = this;
		
		Thread th = new Thread(new Runnable(){

			@Override
			public void run() {
				time = length;
				repaint();
				while(time > 0)
				{
					synchronized(this)
					{
						try {
							wait(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					time--;
					repaint();
				}
				
				synchronized(thiss)
				{
					bllop = 1;
					thiss.notify();
				}
				
			}});
		
		th.start();
		
		synchronized(this)
		{
			if(bllop == 0)
			{
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
//		System.out.println("Finished: " + w.hashCode() + " work " + this.hashCode());
		open.set(true); //dis might not work
		return;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(img, 20,20, 40, 40, null);

		int width = g.getFontMetrics().stringWidth(label);
		int width2 = g.getFontMetrics().stringWidth(top);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(label, -width/2 + 40, 45);

		if(open.get() || time == 0)
		{
			g.setColor(DARK_GREEN);
			width2 = g.getFontMetrics().stringWidth("Open");
			g.drawString("Open", -width2/2 + 40, 15);
		}
		else
		{
			g.setColor(Color.RED);
			width2 = g.getFontMetrics().stringWidth("" + time + "s");
			g.drawString("" + time + "s", -width2/2 + 40, 15);
		}
	}
}