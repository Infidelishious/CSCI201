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
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 18);
	private ArrayList<Square> squares;
	public MainFrame frame;
	
	public OutPanel(MainFrame parent){
		this.frame = parent;
		setLayout(null);
//		this.squares = squares;
	}
	
	
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		g.drawString("Anvils", 220, 280);
		g.drawString("Work Benches", 360, 280);
		g.drawString("Furnaces", 205, 400);
		g.drawString("Table Saws", 375, 400);
		g.drawString("Painting Stations", 250, 520);
		g.drawString("Press", 467, 520);
	}

}
