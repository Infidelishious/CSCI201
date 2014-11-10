package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TaskBoard extends JPanel{
	
	JPanel list, out;
	JScrollPane scrollList;
	
	public TaskBoard()
	{
		out = new JPanel();
		list = new JPanel();
		out.setPreferredSize(new Dimension(150,2000));
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		setLayout(new BorderLayout());
		out.setLayout(new BorderLayout());
		scrollList = new JScrollPane(list);
		out.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setOpaque(true);
		add(new JLabel("TASK BOARD"), BorderLayout.NORTH);
		out.add(scrollList, BorderLayout.CENTER);
		add(out, BorderLayout.CENTER);
	}
	
	public void build(ArrayList<String> a)
	{
		list.removeAll();
		for(String i: a)
			list.add(new JLabel(i));
		list.revalidate();
	}

}
