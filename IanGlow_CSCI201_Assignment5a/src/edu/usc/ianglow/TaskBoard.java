package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TaskBoard extends JPanel{
	
	JPanel out;
	JScrollPane scrollList;
	JTextArea textArea;
	
	public TaskBoard()
	{
		out = new JPanel();
		textArea = new JTextArea("");
		textArea.setEditable(false);
		textArea.setOpaque(false);
		
		out.setPreferredSize(new Dimension(150,2000));
		setLayout(new BorderLayout());
		out.setLayout(new BorderLayout());
		scrollList = new JScrollPane(textArea);
		out.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setOpaque(true);
		add(new JLabel("TASK BOARD"), BorderLayout.NORTH);
		out.add(scrollList, BorderLayout.CENTER);
		add(out, BorderLayout.CENTER);
	}
	
	public void build(ArrayList<String> a)
	{
		String text = "";
		for(String i: a)
			text += i + "\n";
		textArea.setText(text);
	}

}
