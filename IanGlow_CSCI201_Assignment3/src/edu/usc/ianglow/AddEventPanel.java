package edu.usc.ianglow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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

public class AddEventPanel extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 983401575517309534L;
	
	String[] hours = {"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
	String[] timeSlice = {"00", "15", "30", "45"};
	String[] ap = {"AM", "PM"};
	
	MainFrame parent;
	JComboBox<String> hourDropS, minuteDropS, ampmDropS,
		hourDropE, minuteDropE, ampmDropE;
	JTextField title, location;
	JButton createEvent, cancelEvent;

	public AddEventPanel(MainFrame parent)
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
		
		setLayout(new GridLayout(6,1));
		
		JLabel header = new JLabel("Create an Event:", SwingConstants.CENTER);
		header.setFont(new Font("Helvetica", Font.BOLD, 30));
		add(header);
		
		JPanel temp = new JPanel();
		temp.add(new JLabel("Title:"), BorderLayout.WEST);
		temp.add(title, BorderLayout.EAST);
		add(temp);
		
		temp = new JPanel();
		temp.add(new JLabel("Location:"), BorderLayout.WEST);
		temp.add(location, BorderLayout.EAST);
		add(temp);
		
		temp = new JPanel();
		JPanel temp2 = new JPanel(new GridLayout(1,3));
		temp.add(new JLabel("Start:"), BorderLayout.WEST);
		temp2.add(hourDropS);
		temp2.add(minuteDropS);
		temp2.add(ampmDropS);
		temp.add(temp2, BorderLayout.CENTER);
		add(temp);
		
		temp = new JPanel();
		temp2 = new JPanel(new GridLayout(1,3));
		temp.add(new JLabel("End:"), BorderLayout.WEST);
		temp2.add(hourDropE);
		temp2.add(minuteDropE);
		temp2.add(ampmDropE);
		temp.add(temp2, BorderLayout.CENTER);
		add(temp);
		
		temp = new JPanel();
		createEvent = new JButton("Create");
		createEvent.addActionListener(this);
		cancelEvent = new JButton("Cancel");
		cancelEvent.addActionListener(this);
		
		temp.add(createEvent, BorderLayout.WEST);
		temp.add(cancelEvent, BorderLayout.EAST);
		add(temp);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == cancelEvent)
		{
			title.setText("");
			location.setText("");
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
			parent.currentMonth.updateEventArea();
			
			title.setText("");
			location.setText("");
			hourDropS.setSelectedIndex(0);
			minuteDropS.setSelectedIndex(0);
			ampmDropS.setSelectedIndex(0);
			hourDropE.setSelectedIndex(0);
			minuteDropE.setSelectedIndex(0); 
			ampmDropE.setSelectedIndex(0);
			CardLayout cl = (CardLayout)parent.outPanel.getLayout();
			cl.show(parent.outPanel, "month");
		}
		
	}

}
