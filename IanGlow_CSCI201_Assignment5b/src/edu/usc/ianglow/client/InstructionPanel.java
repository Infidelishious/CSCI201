package edu.usc.ianglow.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.usc.ianglow.server.Action;
import edu.usc.ianglow.server.Square;

public class InstructionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final Font FONT = new Font("SansSerif", Font.PLAIN, 18);
	private ArrayList<Square> squares;
	public ClientFrame frame;
	public JTextField tool1num, tool2num, timenum;
	public JComboBox<String> tool1, tool2, workbench;
	
	String[] toolStrings = {" ","Screwdriver", "Hammer", "Paintbrush", "Pliers", "Scissors"};
	String[] workbenchStrings = {" ", "Anvil", "Work Bench", "Furnace", "Table Saw", "Painting Station", "Press"};
	
	public InstructionPanel(ClientFrame parent){
		this.frame = parent;
		setLayout(null);
		setMinimumSize(new Dimension(300, 90));
		setPreferredSize(new Dimension(300, 90));
		
//		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		tool1 = new JComboBox<String>(toolStrings);
		tool1.setLocation(130, 15);
		tool1.setSize(new Dimension(100,20));
		add(tool1);
		
		tool2 = new JComboBox<String>(toolStrings);
		tool2.setLocation(130, 45);
		tool2.setSize(new Dimension(100,20));
		add(tool2);
		
		workbench = new JComboBox<String>(workbenchStrings);
		workbench.setLocation(300, 30);
		workbench.setSize(new Dimension(100,20));
		add(workbench);
		
		
		tool1num = new JTextField();
		tool1num.setLocation(95, 15);
		tool1num.setSize(new Dimension(30,20));
		add(tool1num);
		
		tool2num = new JTextField();
		tool2num.setLocation(95, 45);
		tool2num.setSize(new Dimension(30,20));
		add(tool2num);
		
		timenum = new JTextField();
		timenum.setLocation(480, 30);
		timenum.setSize(new Dimension(30,20));
		add(timenum);

		
	}
	
	
	public Action getAction()
	{
		Action act = new Action();
		
		try{
			int time = Integer.parseInt(timenum.getText());
			int location = workbench.getSelectedIndex();
			int tool1i = tool1.getSelectedIndex() - 1;
			int tool2i = tool2.getSelectedIndex() - 1;
			
			if(location == 0)
				return null;
			
			if(tool1i > -1)
			{
				int tool1n = Integer.parseInt(tool1num.getText());
				for(int i = 0; i < tool1n; i++)
					act.tools.add(Integer.valueOf(tool1i));
			}
			
			if(tool2i > -1)
			{
				int tool2n = Integer.parseInt(tool2num.getText());
				for(int i = 0; i < tool2n; i++)
					act.tools.add(Integer.valueOf(tool2i));
			}
		}
		catch(Exception e)
		{
			return null;
		}
		
		return act;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.setFont(FONT);
		
		g.drawString("Use:", 40, 45);
		g.drawString("At", 270, 45);
		g.drawString("For", 430, 45);
		g.drawString("s", 515, 45);
	}



	public void paintComponent2(Graphics g) {
		super.paintComponent(g);
		
//		g.setColor(Color.BLACK);
//		g.setFont(FONT);
//		g.drawString("Money", 24, 20);
	}
}