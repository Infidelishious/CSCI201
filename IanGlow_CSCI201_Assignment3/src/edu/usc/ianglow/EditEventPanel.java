package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditEventPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5143551689277241665L;

	String[] hours = {"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
	String[] timeSlice = {"00", "15", "30", "45"};
	String[] ap = {"AM", "PM"};
	
	MainFrame parent;
	JComboBox<String> hourDropS, minuteDropS, ampmDropS,
		hourDropE, minuteDropE, ampmDropE;
	JTextField title, location;
	JButton changEvent, deleteEvent, cancelEvent;
	JLabel error;
	Event event;

	public EditEventPanel(MainFrame parent)
	{
		this.parent = parent;
		hourDropS = new JComboBox<String>(hours);
		minuteDropS = new JComboBox<String>(timeSlice);
		ampmDropS = new JComboBox<String>(ap);
		hourDropE = new JComboBox<String>(hours);
		minuteDropE = new JComboBox<String>(timeSlice);
		ampmDropE = new JComboBox<String>(ap);
		title = new JTextField(30);
		location = new JTextField(30);
		
		setLayout(new GridLayout(7,1));
		setBackground(new Color(138,157,180));
		setOpaque(false);
		
		JLabel header = new JLabel("Edit Event:", SwingConstants.CENTER);
		header.setFont(new Font("Helvetica", Font.BOLD, 30));
		add(header);
		
		JPanel temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.add(new JLabel("Title:"), BorderLayout.WEST);
		temp.add(title, BorderLayout.EAST);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.add(new JLabel("Location:"), BorderLayout.WEST);
		temp.add(location, BorderLayout.EAST);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		JPanel temp2 = new JPanel(new GridLayout(1,3));
		temp2.setBackground(Color.WHITE);
		temp.add(new JLabel("Start:"), BorderLayout.WEST);
		temp2.add(hourDropS);
		temp2.add(minuteDropS);
		temp2.add(ampmDropS);
		temp.add(temp2, BorderLayout.CENTER);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp2 = new JPanel(new GridLayout(1,3));
		temp2.setBackground(Color.WHITE);
		temp.add(new JLabel("End:"), BorderLayout.WEST);
		temp2.add(hourDropE);
		temp2.add(minuteDropE);
		temp2.add(ampmDropE);
		temp.add(temp2, BorderLayout.CENTER);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		changEvent = new JButton("Change");
		changEvent.addActionListener(this);
		cancelEvent = new JButton("Cancel");
		cancelEvent.addActionListener(this);
		deleteEvent = new JButton("Delete");
		deleteEvent.addActionListener(this);
		
		temp.add(changEvent, BorderLayout.WEST);
		temp.add(deleteEvent, BorderLayout.CENTER);
		temp.add(cancelEvent, BorderLayout.EAST);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		error = new JLabel(" ");
		error.setForeground(Color.RED);
		temp.add(error, BorderLayout.CENTER);
		add(temp);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == changEvent)
		{
			String t = title.getText();
			String l = location.getText();
			
			if(t.length() == 0 || l.length() == 0)
			{
				error.setText("enter a title and a location");
				return;
			}
			
			parent.events.remove(event);
			
			int sh = hourDropS.getSelectedIndex();
			int sm = minuteDropS.getSelectedIndex() * 15;
			if(ampmDropS.getSelectedIndex() == 1)
				sh += 12;
			
			Calendar start = new GregorianCalendar(parent.currentMonth.start.get(Calendar.YEAR)
					,parent.currentMonth.start.get(Calendar.MONTH)
					,parent.currentMonth.currentDay.day
					,sh, sm);
			
			int eh = hourDropE.getSelectedIndex();
			int em = minuteDropE.getSelectedIndex() * 15;
			if(ampmDropE.getSelectedIndex() == 1)
				eh += 12;
			
			Calendar end = new GregorianCalendar(parent.currentMonth.start.get(Calendar.YEAR)
					,parent.currentMonth.start.get(Calendar.MONTH)
					,parent.currentMonth.currentDay.day
					,eh, em);
			
			parent.events.add(new Event(t, l, start, end));
		}
		else if(arg0.getSource() == deleteEvent)
		{
			parent.events.remove(event);
		}
		
		cleanAndReturn();
		
	}

	private void cleanAndReturn() {
		event = null;
		title.setText("");
		location.setText("");
		error.setText("");
		hourDropS.setSelectedIndex(0);
		minuteDropS.setSelectedIndex(0);
		ampmDropS.setSelectedIndex(0);
		hourDropE.setSelectedIndex(0);
		minuteDropE.setSelectedIndex(0); 
		ampmDropE.setSelectedIndex(0);
		CardLayout cl = (CardLayout)parent.outPanel.getLayout();
		cl.show(parent.outPanel, "month");
		parent.currentMonth.updateEventArea();
	}
	
	public void show(Event event) {
		this.event = event;
		title.setText(event.title);
		location.setText(event.location);
		hourDropS.setSelectedIndex(event.start.get(Calendar.HOUR));
		minuteDropS.setSelectedIndex(event.start.get(Calendar.MINUTE)/15);
		ampmDropS.setSelectedIndex(event.start.get(Calendar.HOUR_OF_DAY) < 12 ? 0 : 1);
		hourDropE.setSelectedIndex(event.end.get(Calendar.HOUR));
		minuteDropE.setSelectedIndex(event.end.get(Calendar.MINUTE)/15); 
		ampmDropE.setSelectedIndex(event.end.get(Calendar.HOUR_OF_DAY) < 12 ? 0 : 1);
		CardLayout cl = (CardLayout)parent.outPanel.getLayout();
		cl.show(parent.outPanel, "edit");
	}

}
