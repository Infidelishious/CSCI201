package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Worktable extends Square {
	
	public static Color DARK_GREEN = new Color(42,180,10);
	
	static int ANVIL = 0,
			BENCH = 1,
			FURNACE = 2,
			SAW = 3,
			PAINTING = 4,
			PRESS = 5;
	
	boolean open;
	float time = 10;
	int type;
	
	public Worktable(OutPanel panel, int type, int x, int y) {
		super(panel, "", "", null, x, y);
		open = true;
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


	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(img, 20,20, 40, 40, null);

		int width = g.getFontMetrics().stringWidth(label);
		int width2 = g.getFontMetrics().stringWidth(top);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(label, -width/2 + 40, 45);

		if(open)
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