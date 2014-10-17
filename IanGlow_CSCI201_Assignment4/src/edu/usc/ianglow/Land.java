package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Land extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 20);
	
	public static Color BACK_COLOR = new Color(45, 140, 230);

	public static int UP = 0,
			RIGHT = 1,
			DOWN = 2,
			LEFT = 3;
	
	public boolean[] travel;
	
	int car;
	Color carcolor;
	
	boolean first;
	
	public Land(){
		travel = new boolean[4];
		travel[UP] = false;
		travel[RIGHT] = false;
		travel[DOWN] = false;
		travel[LEFT] = false;
		
		car = 0;
		first = true;
		this.setSize(50,50);
	}
	
	public void updateD(boolean[] d){
		first = false;
		travel = d;
	}
	
	public void setCar(int car, Color carcolor)
	{
		this.car = car;
		this.carcolor = carcolor;
		repaint();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(first) g.setColor(Color.WHITE);
		else g.setColor(BACK_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		
		g.drawLine(0,0,0,getHeight());
		g.drawLine(getWidth() - 1,0,getWidth() - 1,getHeight());
		
		g.drawLine(0,0,getWidth(),0);
		g.drawLine(0,getHeight() - 1,getWidth(),getHeight() - 1);
		
		if(travel[UP])    g.fillRect(getWidth()/3, 0, getWidth()/3, getHeight()/3);
		if(travel[RIGHT]) g.fillRect(2 * getWidth()/3 - 1, getHeight()/3, getWidth()/3 + 1, getHeight()/3);
		if(travel[DOWN])  g.fillRect(getWidth()/3, 2 * getHeight()/3 - 1, getWidth()/3, getHeight()/3 + 1);
		if(travel[LEFT])  g.fillRect(0, getWidth()/3, getWidth()/3, getHeight()/3);
		
		if(travel[UP] || travel[RIGHT] || travel[DOWN] || travel[LEFT])
		{
			if(car == 0)
				g.fillRect(getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3);
			else
			{
				g.setColor(carcolor);
				g.fillRect(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2);
				
				String str = "" + car;
				int width = g.getFontMetrics().stringWidth(str) / 2;
				g.setColor(Color.BLACK);
				g.setFont(FONT);
				g.drawString(str, getWidth()/2 - width * 2, getHeight()/2 + 6);
			}
		}
	}
}
