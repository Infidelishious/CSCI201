package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class Square extends JPanel {
	
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 20);
	
	String label, top;
	Image img;
	
	public Square(String label, String top, Image img)
	{
		this.label = label;
		this.top = top;
		this.img = img;
		
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(img != null)
			g.drawImage(img, 0,0, null);
		
		int width = g.getFontMetrics().stringWidth(label) / 2;
		int width2 = g.getFontMetrics().stringWidth(top) / 2;
		
		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString(label, getWidth()/2 - width * 2, getHeight()/2 + 6);
		
		g.drawString(top, getWidth()/2 - width2 * 2, getHeight() + 6);
	}
	

}
