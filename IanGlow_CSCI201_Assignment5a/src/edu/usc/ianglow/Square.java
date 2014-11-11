package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

public class Square extends JPanel {

	public static final Font FONT = new Font("SansSerif", Font.PLAIN, 12);

	String label, top;
	Image img;
	OutPanel panel;
	int x = 0,
		y = 0;

	public Square(OutPanel panel, String label, String top, Image img)
	{
		this.panel = panel;
		this.setPreferredSize(new Dimension(80,80));
		this.setSize(80, 60);
		this.setBounds(x, y, getWidth(), getHeight());
		this.label = label;
		this.top = top;
		this.img = img;
	}

	public Square(OutPanel panel, String label, String top, Image img, int x, int y)
	{
		this(panel, label,top,img);
		this.x = x;
		this.y = y;
		this.setBounds(x, y, getWidth(), getHeight());
		panel.revalidate();
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

		int width = g.getFontMetrics().stringWidth(label);
		int width2 = g.getFontMetrics().stringWidth(top);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(label, -width/2 + 40, 45);

		g.drawString(top, -width2/2 + 40, 15);
	}


}
