package edu.usc.ianglow;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddEventPanel extends JPanel{
	
	private static final long serialVersionUID = 983401575517309534L;
	
	String[] hours = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	String[] timeSlice = {"00", "15", "30", "45"};
	String[] ap = {"AM", "PM"};
	
	JComboBox<String> hourDrop, minuteDrop, ampmDrop;
	JTextField title, location;
	JButton createEvent, cancelEvent;

	public AddEventPanel()
	{
		hourDrop = new JComboBox<String>(hours);
		minuteDrop = new JComboBox<String>(timeSlice);
		ampmDrop = new JComboBox<String>(ap);
	}

}
