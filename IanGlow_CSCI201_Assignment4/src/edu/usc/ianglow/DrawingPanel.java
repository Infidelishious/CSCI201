package edu.usc.ianglow;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public Land[][] grid;
	
	public DrawingPanel(){

		grid = new Land[9][9];
		this.setPreferredSize(new Dimension(450,450));
		setLayout(new GridLayout(9,9));
		
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				grid[i][j] = new Land();
				add(grid[i][j]);
			}
		}
	}
	
	public void update(boolean[][][] ds)
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				grid[i][j].updateD(ds[i][j]);
			}
		}
		repaint();
	}

}
