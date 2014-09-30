package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Day extends JLabel implements MouseListener
{
	private static final long serialVersionUID = 1L;
	
	public boolean inMonth, selected; 
	public Month parent;
	public int day;
	
	public Day(Month p, int day, boolean in, boolean s)
	{
		super("" + day, SwingConstants.CENTER);
		setFont(new Font("Helvetica", Font.BOLD, 30));
		setBackground(new Color(201,202,207));
		
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(JLabel.TOP);
		
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(new Color(154,153,158)));
		parent = p;
		this.day = day;
		inMonth = in;
		selected = s;
		
		if(!in) setForeground(new Color(122,121,127));
		else setForeground(new Color(39,42,51));
//		this.setText("" + day);
		this.addMouseListener(this);
//		setEventMarker(true);
	}

	public void setEventMarker(boolean a)
	{
		if(!a)
			setIcon(null);
		else
			setIcon(new ImageIcon("dot.png"));
		
	    revalidate();
	}
	
	public void mouseClicked(MouseEvent arg0) {
		if(!inMonth) return;
		
		if(parent.currentDay == this)
			parent.createEvent();
		else 
		{
			if(parent.currentDay != null)
				parent.currentDay.unselect();
			parent.currentDay = this;
			select();
		}
	}

	private void select() {
		setForeground(Color.WHITE);
		setBackground(new Color(20,104,212));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}

	private void unselect() {
		setForeground(new Color(39,42,51));
		setBackground(new Color(201,202,207));
		setBorder(BorderFactory.createLineBorder(new Color(154,153,158)));
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}
	
}
