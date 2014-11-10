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
	}
}
