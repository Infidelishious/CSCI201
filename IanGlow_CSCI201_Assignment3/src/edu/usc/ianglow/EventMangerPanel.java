package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EventMangerPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 983401575517309534L;
	
	String[] hours = {"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
	String[] months = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	String[] timeSlice = {"00", "15", "30", "45"};
	String[] ap = {"AM", "PM"};
	
	MainFrame parent;
	JComboBox<String> hourDropS, minuteDropS, ampmDropS,
		hourDropE, minuteDropE, ampmDropE, yearDrop, monthDrop, dayDrop;
	JTextField title, location;

	JLabel error;
	JButton createEvent, cancelEvent;
	
	int currentYear;

	public EventMangerPanel(MainFrame parent)
	{
		Calendar now = Calendar.getInstance();
		currentYear = now.get(Calendar.YEAR);
		
		this.parent = parent;
		hourDropS = new JComboBox<String>(hours);
		minuteDropS = new JComboBox<String>(timeSlice);
		ampmDropS = new JComboBox<String>(ap);
		hourDropE = new JComboBox<String>(hours);
		minuteDropE = new JComboBox<String>(timeSlice);
		ampmDropE = new JComboBox<String>(ap);
		
		makeYearDrop();
		monthDrop = new JComboBox<String>(months);
		monthDrop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateDayDrop();
		}});
		dayDrop = new JComboBox<String>(hours);
		updateDayDrop();
		
		title = new JTextField(30);
		location = new JTextField(30);
		
		setLayout(new GridLayout(8,1));
		setOpaque(true);
		setBackground(new Color(138,157,180));
		
		JLabel header = new JLabel("Event Manager", SwingConstants.CENTER);
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
		temp.add(new JLabel("Date:"), BorderLayout.WEST);
		temp2.add(yearDrop);
		temp2.add(monthDrop);
		temp2.add(dayDrop);
		temp.add(temp2, BorderLayout.CENTER);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp2 = new JPanel(new GridLayout(1,3));
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
		createEvent = new JButton("Create");
		createEvent.addActionListener(this);
		cancelEvent = new JButton("Cancel");
		cancelEvent.addActionListener(this);
		temp.add(createEvent, BorderLayout.WEST);
		temp.add(cancelEvent, BorderLayout.EAST);
		add(temp);
		
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		error = new JLabel(" ");
		error.setForeground(Color.RED);
		temp.add(error, BorderLayout.CENTER);
		add(temp);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateDayDrop() {
		Calendar selected = new GregorianCalendar(currentYear + yearDrop.getSelectedIndex(),monthDrop.getSelectedIndex(),1);
		ArrayList<String> days = new ArrayList<String>();
		
		for(int i = 1; i <= selected.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
			days.add("" + i);
		
		dayDrop.setModel(new DefaultComboBoxModel(days.toArray()));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void makeYearDrop() {
		ArrayList<String> years = new ArrayList<String>();
		
		for(int i = 0; i < 10; i++)
			years.add("" + (i + currentYear));
		
		yearDrop = new JComboBox<String>();
		yearDrop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateDayDrop();
		}});
		yearDrop.setModel(new DefaultComboBoxModel(years.toArray()));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == cancelEvent)
		{
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
		}
		else
		{
			String t = title.getText();
			String l = location.getText();
			
			if(t.length() == 0 || l.length() == 0)
			{
				error.setText("enter a title and a location");
				return;
			}
			
			int sh = hourDropS.getSelectedIndex();
			int sm = minuteDropS.getSelectedIndex() * 15;
			if(ampmDropS.getSelectedIndex() == 1)
				sh += 12;
			
			Calendar start = new GregorianCalendar(currentYear + yearDrop.getSelectedIndex()
					,monthDrop.getSelectedIndex()
					,dayDrop.getSelectedIndex() + 1
					,sh, sm);
			
			int eh = hourDropE.getSelectedIndex();
			int em = minuteDropE.getSelectedIndex() * 15;
			if(ampmDropE.getSelectedIndex() == 1)
				eh += 12;
			
			Calendar end = new GregorianCalendar(currentYear + yearDrop.getSelectedIndex()
					,monthDrop.getSelectedIndex()
					,dayDrop.getSelectedIndex() + 1
					,eh, em);
			
			parent.events.add(new Event(t, l, start, end));
			parent.currentMonth.updateEventArea();
			
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
			parent.currentMonth.addMarkers();
		}
		
	}

}