package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class OutPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 20);
	private ArrayList<Square> squares;
	
	public OutPanel(){
		setLayout(new GridBagLayout());
//		this.squares = squares;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
//		for(int i = 0; i < 9; i++)
//		{
//			g.setColor(Color.BLACK);
//			g.setFont(FONT);
//			g.drawString(new String(Character.toChars(i + 'A')), getWidth()/2 - 250, getHeight()/2 - 195 + i * 50);
//			g.drawString("" + (i + 1), getWidth()/2 - 205 + i * 50, getHeight()/2 - 235);
//		}
		
		
	}

}
