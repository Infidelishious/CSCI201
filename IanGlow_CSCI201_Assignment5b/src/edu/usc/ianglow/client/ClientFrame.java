package edu.usc.ianglow.client;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.usc.ianglow.server.MainFrame;

public class ClientFrame extends JFrame{
	
	public ClientFrame()
	{
		
	}
	
	public static void main(String[] args)
	{
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.setSize(800,600);
		mainFrame.setResizable(false);
		mainFrame.setMinimumSize(new Dimension(50*9 + 280,50*9 + 110));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}
