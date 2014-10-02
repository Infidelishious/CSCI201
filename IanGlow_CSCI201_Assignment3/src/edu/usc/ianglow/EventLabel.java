package edu.usc.ianglow;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class EventLabel extends JLabel implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	Month parent;
	Event event;
	
	public EventLabel(Month parent, Event event)
	{
		super(event.toString());
		this.event = event;
		this.parent = parent;
		setFont(new Font("Helvetica", Font.BOLD, 20));
		addMouseListener(this);
		setBorder(BorderFactory.createLineBorder(new Color(154,153,158)));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		parent.editEvent(this);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		parent.parent.editEventPanel.show(this.event);
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

}
